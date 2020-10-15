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

    float colouredCubeVerts[] = {
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

    float cubeWithNormals[] = {
            -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
             0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
            -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
             0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,

            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
            -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
            -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

             0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
             0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
             0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
             0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
             0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
             0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

            -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
            -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
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
        System.out.println("LWJGL Version: " + Version.getVersion() + "!");

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

        Model cube = new Model(cubeWithNormals, 6);
        ShaderProgram program = new ShaderProgram(
                "src/shaders/cube.vs",
                "src/shaders/cube.fs");

        ShaderProgram lightSourceProgram = new ShaderProgram(
                "src/shaders/lightSource.vs",
                "src/shaders/lightSource.fs");

        //Texture wall = Texture.loadTexture("res/wall.png");
        //Texture awesomeface = Texture.loadTexture("res/awesomeface.png");

        Vector3f lightPos = new Vector3f(2.0f, 3.0f,0.0f);

        program.use();
        program.setUniform3f("objectColor", 1.0f, 0.5f, 0.31f);
        program.setUniform3f("lightColor", 1.0f, 1.0f, 1.0f);
        program.setUniform3f("lightPos", lightPos.x, lightPos.y, lightPos.z);

        lightSourceProgram.use();
        lightSourceProgram.setUniform3f("lightColor", 1.0f, 1.0f, 1.0f);


        float deltaTime = 0.0f;
        float lastFrame = 0.0f;

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        while(!display.shouldClose()) {

            float currentTime =  display.getTime();
            deltaTime = currentTime - lastFrame;
            lastFrame = currentTime;
            display.setDeltaTime(deltaTime);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Camera c = display.getCamera();
            display.processInput();

            Matrix4f model = new Matrix4f();
            Matrix4f view = c.getViewMatrix();
            Matrix4f projection = new Matrix4f();
            projection.perspective(Math.toRadians(c.getZoom()),
                    (float)(display.getWidth() / display.getHeight()), 0.1f, 100.0f);
            Matrix4f mvp = new Matrix4f();

            //Light Source//////////////////////////////////////
            cube.bindVAO();

            model.identity();
            model.translate(lightPos);
            mvp.identity();
            projection.mul(view, mvp).mul(model, mvp);

            lightSourceProgram.use();
            lightSourceProgram.setMatrix4f("mvp", mvp);

            glDrawArrays(GL_TRIANGLES, 0, cube.getVertexCount());

            cube.unbindVAO();

            //Objects Affected by Light Source [[CUBE]]
            cube.bindVAO();

            model.identity();
            model.translate(new Vector3f(-2.0f, 0.0f,0.0f)).rotateY(Math.toRadians(currentTime) * 10);
            mvp.identity();
            projection.mul(view, mvp).mul(model, mvp);

            program.use();
            program.setMatrix4f("model", model);
            program.setMatrix4f("view", view);
            program.setMatrix4f("projection", projection);
            program.setUniform3f("viewPos", c.getPosition());

            glDrawArrays(GL_TRIANGLES, 0, cube.getVertexCount());

            cube.unbindVAO();

            //Check events and swap buffers
            display.setCamera(c);
            display.update();
        }
    }

    public static void main(String[] args) { new Main().run(); }
}
