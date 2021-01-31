package me.ingannatore.randomfill;

import me.ingannatore.randomfill.config.RandomFillArguments;
import me.ingannatore.randomfill.config.RandomFillAutocomplete;
import me.ingannatore.randomfill.presets.Preset;
import me.ingannatore.randomfill.utils.LocationService;
import me.ingannatore.randomfill.utils.WorldService;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class CommandRandomFill implements CommandExecutor, TabCompleter {
    private final Map<String, Preset> presets;

    public CommandRandomFill(List<Preset> presets) {
        this.presets = new HashMap<>();
        for (Preset item : presets) {
            this.presets.put(item.getName(), item);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 7) {
            return false;
        }

        Location baseLocation = LocationService.Get(sender);
        if (baseLocation == null) {
            return false;
        }

        World world = baseLocation.getWorld();
        if (world == null) {
            return false;
        }

        RandomFillArguments arguments = new RandomFillArguments(baseLocation, args);
        Preset selectedPreset = presets.get(arguments.getPresetName());
        for (int y : arguments.getYValues()) {
            for (int z : arguments.getZValues()) {
                for (int x : arguments.getXValues()) {
                    WorldService.replaceAirBlock(world, new Location(world, x, y, z), selectedPreset.selectRandomMaterial());
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return RandomFillAutocomplete.Create(args, presets);
    }
}
