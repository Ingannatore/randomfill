package me.ingannatore.randomfill.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JsonLoader {
    public static <T> List<T> Load(File parent, String filename, Class<T> type) throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(
                new FileReader(new File(parent, filename)),
                TypeToken.getParameterized(List.class, type).getType()
        );
    }
}
