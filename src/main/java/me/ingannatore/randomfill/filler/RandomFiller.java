package me.ingannatore.randomfill.filler;

import me.ingannatore.randomfill.utils.MaterialService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.stream.IntStream;

public class RandomFiller {
    private final PresetLibrary presetLibrary;

    public static RandomFiller create(PresetLibrary presetLibrary) {
        return new RandomFiller(presetLibrary);
    }

    public RandomFiller(PresetLibrary presetLibrary) {
        this.presetLibrary = presetLibrary;
    }

    public void Fill(RandomFillerOptions options) throws Exception {
        Preset preset = presetLibrary.get(options.getPresetName());
        if (preset == null) {
            throw new Exception(String.format("Preset '%s' is not defined", options.getPresetName()));
        }

        World world = options.getFrom().getWorld();
        if (world == null) {
            throw new Exception("Invalid location: world is missing");
        }

        int[] xValues = getValues((int) options.getFrom().getX(), (int) options.getTo().getX());
        int[] yValues = getValues((int) options.getFrom().getY(), (int) options.getTo().getY());
        int[] zValues = getValues((int) options.getFrom().getZ(), (int) options.getTo().getZ());

        for (int y : yValues) {
            for (int z : zValues) {
                for (int x : xValues) {
                    Material material = MaterialService.getSolidMaterial(preset.selectRandomMaterial());
                    replaceAirBlock(world, new Location(world, x, y, z), material);
                }
            }
        }
    }

    private void replaceAirBlock(World world, Location location, Material material) {
        if (material == null || world.getBlockAt(location).getType() != Material.AIR) {
            return;
        }

        world.getBlockAt(location).setType(material);
    }

    private int[] getValues(int a, int b) {
        IntStream range = a < b ? IntStream.rangeClosed(a, b) : IntStream.rangeClosed(b, a);
        return range.toArray();
    }
}
