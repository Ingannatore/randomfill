package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.PresetLibrary;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomFillTabCompleter implements TabCompleter {
    private final PresetLibrary presetLibrary;

    public RandomFillTabCompleter(PresetLibrary presetLibrary) {
        this.presetLibrary = presetLibrary;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
            case 4:
                return getCoordinatesHints(commandSender, 0);
            case 2:
            case 5:
                return getCoordinatesHints(commandSender, 1);
            case 3:
            case 6:
                return getCoordinatesHints(commandSender, 2);
            case 7:
                return getPresetHints(args[6]);
        }

        return Collections.emptyList();
    }

    private List<String> getCoordinatesHints(CommandSender commandSender, int numberOfCompiledArgs) {
        List<String> results = new ArrayList<>();
        Location targetBlockLocation = getTargetBlockLocation(commandSender);

        List<String> hints = new ArrayList<>();
        hints.add(targetBlockLocation == null ? "~" : String.valueOf(targetBlockLocation.getBlockX()));
        hints.add(targetBlockLocation == null ? "~" : String.valueOf(targetBlockLocation.getBlockY()));
        hints.add(targetBlockLocation == null ? "~" : String.valueOf(targetBlockLocation.getBlockZ()));

        int hintSize = 1;
        for (int i = numberOfCompiledArgs; i < 3; i++) {
            results.add(String.join(" ", hints.subList(numberOfCompiledArgs, numberOfCompiledArgs + hintSize)));
            hintSize++;
        }

        return results;
    }

    private Location getTargetBlockLocation(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return null;
        }

        Block targetBlock = ((Player) sender).getTargetBlockExact(7);
        if (targetBlock == null) {
            return null;
        }

        return targetBlock.getLocation();
    }

    private List<String> getPresetHints(String argValue) {
        if (argValue == null || argValue.equals("")) {
            return presetLibrary.getPresetNames();
        }

        return presetLibrary
                .getPresetNames()
                .stream()
                .filter(s -> s.startsWith(argValue))
                .collect(Collectors.toList());
    }
}
