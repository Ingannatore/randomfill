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

    public static Location update(Location location, String x, String y, String z) throws Exception {
        return new Location(
                location.getWorld(),
                updateCoordinates(location.getX(), x),
                updateCoordinates(location.getY(), y),
                updateCoordinates(location.getZ(), z)
        );
    }

    public static double updateCoordinates(double value, String modifier) throws Exception {
        try {
            if (modifier.startsWith("~")) {
                return value + (modifier.length() == 1 ? 0 : Double.parseDouble(modifier.substring(1)));
            }

            return Double.parseDouble(modifier);
        } catch (NumberFormatException ex) {
            throw new Exception(String.format("Invalid coordinates modifier '%s'", modifier));
        }
    }
}
