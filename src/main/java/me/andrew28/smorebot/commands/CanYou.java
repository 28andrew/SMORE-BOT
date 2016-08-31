package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.types.StartsWithCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * @author Andrew Tran
 */
@Command(command = "(@)?Smore(,) can (yo)?u .*",
        caseSensetive = false,
        help = "Smore might be something for you",
        usage = "Smore, can you <something>")
public class CanYou implements RegexCommand {
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        String[] split = message.getRawContent().split(" ");
        ArrayList<String> remainder1 = new ArrayList<>();
        for (Integer i = 0; i < split.length; i++){
            if (i >= 3){
                remainder1.add(split[i]);
            }
        }
        String remainder = String.join(" ", remainder1);
        if (SmoreBot.getInstance().getRandom().nextBoolean()){
            //YES
            MessageUtility.reply(message, user.getAsMention() + ", I will not " + remainder);
        }else{
            //NO
            String[] verbs = {"gladfully", "graciously", "amazingly", "horribly", "lovingly"};
            MessageUtility.reply(message, String.format(user.getAsMention() + ", I will %s " + remainder, verbs[SmoreBot.getInstance().getRandom().nextInt(verbs.length - 1)]));
        }
    }
}
