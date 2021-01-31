package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.RandomFiller;
import me.ingannatore.randomfill.filler.RandomFillerOptions;
import me.ingannatore.randomfill.utils.LocationService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandRandomFill implements CommandExecutor, TabCompleter {
    private final RandomFiller randomFiller;

    public CommandRandomFill(RandomFiller randomFiller) {
        this.randomFiller = randomFiller;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            randomFiller.Fill(RandomFillerOptions.create(LocationService.getFromSender(sender), args));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
            case 4:
                return Arrays.asList("~", "~ ~", "~ ~ ~");
            case 2:
            case 5:
                return Arrays.asList("~", "~ ~");
            case 3:
            case 6:
                return Collections.singletonList("~");
            case 7:
                return randomFiller.getPresetLibrary().getPresetNames();
        }

        return new ArrayList<>();
    }
}
