package me.ingannatore.randomfill.scheduler;

import me.ingannatore.randomfill.utils.JsonLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleLibrary {
    private final Map<String, List<Schedule>> scheduleMap;
    private final List<String> worldNames;

    public static ScheduleLibrary create(File parent, String filename) throws Exception {
        return new ScheduleLibrary(JsonLoader.Load(parent, filename, Schedule.class));
    }

    public ScheduleLibrary(List<Schedule> schedules) {
        this.scheduleMap = new HashMap<>();
        this.worldNames = new ArrayList<>();

        for (Schedule item : schedules) {
            if (!this.scheduleMap.containsKey(item.getWorld())) {
                this.scheduleMap.put(item.getWorld(), new ArrayList<>());
                this.worldNames.add(item.getWorld());
            }

            this.scheduleMap.get(item.getWorld()).add(item);
        }
    }

    public int size() {
        return scheduleMap.size();
    }

    public List<String> getWorldNames() {
        return worldNames;
    }

    public List<Schedule> getAllByWorld(String worldName) {
        return this.scheduleMap.getOrDefault(worldName, new ArrayList<>());
    }

    public void removeAllByWorld(String worldName) {
        this.scheduleMap.remove(worldName);
        this.worldNames.remove(worldName);
    }
}
