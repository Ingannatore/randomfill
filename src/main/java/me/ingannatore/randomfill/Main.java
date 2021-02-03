package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.PresetLibrary;
import me.ingannatore.randomfill.filler.RandomFiller;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private final String presetsFileName = "presets.json";

    @Override
    public void onEnable() {
        this.saveResource(presetsFileName, false);

        try {
            PresetLibrary presetLibrary = PresetLibrary.create(this.getDataFolder(), presetsFileName);
            getLogger().info(String.format("Finished loading %d presets", presetLibrary.size()));

            registerCommand(
                    "randomfill",
                    new RandomFillCommand(RandomFiller.create(presetLibrary), this.getLogger()),
                    new RandomFillTabCompleter(presetLibrary)
            );
        } catch (FileNotFoundException ex) {
            this.getLogger().log(Level.SEVERE, "Unable to load the presets library", ex);
        } catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, "onEnable generic error", ex);
        }
    }

    private void registerCommand(String name, CommandExecutor executor, TabCompleter completer) throws Exception {
        PluginCommand command = this.getCommand(name);
        if (command == null) {
            throw new Exception(String.format("Command '%s' not found", name));
        }

        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }
}
