package me.ingannatore.randomfill.config;

import me.ingannatore.randomfill.presets.Preset;

import java.util.*;

public class RandomFillAutocomplete {
    public static List<String> Create(String[] args, Map<String, Preset> presets) {
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
                return new ArrayList<>(presets.keySet());
        }

        return new ArrayList<>();
    }
}
