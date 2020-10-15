package main;

import camera.Camera;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    private long window;

    private final int width;
    private final int height;
    private final String title;

    private Camera camera;
    private float deltaTime;

    private boolean firstMouse;
    private float lastX;
    private float lastY;

    public DisplayManager(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

        this.camera = null;
        deltaTime = 0.0f;
        firstMouse = true;
        lastX = this.width / 2.0f;
        lastY = this.height / 2.0f;

        init();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mods)->{
            if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(window, true);

            if(key == GLFW_KEY_L && action == GLFW_PRESS)
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

            if(key == GLFW_KEY_F && action == GLFW_PRESS)
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            if(firstMouse) {
                lastX = (float)xpos;
                lastY = (float)ypos;
                firstMouse = false;
            }

            float xOffset = (float)xpos - lastX;
            float yOffset = lastY - (float)ypos;

            lastX = (float)xpos;
            lastY = (float)ypos;

            camera.processMouseMovement(xOffset, yOffset, true);
        });

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> camera.processMouseScroll((float)yoffset));
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> glViewport(0,0,width, height));

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
    }

    public void processInput() {
        if(camera != null) {
            if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.FORWARD, deltaTime);

            if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.BACKWARD, deltaTime);

            if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.LEFT, deltaTime);

            if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.RIGHT, deltaTime);

            if(glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.UP, deltaTime);

            if(glfwGetKey(window, GLFW_KEY_LEFT_ALT) == GLFW_PRESS)
                camera.processKeyboard(Camera.Movement.DOWN, deltaTime);
        }
    }

    public void setDeltaTime(float dt) {
        this.deltaTime = dt;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public float getTime() {
        return (float)glfwGetTime();
    }

    public void destroy() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
