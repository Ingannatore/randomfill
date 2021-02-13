package me.ingannatore.randomfill.filler;

import me.ingannatore.randomfill.scheduler.Schedule;
import me.ingannatore.randomfill.utils.LocationService;
import org.bukkit.Location;
import org.bukkit.World;

public class RandomFillerOptions {
    private static final int numberOfArguments = 7;
    private final Location from;
    private final Location to;
    private final String presetName;

    public static RandomFillerOptions create(Location location, String[] args) throws Exception {
        if (location == null) {
            throw new Exception("Base location not specified");
        }
        if (args.length != numberOfArguments) {
            throw new Exception(String.format("Incorrect number of arguments: %d of %d", args.length, numberOfArguments));
        }

        Location from = LocationService.update(location, args[0], args[1], args[2]);
        Location to = LocationService.update(location, args[3], args[4], args[5]);

        return new RandomFillerOptions(from, to, args[6]);
    }

    public static RandomFillerOptions create(World world, Schedule schedule) throws Exception {
        if (world == null) {
            throw new Exception("World not specified");
        }
        if (schedule == null) {
            throw new Exception("Schedule not specified");
        }

        Location from = LocationService.create(world, schedule.getFrom());
        Location to = LocationService.create(world, schedule.getTo());

        return new RandomFillerOptions(from, to, schedule.getPreset());
    }

    public RandomFillerOptions(Location from, Location to, String presetName) {
        this.from = from;
        this.to = to;
        this.presetName = presetName;
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
