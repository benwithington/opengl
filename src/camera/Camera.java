package camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    public enum Action {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        UP,
        DOWN,
        RESET
    }

    //Default Values
    final float YAW         = -90.0f;
    final float PITCH       =   0.0f;
    final float SPEED       =  10.0f;
    final float SENSITIVITY =   0.1f;
    final float ZOOM        =  45.0f;

    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private Vector3f right;
    private Vector3f worldUp;

    private float yaw;
    private float pitch;
    private float movementSpeed;
    private float mouseSensitivity;
    private float zoom;

    public Camera() {
        this.resetToDefault();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(this.position, this.position.add(this.front, new Vector3f()), this.up);
    }

    public void processKeyboard(Action action, float deltaTime) {
        float velocity = this.movementSpeed * deltaTime;
        if(action == Action.FORWARD) {
            this.position.add(this.front.mul(velocity, new Vector3f()));
        }
        if(action == Action.BACKWARD) {
            this.position.sub(this.front.mul(velocity, new Vector3f()));
        }
        if(action == Action.LEFT) {
            this.position.sub(this.right.mul(velocity, new Vector3f()));
        }
        if(action == Action.RIGHT) {
            this.position.add(this.right.mul(velocity, new Vector3f()));
        }
        if(action == Action.UP) {
            this.position.add(this.up.mul(velocity, new Vector3f()));
        }
        if(action == Action.DOWN) {
            this.position.sub(this.up.mul(velocity, new Vector3f()));
        }
        if(action == Action.RESET) {
            this.resetToDefault();
        }
    }

    public void resetToDefault() {
        this.position    = new Vector3f(0.0f, 0.0f,  10.0f);
        this.up          = new Vector3f(0.0f, 1.0f,  0.0f);
        this.front       = new Vector3f(0.0f, 0.0f, -1.0f);
        this.right       = new Vector3f();

        this.yaw                = YAW;
        this.pitch              = PITCH;
        this.movementSpeed      = SPEED;
        this.mouseSensitivity   = SENSITIVITY;
        this.zoom               = ZOOM;

        this.worldUp = this.up;

        updateCameraVectors();
    }

    public void processMouseMovement(float xOffset, float yOffset, boolean constainPitch) {
        xOffset *= this.mouseSensitivity;
        yOffset *= this.mouseSensitivity;

        this.yaw    += xOffset;
        this.pitch  += yOffset;

        if(constainPitch) {
            if(this.pitch > 89.0f)
                this.pitch = 89.0f;

            if(this.pitch < -89.0f)
                this.pitch = -89.0f;
        }

        updateCameraVectors();
    }

    public void processMouseScroll(float yOffset) {
        this.zoom -= yOffset;
        if(this.zoom < 1.0f)
            this.zoom = 1.0f;
        if(this.zoom > 45.0f)
            this.zoom = 45.0f;
    }

    private void updateCameraVectors() {

        float x = (float) (Math.cos(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch)));
        float y = (float) (Math.sin(Math.toRadians(this.pitch)));
        float z = (float) (Math.sin(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch)));

        Vector3f front = new Vector3f(x, y, z);
        front.normalize(this.front);

        this.front.cross(this.worldUp, this.right);
        this.right.normalize();
        this.right.cross(this.front, this.up);
        this.up.normalize();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getFront() {
        return front;
    }

    public Vector3f getUp() {
        return up;
    }

    public Vector3f getRight() {
        return right;
    }

    public Vector3f getWorldUp() {
        return worldUp;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    public float getZoom() {
        return zoom;
    }
}
