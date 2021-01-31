package me.ingannatore.randomfill.filler;

public class PresetMaterial {
    private final String material;
    private final double weight;

    public PresetMaterial(String material, double weight) {
        this.material = material;
        this.weight = weight;
    }

    public String getMaterial() {
        return material;
    }

    public double getWeight() {
        return weight;
    }
}
