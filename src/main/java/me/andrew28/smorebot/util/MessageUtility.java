package me.andrew28.smorebot.util;

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
}
