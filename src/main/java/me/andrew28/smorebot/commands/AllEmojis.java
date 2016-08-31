package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Emote;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @author Andrew Tran
 */
@Command(command = "::all emo(jis|tes)",
        caseSensetive = false,
        help = "List all emojis",
        usage = ":: all emojis")
public class AllEmojis implements RegexCommand{
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        String msg = "";
        for (Emote emote : SmoreBot.getInstance().getJDA().getAvailableEmotes()){
            msg += emote.getAsEmote() + " ";
        }
        MessageUtility.reply(message, msg);
    }

}

