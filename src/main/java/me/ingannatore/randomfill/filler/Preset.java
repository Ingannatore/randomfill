package me.ingannatore.randomfill.filler;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Preset {
    private final String name;
    private final List<PresetMaterial> items;
    private EnumeratedDistribution<String> materials;

    public Preset(String name, List<PresetMaterial> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public String selectRandomMaterial() {
        if (materials == null) {
            List<Pair<String, Double>> pairs = new ArrayList<>();
            for (PresetMaterial item : items) {
                pairs.add(new Pair<>(item.getMaterial(), item.getWeight()));
            }

            materials = new EnumeratedDistribution<>(pairs);
        }

        return materials.sample();
    }
}
