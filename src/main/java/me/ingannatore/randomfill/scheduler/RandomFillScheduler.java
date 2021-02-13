package me.ingannatore.randomfill.scheduler;

import me.ingannatore.randomfill.filler.RandomFiller;
import me.ingannatore.randomfill.filler.RandomFillerOptions;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomFillScheduler implements Runnable {
    private final RandomFiller randomFiller;
    private final ScheduleLibrary scheduleLibrary;
    private final Logger logger;

    public RandomFillScheduler(RandomFiller randomFiller, ScheduleLibrary scheduleLibrary, Logger logger) {
        this.randomFiller = randomFiller;
        this.scheduleLibrary = scheduleLibrary;
        this.logger = logger;
    }

    public boolean hasSchedules() {
        return scheduleLibrary.size() > 0;
    }

    @Override
    public void run() {
        List<String> worldNames = new ArrayList<>(scheduleLibrary.getWorldNames());
        for (String worldName : worldNames) {
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.warning(String.format("Unknown world '%s'", worldName));
                scheduleLibrary.removeAllByWorld(worldName);
                logger.warning(String.format("Removed all schedules for the world '%s'", worldName));

                continue;
            }

            long worldTime = world.getFullTime();
            long dayTime = world.getTime();
            for (Schedule schedule : scheduleLibrary.getAllByWorld(worldName)) {
                if (schedule.mustBeInitialized()) {
                    schedule.updateNextRunTime(worldTime, dayTime);
                    logger.info(String.format("Initialized schedule '%s'", schedule.getName()));
                }
                if (!schedule.mustRun(worldTime)) {
                    continue;
                }

                try {
                    randomFiller.Fill(RandomFillerOptions.create(world, schedule));
                    logger.info(String.format("Executed schedule '%s' on world '%s'", schedule.getName(), worldName));
                } catch (Exception exception) {
                    logger.log(
                            Level.WARNING,
                            String.format("Error running schedule '%s' on world '%s'", schedule.getName(), worldName),
                            exception
                    );

                    logger.warning(String.format("Disabling schedule '%s' on world '%s'", schedule.getName(), worldName));
                    schedule.disable();

                    continue;
                }

                schedule.updateNextRunTime(worldTime, dayTime);
            }
        }
    }
}
