package me.ingannatore.randomfill.filler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresetLibrary {
    private final Map<String, Preset> presetMap;
    private final List<String> presetNames;

    public static PresetLibrary create(File parent, String filename) {
        return new PresetLibrary(parent, filename);
    }

    public PresetLibrary(File parent, String filename) {
        this.presetMap = new HashMap<>();
        this.presetNames = new ArrayList<>();

        List<Preset> presets = load(parent, filename);
        for (Preset item : presets) {
            this.presetMap.put(item.getName(), item);
            this.presetNames.add(item.getName());
        }
    }

    public int size() {
        return presetMap.size();
    }

    public List<String> getPresetNames() {
        return presetNames;
    }

    public Preset get(String name) {
        return presetMap.getOrDefault(name, null);
    }

    private List<Preset> load(File parent, String filename) {
        File presetsFile = new File(parent, filename);
        Gson gson = new Gson();

        try {
            return gson.fromJson(new FileReader(presetsFile), new TypeToken<List<Preset>>() {}.getType());
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
