package me.ingannatore.randomfill.utils;

import org.bukkit.Material;

public class MaterialService {
    public static Material getSolidMaterial(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        Material material = Material.matchMaterial(name);
        if (material == null) {
            throw new Exception(String.format("Material '%s' not found", name));
        }
        if (!material.isSolid()) {
            throw new Exception(String.format("Material '%s' is not a solid block", name));
        }

        return material;
    }
}
