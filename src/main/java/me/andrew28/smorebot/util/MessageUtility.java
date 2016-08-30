package me.andrew28.smorebot.util;

import net.dv8tion.jda.entities.Message;

/**
 * @author Andrew Tran
 */
public class MessageUtility {
    public static void reply(Message message, String reply){
        message.getChannel().sendMessage(reply);
    }
}
