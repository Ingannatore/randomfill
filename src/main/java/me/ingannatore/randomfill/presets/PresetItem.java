package me.ingannatore.randomfill.presets;

public class PresetItem {
    private final String material;
    private final double weight;

    public PresetItem(String material, double weight) {
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
