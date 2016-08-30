package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.Rank;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.TimerTask;

/**
 * @author Andrew Tran
 */
@Command(command = "(shutdown|explode|melt) (please )?smore",
        caseSensetive = false,
        usage = "explode please smore",
        help = "Shutdown Smorebot",
        requiredRank = Rank.ADMIN)
public class Shutdown implements RegexCommand{
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        MessageUtility.reply(message, "I am about to explode.");
        SmoreBot.getInstance().getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                MessageUtility.reply(message, ":boom: Marshmallows, cracker crumbs, and scoldering chocolate everywhere :boom:");
                System.exit(1);
            }
        }, 1000L);
    }
}
