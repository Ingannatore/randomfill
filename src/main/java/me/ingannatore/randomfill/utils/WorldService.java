package me.ingannatore.randomfill.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldService {
    public static void replaceAirBlock(World world, Location location, String materialName) {
        if (world.getBlockAt(location).getType() != Material.AIR) {
            return;
        }

        Material material = Material.matchMaterial(materialName);
        if (material == null || !material.isSolid()) {
            return;
        }

        world.getBlockAt(location).setType(material);
    }
}
