package model;

import org.joml.Vector3f;

public class Light {

    //Light properties
    private Vector3f position;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;

    public Light(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular) {
        this.position = position;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = new Vector3f(position);
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = new Vector3f(ambient);
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = new Vector3f(diffuse);
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = new Vector3f(specular);
    }
}
