package model;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Model {

    private final int vao;
    private final int vertexCount;
    private final int FLOAT_SIZE = Float.SIZE/8;

    private Vector3f colour;

    public Model(float[] positions, int[] indices, Vector3f colour) {
        vertexCount = indices.length;
        this.colour = colour;

        vao = glGenVertexArrays();
        bindVAO();

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, dataToIntBuffer(indices), GL_STATIC_DRAW);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, dataToFloatBuffer(positions), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 0 * FLOAT_SIZE);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        unbindVAO();
    }

    public Model(float[] positions, int floatsPerVertex, Vector3f colour) {
        vertexCount = positions.length/floatsPerVertex;
        this.colour = colour;

        vao = glGenVertexArrays();
        bindVAO();

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, dataToFloatBuffer(positions), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, floatsPerVertex * FLOAT_SIZE, 0 * FLOAT_SIZE);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, floatsPerVertex * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        unbindVAO();
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
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
