package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @author Andrew Tran
 */
@Command(command = "((commands of|help) (me )?(@)?Smore(bot)?|(@)?Smore(bot)? (commands of |help)( me)?)",
        usage = "Help Me @Smore",
        help = "PMs you the help of Smorebot",
        caseSensetive =  false)
public class Help implements RegexCommand{
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        MessageUtility.reply(message, "PMing you my help, " + user.getAsMention());
        user.getPrivateChannel().sendMessage(SmoreBot.getInstance().getHelp());
    }
}
