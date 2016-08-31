package me.andrew28.smorebot.util;

import me.andrew28.smorebot.SmoreBot;
import net.dv8tion.jda.entities.Message;

/**
 * @author Andrew Tran
 */
public class MessageUtility {
    public static void reply(Message message, String reply){
        if (reply == ""){
            reply = ".";
        }
        if (reply.length() > 2000){
            reply = reply.substring(1999);
        }
        message.getChannel().sendMessage(reply);
    }
    public static String choose(String... randoms){
        return randoms[SmoreBot.getInstance().getRandom().nextInt(randoms.length - 1)];
    }
}
