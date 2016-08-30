package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @author Andrew Tran
 */
@Command(command = "Hi(,)? (@)?Smore( !|!)?",
        usage = "Hi, @Smore !",
        help = "Say hi to smore!",
        caseSensetive = false)
public class HiSmore implements RegexCommand{

    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        MessageUtility.reply(message, "Hi, " + user.getAsMention() + ". I am Smore!");
    }

}
