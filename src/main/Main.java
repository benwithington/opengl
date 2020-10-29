package main;

import camera.Camera;
import model.Light;
import model.Material;
import model.MaterialModel;
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

    float vertices[] = {
            // positions          // normals           // texture coords
            -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,
             0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  0.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
            -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,

            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,
             0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,

            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
            -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
            -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

             0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
             0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
             0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
             0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
             0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,
             0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  1.0f,
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  0.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,

            -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f,
             0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  1.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
             0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  0.0f,
            -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f
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

        Light light = new Light(new Vector3f(2.5f, 3.0f, 0.0f),
                new Vector3f(0.2f, 0.2f, 0.2f),
                new Vector3f(0.5f, 0.5f, 0.5f),
                new Vector3f(1.0f, 1.0f, 1.0f));
        Model lightModel = new Model(vertices, 8);

        Material cubeMaterial = new Material(new Vector3f(1.0f, 0.5f, 0.31f), 64.0f);
        MaterialModel cube = new MaterialModel(vertices, 8, cubeMaterial);

        ShaderProgram cubeProgram = new ShaderProgram(
                "src/shaders/directionalLight.vs",
                "src/shaders/directionalLight.fs");

        cubeProgram.use();
        cubeProgram.setInt("material.diffuse", 0);
        cubeProgram.setInt("material.specular", 1);

        ShaderProgram lightSourceProgram = new ShaderProgram(
                "src/shaders/lightSource.vs",
                "src/shaders/lightSource.fs");

        //Texture wall = Texture.loadTexture("res/wall.png");
        //Texture awesomeface = Texture.loadTexture("res/awesomeface.png");
        Texture diffuseMap = Texture.loadTexture("res/container2.png");
        Texture specularMap = Texture.loadTexture("res/container2_specular.png");

        float deltaTime;
        float lastFrame = 0.0f;

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
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
            model.translate(light.getPosition());
            mvp.identity();
            projection.mul(view, mvp).mul(model, mvp);

            lightSourceProgram.use();
            lightSourceProgram.setUniform3f("lightColor", light.getSpecular());
            lightSourceProgram.setMatrix4f("mvp", mvp);

            //glDrawArrays(GL_TRIANGLES, 0, lightModel.getVertexCount());

            cube.unbindVAO();

            //Objects Affected by Light Source [[CUBE]]
            cube.bindVAO();

            cubeProgram.use();

            //Vertex uniforms
            cubeProgram.setMatrix4f("view", view);
            cubeProgram.setMatrix4f("projection", projection);
            //Fragment uniforms
            cubeProgram.setUniform3f("viewPos", c.getPosition());

            //Light Uniforms
            cubeProgram.setUniform3f("light.direction", new Vector3f(-0.2f, -1.0f, -0.3f));
            cubeProgram.setUniform3f("light.ambient", light.getAmbient());
            cubeProgram.setUniform3f("light.diffuse", light.getDiffuse());
            cubeProgram.setUniform3f("light.specular", light.getSpecular());

            //Material Uniforms
            cubeProgram.setUniform3f("material.specular", cube.getMaterial().getSpecular());
            cubeProgram.setFloat("material.shininess", cube.getMaterial().getShininess());

            for(int i = 0; i < cubePositions.length; i++) {
                model.identity();
                model.translate(cubePositions[i]);
                float angle = 20.0f * i;
                model.rotate(angle, new Vector3f(1.0f, 0.3f,0.5f));
                cubeProgram.setMatrix4f("model", model);

                Texture.setActiveTexture(0);
                diffuseMap.bind();
                Texture.setActiveTexture(1);
                specularMap.bind();
                glDrawArrays(GL_TRIANGLES, 0, cube.getVertexCount());
            }

            cube.unbindVAO();

            //Check events and swap buffers
            display.setCamera(c);
            display.update();
        }
    }

    public static void main(String[] args) { new Main().run(); }
}
