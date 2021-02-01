package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.PresetLibrary;
import me.ingannatore.randomfill.filler.RandomFiller;
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

            this.getCommand("randomfill").setExecutor(new CommandRandomFill(RandomFiller.create(presetLibrary), this.getLogger()));
        } catch (FileNotFoundException ex) {
            this.getLogger().log(Level.SEVERE, "Unable to load the presets library", ex);
        } catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, "onEnable generic error", ex);
        }
    }
}
