package com.basicengine.graphics.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
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

	public GLBitmap(GL10 gl10, Bitmap bitmap) {
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

		gl = gl10;

		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);
		bitmap.recycle();
	}

	public GLBitmap(GL10 gl10, Bitmap bitmap, int typel) {
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

		gl = gl10;
		type = typel;

		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(type, 0, bitmap, 0);
		bitmap.recycle();
	}

	public GLBitmap(GL10 gl10, int typel, int textureid) {
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

		gl = gl10;
		type = typel;
		textures[0] = textureid;
		gl.glBindTexture(type, textures[0]);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(type, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	}

	public void loadGLTexture(Bitmap bitmap) {
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
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
