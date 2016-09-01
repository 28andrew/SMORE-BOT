package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.InviteUtil;

/**
 * @author Andrew Tran
 */
@Command(command = "(?i)(@)?Smore( ,|,|) what servers are (yo)?u (o|i)n(\\?)?",
        caseSensetive = false,
        help = "",
        usage = "")
public class AllServers implements RegexCommand {
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        String msg = "Servers: ";
        for (Guild g : SmoreBot.getInstance().getJDA().getGuilds()){
            InviteUtil.AdvancedInvite invite = null;
            try {
                invite = InviteUtil.createInvite(g.getPublicChannel());
            }catch(Exception e){
                invite = null;
            }
            if (invite == null){
                msg += "\n" + g.getName() + " ";
            }else{
                msg += "\n" + g.getName() + " <https://discord.gg/" + invite.getCode() + ">";
            }

        }
        MessageUtility.reply(message, msg);
    }
}
