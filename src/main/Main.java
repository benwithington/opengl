package main;

import camera.Camera;
import model.Model;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import shaders.ShaderProgram;
import textures.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Main {

    private DisplayManager display;

    int indices[] = {  // note that we start from 0!
            0, 1, 3,   // first triangle
            1, 2, 3    // second triangle
    };

    float cubeVerts[] = {
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

             0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
             0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
             0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
             0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
    };

    Vector3f cubePositions[] = {
      new Vector3f( 0.0f,  0.0f,  0.0f),
      new Vector3f( 2.0f,  5.0f, -15.0f),
      new Vector3f(-1.5f, -2.2f, -2.5f),
      new Vector3f(-3.8f, -2.0f, -12.3f),
      new Vector3f( 2.4f, -0.4f, -3.5f),
      new Vector3f(-1.7f,  3.0f, -7.5f),
      new Vector3f( 1.3f, -2.0f, -2.5f),
      new Vector3f( 1.5f,  2.0f, -2.5f),
      new Vector3f( 1.5f,  0.2f, -1.5f),
      new Vector3f(-1.3f,  1.0f, -1.5f),
    };

    private void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        display.destroy();
    }

    private void init() {
        display = new DisplayManager(800, 600, "OpenGL Project");
        GL.createCapabilities();
        glViewport(0,0, display.getWidth(), display.getHeight());
        glEnable(GL_DEPTH_TEST);
    }

    private void loop() {

        Camera camera = new Camera();
        display.setCamera(camera);

        Model cube = new Model(cubeVerts, indices);
        ShaderProgram program = new ShaderProgram(
                "src/shaders/vertexShader.txt",
                "src/shaders/fragmentShader.txt");

        Texture wall = Texture.loadTexture("res/wall.png");
        Texture awesomeface = Texture.loadTexture("res/awesomeface.png");

        program.use();
        program.setInt("texture1", 0);
        program.setInt("texture2", 1);

        float deltaTime = 0.0f;
        float lastFrame = 0.0f;

        glClearColor(0.2f, 0.6f, 0.4f, 1.0f);
        while(!display.shouldClose()) {

            float currentTime =  display.getTime();
            deltaTime = currentTime - lastFrame;
            lastFrame = currentTime;
            display.setDeltaTime(deltaTime);


            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Camera c = display.getCamera();
            display.processInput();

            Texture.setActiveTexture(0);
            wall.bind();
            Texture.setActiveTexture(1);
            awesomeface.bind();

            Matrix4f view = c.getViewMatrix();

            Matrix4f projection = new Matrix4f();
            projection.perspective(Math.toRadians(c.getZoom()),
                    (float)(display.getWidth() / display.getHeight()), 0.1f, 100.0f);

            program.setMatrix4f("view", view);
            program.setMatrix4f("projection", projection);

            cube.bindVAO();

            for(int i = 0; i < cubePositions.length; i++) {
                Matrix4f model = new Matrix4f();
                float angle = Math.toRadians(20.0f * i);

                if(i % 3 == 0)
                    angle = Math.toRadians(currentTime * 25.0f);


                model.translate(cubePositions[i])
                        .rotateX(angle * 1.0f)
                        .rotateY(angle * 0.3f)
                        .rotateZ(angle * 0.5f);

                Matrix4f mvp = new Matrix4f().identity();
                projection.mul(view, mvp).mul(model, mvp);

                program.setMatrix4f("model", model);
                program.setMatrix4f("mvp", mvp);

                glDrawArrays(GL_TRIANGLES, 0, cube.getVertexCount());
            }

            display.setCamera(c);
            //glDrawArrays(GL_TRIANGLES, 0, cube.getVertexCount());
            cube.unbindVAO();

            Texture.unbind();

            //Check events and swap buffers
            display.update();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
