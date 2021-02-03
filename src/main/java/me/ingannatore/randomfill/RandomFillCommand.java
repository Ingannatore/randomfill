package me.ingannatore.randomfill;

import me.ingannatore.randomfill.filler.RandomFiller;
import me.ingannatore.randomfill.filler.RandomFillerOptions;
import me.ingannatore.randomfill.utils.LocationService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class RandomFillCommand implements CommandExecutor {
    private final RandomFiller randomFiller;
    private final Logger logger;

    public RandomFillCommand(RandomFiller randomFiller, Logger logger) {
        this.randomFiller = randomFiller;
        this.logger = logger;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            randomFiller.Fill(RandomFillerOptions.create(LocationService.getFromSender(sender), args));
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            logger.warning(e.getMessage());
            return false;
        }

        return true;
    }
}
