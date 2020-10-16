package model;

public class MaterialModel extends Model {

    private Material material;

    public MaterialModel(float[] positions, int[] indices, Material material) {
        super(positions, indices);
        this.material = material;
    }

    public MaterialModel(float[] positions, int floatsPerVertex, Material material) {
        super(positions, floatsPerVertex);
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
