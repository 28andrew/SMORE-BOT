package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.lang.management.ManagementFactory;

/**
 * @author Andrew Tran
 */
@Command(command = "info (about )?(@)?Smore(bot)?",
        caseSensetive = false,
        help = "Get info about Smore",
        usage = "info @Smore")
public class Info implements RegexCommand{
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        Integer people = 0;
        for (Guild g : SmoreBot.getInstance().getJDA().getGuilds()){
            people += g.getUsers().size();
        }
        Integer channels = 0;
        for (Guild g : SmoreBot.getInstance().getJDA().getGuilds()){
            channels += g.getTextChannels().size();
        }
        //Taken from Almighty Alpaca
        //https://github.com/Java-Discord-Bot-System/Plugin-Uptime/blob/master/src/main/java/com/almightyalpaca/discord/bot/plugin/uptime/UptimePlugin.java#L28-L42
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;
        // final long milliseconds = duration % 1000;

        String uptime = (years == 0 ? "" : years + " Years, ") + (months == 0 ? "" : months + " Months, ") + (days == 0 ? "" : days + " Days, ") + (hours == 0 ? "" : hours + " Hours, ")
                + (minutes == 0 ? "" : minutes + " Minutes, ") + (seconds == 0 ? "" : seconds + " Seconds, ") /* + (milliseconds == 0 ? "" : milliseconds + " Milliseconds, ") */;

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        String[] msg = {
            "SmoreBot",
            "Author: Andrew Tran -- " + SmoreBot.getInstance().getJDA().getUserById("127170421987475456").getId(),
            "Source: <https://github.com/xXAndrew28Xx/SMORE-BOT>",
            "Servers: `" + SmoreBot.getInstance().getJDA().getGuilds().size() + "`",
            "Channels: `" + channels + "`",
            "People: `" + people + '`',
            "Uptime: `" + uptime + "`"
        };

        MessageUtility.reply(message, String.join("\n", msg));
    }
    //Taken from Almighty Alpaca
    //https://github.com/Java-Discord-Bot-System/Core/blob/master/src/main/java/com/almightyalpaca/discord/bot/system/util/StringUtils.java#L15-L17
    private String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
}
