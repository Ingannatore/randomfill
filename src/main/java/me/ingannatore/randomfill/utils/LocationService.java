package me.ingannatore.randomfill.utils;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationService {
    public static Location getFromSender(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender).getLocation();
        }
        if (sender instanceof BlockCommandSender) {
            return ((BlockCommandSender) sender).getBlock().getLocation();
        }

        return null;
    }

    public static Location update(Location base, String x, String y, String z) {
        return new Location(
                base.getWorld(),
                updateCoordinate(base.getX(), x),
                updateCoordinate(base.getY(), y),
                updateCoordinate(base.getZ(), z)
        );
    }

    public static double updateCoordinate(double value, String modifier) {
        if (modifier.startsWith("~")) {
            return value + (modifier.length() == 1 ? 0 : Double.parseDouble(modifier.substring(1)));
        }

        return Double.parseDouble(modifier);
    }
}
