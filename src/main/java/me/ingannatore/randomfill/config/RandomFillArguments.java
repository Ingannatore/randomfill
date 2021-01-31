package me.ingannatore.randomfill.config;

import me.ingannatore.randomfill.utils.LocationService;
import org.bukkit.Location;

import java.util.stream.IntStream;

public class RandomFillArguments {
    private final String presetName;
    private final int[] xValues;
    private final int[] yValues;
    private final int[] zValues;

    public RandomFillArguments(Location base, String[] args) {
        Location from = LocationService.Update(base, args[0], args[1], args[2]);
        Location to = LocationService.Update(base, args[3], args[4], args[5]);
        presetName = args[6];

        xValues = from.getX() < to.getX()
                ? IntStream.rangeClosed((int) from.getX(), (int) to.getX()).toArray()
                : IntStream.rangeClosed((int) to.getX(), (int) from.getX()).toArray();

        yValues = from.getY() < to.getY()
                ? IntStream.rangeClosed((int) from.getY(), (int) to.getY()).toArray()
                : IntStream.rangeClosed((int) to.getY(), (int) from.getY()).toArray();

        zValues = from.getZ() < to.getZ()
                ? IntStream.rangeClosed((int) from.getZ(), (int) to.getZ()).toArray()
                : IntStream.rangeClosed((int) to.getZ(), (int) from.getZ()).toArray();
    }

    public String getPresetName() {
        return presetName;
    }

    public int[] getXValues() {
        return xValues;
    }

    public int[] getYValues() {
        return yValues;
    }

    public int[] getZValues() {
        return zValues;
    }
}
