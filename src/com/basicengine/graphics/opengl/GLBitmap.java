package com.basicengine.graphics.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLUtils;

public class GLBitmap {
	private FloatBuffer textureBuffer; // buffer holding the texture coordinates  
	private float texture[] = {
	        // Mapping coordinates for the vertices  
	        0.0f, 1.0f, // top left 
	        0.0f, 0.0f, // bottom left
	        1.0f, 1.0f, // top right  
	        1.0f, 0.0f // bottom right 
	};

	private FloatBuffer vertexBuffer; // buffer holding the vertices  

	private float vertices[] = { -1.0f, -1.0f, 0.0f, // bottom left  
	        -1.0f, 1.0f, 0.0f, // top left  
	        1.0f, -1.0f, 0.0f, // bottom right  
	        1.0f, 1.0f, 0.0f // top right  
	};

	int[] textures = new int[1];
	GL10 gl;
	int type = GL10.GL_TEXTURE_2D;
	int index = 1; // The Texture which is contains or allocated this picture's index

	public GLBitmap(GL10 gl10, Bitmap bitmap, int i) {
		gl = gl10;

		index = i;

		gl.glEnable(type);
		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public GLBitmap(GL10 gl10, Bitmap bitmap) {
		gl = gl10;

		gl.glEnable(type);
		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public GLBitmap(GL10 gl10, int typel, Bitmap bitmap, int i) {
		gl = gl10;
		type = typel;

		index = i;

		gl.glEnable(type);
		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public GLBitmap(GL10 gl10, int typel, Bitmap bitmap) {
		gl = gl10;
		type = typel;

		gl.glEnable(type);
		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public GLBitmap(GL10 gl10, int typel, int textureid) {
		gl = gl10;
		type = typel;
		textures[0] = textureid;
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public void loadGLTexture(Bitmap bitmap, int i) {
		index = i;

		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	}
	
	public void loadGLTexture(Bitmap bitmap) {
		gl.glGenTextures(index, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void setRectDx(Rect r) {
		float[] vertic = new float[8];
		vertic[0] = r.left;
		vertic[1] = r.bottom;
		vertic[2] = r.left;
		vertic[3] = r.top;
		vertic[4] = r.right;
		vertic[5] = r.bottom;
		vertic[6] = r.right;
		vertic[7] = r.top;

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertic.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(vertic);
		textureBuffer.position(0);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void setRectEx(Rect r, Rect fixed) {
		float ratio = (float)4 / (float)(fixed.right - fixed.left + fixed.bottom - fixed.top); //Just for beauty 
		float[] vertic = new float[12];
		vertic[0] = r.left * ratio - 1;
		vertic[1] = r.bottom * ratio - 1;
		vertic[2] = r.left * ratio - 1;
		vertic[3] = r.top * ratio - 1;
		vertic[4] = r.right * ratio - 1;
		vertic[5] = r.bottom * ratio - 1;
		vertic[6] = r.right * ratio - 1;
		vertic[7] = r.top * ratio - 1;

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertic.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(vertic);
		textureBuffer.position(0);
	}

	public void draw(int x, int y) {
		gl.glTranslatex(x, y, 0);
		// bind the previously generated texture  
		gl.glBindTexture(type, textures[0]);

		// Point to our buffers  
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation  
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer  
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		// Draw the vertices as triangle strip  
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving  
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

}
