package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.PresetLibrary;
import me.ingannatore.randomfill.filler.RandomFiller;
import me.ingannatore.randomfill.scheduler.RandomFillScheduler;
import me.ingannatore.randomfill.scheduler.ScheduleLibrary;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private final String presetsFileName = "presets.json";
    private final String scheduleFileName = "schedule.json";
    private int schedulerTaskId;

    @Override
    public void onEnable() {
        this.saveResource(presetsFileName, false);
        this.saveResource(scheduleFileName, false);

        try {
            PresetLibrary presetLibrary = PresetLibrary.create(this.getDataFolder(), presetsFileName);
            getLogger().info(String.format("Finished loading %d presets", presetLibrary.size()));

            ScheduleLibrary scheduleLibrary = ScheduleLibrary.create(this.getDataFolder(), scheduleFileName);
            getLogger().info(String.format("Finished loading %d schedules", scheduleLibrary.size()));

            RandomFiller randomFiller = RandomFiller.create(presetLibrary);
            registerCommand(
                    new RandomFillCommand(randomFiller, this.getLogger()),
                    new RandomFillTabCompleter(presetLibrary)
            );
            registerScheduler(
                    new RandomFillScheduler(randomFiller, scheduleLibrary, this.getLogger())
            );
        } catch (FileNotFoundException ex) {
            this.getLogger().log(Level.SEVERE, "Unable to load a library", ex);
        } catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, "onEnable generic error", ex);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.getServer().getScheduler().cancelTask(schedulerTaskId);
        if (schedulerTaskId > 0) {
            this.getLogger().info(String.format("Stopped scheduler task with ID %d", schedulerTaskId));
        }
    }

    private void registerCommand(CommandExecutor executor, TabCompleter completer) throws Exception {
        final String commandName = "randomfill";

        PluginCommand command = this.getCommand(commandName);
        if (command == null) {
            throw new Exception(String.format("Command '%s' not found", commandName));
        }

        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }

    private void registerScheduler(RandomFillScheduler scheduler) throws Exception {
        if (!scheduler.hasSchedules()) {
            this.getLogger().info("Skipping creating task for scheduler: empty schedule library");
            return;
        }

        schedulerTaskId = this.getServer()
                .getScheduler()
                .scheduleSyncRepeatingTask(this, scheduler, 100, 100);

        if (schedulerTaskId < 0) {
            throw new Exception("Unable to create a task for the scheduler");
        }

        this.getLogger().info(String.format("Created scheduler task with ID %d", schedulerTaskId));
    }
}
