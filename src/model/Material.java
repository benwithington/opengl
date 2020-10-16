package model;

import org.joml.Vector3f;

public class Material {

    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float shininess;

    public Material(Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
        this.ambient = new Vector3f(ambient);
        this.diffuse = new Vector3f(diffuse);
        this.specular = new Vector3f(specular);
        this.shininess = shininess;
    }

    public Material(Vector3f colour, float shininess) {
        this.ambient = new Vector3f(colour);
        this.diffuse = new Vector3f(colour);
        this.specular = new Vector3f(0.5f, 0.5f, 0.5f);
        this.shininess = shininess;
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

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
