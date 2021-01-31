package me.ingannatore.randomfill.utils;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationService {
    public static Location Get(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender).getLocation();
        }
        if (sender instanceof BlockCommandSender) {
            return ((BlockCommandSender) sender).getBlock().getLocation();
        }

        return null;
    }

    public static Location Update(Location base, String x, String y, String z) {
        return new Location(
                base.getWorld(),
                UpdateValue(base.getX(), x),
                UpdateValue(base.getY(), y),
                UpdateValue(base.getZ(), z)
        );
    }

    public static double UpdateValue(double base, String value) {
        if (value.startsWith("~")) {
            return base + (value.length() == 1 ? 0 : Double.parseDouble(value.substring(1)));
        }

        return Double.parseDouble(value);
    }
}
