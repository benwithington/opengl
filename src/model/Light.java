package model;

import org.joml.Vector3f;

public class Light {

    public static final Vector3f WHITE  = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f BLACK  = new Vector3f(0.0f, 0.0f, 0.0f);
    public static final Vector3f RED    = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f GREEN  = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f BLUE   = new Vector3f(0.0f, 0.0f, 1.0f);

    private Model model;

    private Vector3f colour;
    private Vector3f position;

    public Light(Vector3f position, Vector3f colour, Model model) {
        this.model = model;
        this.colour = colour;
        this.position = position;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
