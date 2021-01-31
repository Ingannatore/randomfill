package me.ingannatore.randomfill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.ingannatore.randomfill.presets.Preset;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Main extends JavaPlugin {
    private final String presetsFileName = "presets.json";
    private List<Preset> presets;

    @Override
    public void onEnable() {
        presets = LoadPresets();
        getLogger().info(String.format("Finished loading %d presets", presets.size()));

        this.getCommand("randomfill").setExecutor(new CommandRandomFill(presets));
    }

    private List<Preset> LoadPresets() {
        this.saveResource(presetsFileName, false);

        File presetsFile = new File(this.getDataFolder(), presetsFileName);
        Gson gson = new Gson();

        try {
            return gson.fromJson(new FileReader(presetsFile), new TypeToken<List<Preset>>() {}.getType());
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
