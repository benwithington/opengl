package model;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Model {

    private final int vao;
    private final int vertexCount;
    private final int FLOAT_SIZE = 4;

    public Model(float[] positions, int[] indices) {
        vertexCount = positions.length/5;
        vao = glGenVertexArrays();
        bindVAO();

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, dataToIntBuffer(indices), GL_STATIC_DRAW);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, dataToFloatBuffer(positions), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * FLOAT_SIZE, 0 * FLOAT_SIZE);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        unbindVAO();
    }

    public void bindVAO() {
        glBindVertexArray(vao);
    }

    public void unbindVAO() {
        glBindVertexArray(0);
    }

    private FloatBuffer dataToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private IntBuffer dataToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}