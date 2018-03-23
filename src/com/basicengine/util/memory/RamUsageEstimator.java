package com.basicengine.util.memory;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;

import com.basicengine.util.Constants;

class RamUsageEstimator { //public

  public static final long ONE_KB = 1024;

  public static final long ONE_MB = ONE_KB * ONE_KB;

  public static final long ONE_GB = ONE_KB * ONE_MB;

  private RamUsageEstimator() {}

  public final static boolean COMPRESSED_REFS_ENABLED;

  public final static int NUM_BYTES_OBJECT_REF;

  public final static int NUM_BYTES_OBJECT_HEADER;

  public final static int NUM_BYTES_ARRAY_HEADER;
  
  public final static int NUM_BYTES_OBJECT_ALIGNMENT;

  private static final Map<Class<?>,Integer> primitiveSizes = new IdentityHashMap<Class<?>, Integer>();
  static {
    primitiveSizes.put(boolean.class, 1);
    primitiveSizes.put(byte.class, 1);
    primitiveSizes.put(char.class, Integer.valueOf(Character.BYTES));
    primitiveSizes.put(short.class, Integer.valueOf(Short.BYTES));
    primitiveSizes.put(int.class, Integer.valueOf(Integer.BYTES));
    primitiveSizes.put(float.class, Integer.valueOf(Float.BYTES));
    primitiveSizes.put(double.class, Integer.valueOf(Double.BYTES));
    primitiveSizes.put(long.class, Integer.valueOf(Long.BYTES));
  }

  static final long LONG_CACHE_MIN_VALUE, LONG_CACHE_MAX_VALUE;
  static final int LONG_SIZE;
  
  static final boolean JVM_IS_HOTSPOT_64BIT;
  
  static final String MANAGEMENT_FACTORY_CLASS = "java.lang.management.ManagementFactory";
  static final String HOTSPOT_BEAN_CLASS = "com.sun.management.HotSpotDiagnosticMXBean";

  static {    
    if (Constants.JRE_IS_64BIT) {
      // At this time, There aren't any android phone use 64-bit Java
      boolean compressedOops = false;
      int objectAlignment = 8;
      boolean isHotspot = false;
      try {
        final Class<?> beanClazz = Class.forName(HOTSPOT_BEAN_CLASS);
        final Object hotSpotBean = Class.forName(MANAGEMENT_FACTORY_CLASS)
          .getMethod("getPlatformMXBean", Class.class)
          .invoke(null, beanClazz);
        if (hotSpotBean != null) {
          isHotspot = true;
          final Method getVMOptionMethod = beanClazz.getMethod("getVMOption", String.class);
          try {
            final Object vmOption = getVMOptionMethod.invoke(hotSpotBean, "UseCompressedOops");
            compressedOops = Boolean.parseBoolean(
                vmOption.getClass().getMethod("getValue").invoke(vmOption).toString()
            );
          } catch (Exception e) {
            isHotspot = false;
          }
          try {
            final Object vmOption = getVMOptionMethod.invoke(hotSpotBean, "ObjectAlignmentInBytes");
            objectAlignment = Integer.parseInt(
                vmOption.getClass().getMethod("getValue").invoke(vmOption).toString()
            );
          } catch (Exception e) {
            isHotspot = false;
          }
        }
      } catch (Exception e) {
        isHotspot = false;
      }
      JVM_IS_HOTSPOT_64BIT = isHotspot;
      COMPRESSED_REFS_ENABLED = compressedOops;
      NUM_BYTES_OBJECT_ALIGNMENT = objectAlignment;
      // reference size is 4, if we have compressed oops:
      NUM_BYTES_OBJECT_REF = COMPRESSED_REFS_ENABLED ? 4 : 8;
      // "best guess" based on reference size:
      NUM_BYTES_OBJECT_HEADER = 8 + NUM_BYTES_OBJECT_REF;
      // array header is NUM_BYTES_OBJECT_HEADER + NUM_BYTES_INT, but aligned (object alignment):
      NUM_BYTES_ARRAY_HEADER = (int) alignObjectSize(NUM_BYTES_OBJECT_HEADER + Integer.BYTES);
    } else {
      JVM_IS_HOTSPOT_64BIT = false;
      COMPRESSED_REFS_ENABLED = false;
      NUM_BYTES_OBJECT_ALIGNMENT = 8;
      NUM_BYTES_OBJECT_REF = 4;
      NUM_BYTES_OBJECT_HEADER = 8;
      NUM_BYTES_ARRAY_HEADER = NUM_BYTES_OBJECT_HEADER + Integer.BYTES;
    }

    // get min/max value of cached Long class instances:
    long longCacheMinValue = 0;
    while (longCacheMinValue > Long.MIN_VALUE
        && Long.valueOf(longCacheMinValue - 1) == Long.valueOf(longCacheMinValue - 1)) {
      longCacheMinValue -= 1;
    }
    long longCacheMaxValue = -1;
    while (longCacheMaxValue < Long.MAX_VALUE
        && Long.valueOf(longCacheMaxValue + 1) == Long.valueOf(longCacheMaxValue + 1)) {
      longCacheMaxValue += 1;
    }
    LONG_CACHE_MIN_VALUE = longCacheMinValue;
    LONG_CACHE_MAX_VALUE = longCacheMaxValue;
    LONG_SIZE = (int) shallowSizeOfInstance(Long.class);
  }
  
  public static long alignObjectSize(long size) {
    size += (long) NUM_BYTES_OBJECT_ALIGNMENT - 1L;
    return size - (size % NUM_BYTES_OBJECT_ALIGNMENT);
  }

  public static long sizeOf(Long value) {
    if (value >= LONG_CACHE_MIN_VALUE && value <= LONG_CACHE_MAX_VALUE) {
      return 0;
    }
    return LONG_SIZE;
  }

  public static long sizeOf(byte[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + arr.length);
  }

  public static long sizeOf(boolean[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + arr.length);
  }

  public static long sizeOf(char[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Character.BYTES * arr.length);
  }

  public static long sizeOf(short[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Short.BYTES * arr.length);
  }

  public static long sizeOf(int[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Integer.BYTES * arr.length);
  }

  public static long sizeOf(float[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Float.BYTES * arr.length);
  }

  public static long sizeOf(long[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Long.BYTES * arr.length);
  }

  public static long sizeOf(double[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Double.BYTES * arr.length);
  }

  public static long shallowSizeOf(Object[] arr) {
    return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) NUM_BYTES_OBJECT_REF * arr.length);
  }

  public static long shallowSizeOf(Object obj) {
    if (obj == null) return 0;
    final Class<?> clz = obj.getClass();
    if (clz.isArray()) {
      return shallowSizeOfArray(obj);
    } else {
      return shallowSizeOfInstance(clz);
    }
  }

  public static long shallowSizeOfInstance(Class<?> clazz) {
    if (clazz.isArray())
      throw new IllegalArgumentException("This method does not work with array classes.");
    if (clazz.isPrimitive())
      return primitiveSizes.get(clazz);
    
    long size = NUM_BYTES_OBJECT_HEADER;

    for (;clazz != null; clazz = clazz.getSuperclass()) {
      final Class<?> target = clazz;
      final Field[] fields = AccessController.doPrivileged(new PrivilegedAction<Field[]>() {
        @Override
        public Field[] run() {
          return target.getDeclaredFields();
        }
      });
      for (Field f : fields) {
        if (!Modifier.isStatic(f.getModifiers())) {
          size = adjustForField(size, f);
        }
      }
    }
    return alignObjectSize(size);    
  }

  private static long shallowSizeOfArray(Object array) {
    long size = NUM_BYTES_ARRAY_HEADER;
    final int len = Array.getLength(array);
    if (len > 0) {
      Class<?> arrayElementClazz = array.getClass().getComponentType();
      if (arrayElementClazz.isPrimitive()) {
        size += (long) len * primitiveSizes.get(arrayElementClazz);
      } else {
        size += (long) NUM_BYTES_OBJECT_REF * len;
      }
    }
    return alignObjectSize(size);
  }

  static long adjustForField(long sizeSoFar, final Field f) {
    final Class<?> type = f.getType();
    final int fsize = type.isPrimitive() ? primitiveSizes.get(type) : NUM_BYTES_OBJECT_REF;
    return sizeSoFar + fsize;
  }

  public static String humanReadableUnits(long bytes) {
    return humanReadableUnits(bytes, 
        new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.ROOT)));
  }

  public static String humanReadableUnits(long bytes, DecimalFormat df) {
    if (bytes / ONE_GB > 0) {
      return df.format((float) bytes / ONE_GB) + " GB";
    } else if (bytes / ONE_MB > 0) {
      return df.format((float) bytes / ONE_MB) + " MB";
    } else if (bytes / ONE_KB > 0) {
      return df.format((float) bytes / ONE_KB) + " KB";
    } else {
      return bytes + " bytes";
    }
  }

}
