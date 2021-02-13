package me.ingannatore.randomfill.filler;

import me.ingannatore.randomfill.utils.JsonLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PresetLibrary {
    private final Map<String, Preset> presetMap;
    private final List<String> presetNames;

    public static PresetLibrary create(File parent, String filename) throws Exception {
        return new PresetLibrary(JsonLoader.Load(parent, filename, Preset.class));
    }

    public PresetLibrary(List<Preset> presets) {
        this.presetMap = new HashMap<>();
        this.presetNames = new ArrayList<>();

        for (Preset item : presets) {
            this.presetMap.put(item.getName(), item);
            this.presetNames.add(item.getName());
        }
    }

    public int size() {
        return presetMap.size();
    }

    public List<String> getNames(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            return presetNames;
        }

        return presetNames
                .stream()
                .filter(s -> s.startsWith(filter))
                .collect(Collectors.toList());
    }

    public Preset get(String name) {
        return presetMap.getOrDefault(name, null);
    }
}
