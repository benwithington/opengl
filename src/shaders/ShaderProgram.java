package shaders;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.*;
import java.nio.file.*;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int program;

    public ShaderProgram(String vertexFile, String fragmentFile) {
        String vertexShaderSource = readFile(vertexFile);
        String fragmentShaderSource = readFile(fragmentFile);

        IntBuffer ib = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        int status;
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, ib);
        status = ib.get(0);
        if(status==0) {
            glGetShaderiv(vertexShader, GL_INFO_LOG_LENGTH, ib);
            status = ib.get(0);
            if(status > 1) {
                System.out.println("Vertex Shader Error: " + glGetShaderInfoLog(vertexShader));
            }
        }

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, ib);
        status = ib.get(0);
        if(status == 0) {
            glGetShaderiv(fragmentShader, GL_INFO_LOG_LENGTH, ib);
            status = ib.get(0);
            if(status > 1) {
                System.out.println("Fragment Shader Error: " + glGetShaderInfoLog(fragmentShader));
            }
        }

        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);
        glValidateProgram(program);

        glGetProgramiv(program, GL_LINK_STATUS, ib);
        status = ib.get(0);
        if(status==0) {
            System.out.println("Shader Program Unable To Link.");
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void use() {
        glUseProgram(program);
    }

    public int uniformLocation(String variableName) {
        return glGetUniformLocation(program, variableName);
    }

    public void setMatrix4f(String name, Matrix4f matrix) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        matrix.get(fb);
        glUniformMatrix4fv(uniformLocation(name), false, fb);
    }

    public void setUniform4f(String name, float a, float b, float c, float d) {
        glUniform4f(uniformLocation(name), a, b, c, d);
    }

    public void setUniform3f(String name, float a, float b, float c) {
        glUniform3f(uniformLocation(name), a, b, c);
    }

    public void setUniform3f(String name, Vector3f vec) {
        glUniform3f(uniformLocation(name), vec.x, vec.y, vec.z);
    }

    public void setInt(String name, int value) {
        glUniform1i(uniformLocation(name), value);
    }

    public void setFloat(String name, float value) {
        glUniform1f(uniformLocation(name), value);
    }

    public void setBool(String name, boolean value) {
        int set = 0;

        if(value)
            set = 1;

        glUniform1i(uniformLocation(name), set);
    }

    private String readFile(String path) {
        Path filename = Path.of(path);
        String shaderSource = null;
        try {
            shaderSource = Files.readString(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shaderSource;
    }
}
