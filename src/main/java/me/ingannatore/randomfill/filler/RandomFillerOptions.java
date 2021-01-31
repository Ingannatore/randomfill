package me.ingannatore.randomfill.filler;

import me.ingannatore.randomfill.utils.LocationService;
import org.bukkit.Location;

public class RandomFillerOptions {
    private final Location from;
    private final Location to;
    private final String presetName;

    public static RandomFillerOptions create(Location location, String[] args) throws Exception {
        return new RandomFillerOptions(location, args);
    }

    public RandomFillerOptions(Location location, String[] args) throws Exception {
        if (args.length != 7) {
            throw new Exception(String.format("Missing arguments: only %d/7 found", args.length));
        }
        if (location == null) {
            throw new Exception("Missing base location");
        }

        from = LocationService.update(location, args[0], args[1], args[2]);
        to = LocationService.update(location, args[3], args[4], args[5]);
        presetName = args[6];
    }

    public String getPresetName() {
        return presetName;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }
}
