package me.ingannatore.randomfill.scheduler;

import me.ingannatore.randomfill.utils.Position;

public class Schedule {
    private final long ticksPerDay = 24000;
    private final String name;
    private final String world;
    private final Position from;
    private final Position to;
    private final String preset;
    private final int when;
    private boolean enabled;
    private transient long nextRunTime = 0;

    public Schedule(String name, String world, Position from, Position to, String preset, int when, boolean enabled) {
        this.name = name;
        this.world = world;
        this.from = from;
        this.to = to;
        this.preset = preset;
        this.when = when;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public String getPreset() {
        return preset;
    }

    public void disable() {
        enabled = false;
    }

    public boolean mustBeInitialized() {
        return nextRunTime == 0 && when != 0;
    }

    public boolean mustRun(long worldTime) {
        return enabled && worldTime >= nextRunTime;
    }

    public void updateNextRunTime(long worldTime, long dayTime) {
        if (dayTime == when) {
            nextRunTime = worldTime + ticksPerDay;
            return;
        }

        long todayRunTime = ((worldTime / ticksPerDay) * ticksPerDay) + when;
        nextRunTime = todayRunTime + (dayTime < when ? 0 : ticksPerDay);
    }
}
