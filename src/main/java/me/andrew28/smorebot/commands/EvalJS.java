package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.Rank;
import me.andrew28.smorebot.types.StartsWithCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;

/**
 * @author Andrew Tran
 */
@Command(command = "::eval js", help = "Evaluate JS", usage = "::eval js <(new line)javascript>", requiredRank = Rank.ADMIN)
public class EvalJS implements StartsWithCommand{
    @Override
    public void handle(User user, Message message, String remainder, MessageReceivedEvent event) {
        remainder = remainder.substring(1);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("SmoreBot", SmoreBot.getInstance());
        engine.put("Channel", message.getChannel());
        engine.put("Message", message);
        engine.put("MessageUtility", MessageUtility.class);
        Object result = null;
        MessageUtility.reply(message, "Evaluating: " + remainder);
        try {
            result = engine.eval(remainder);
            message.getChannel().deleteMessageById("220294758856130560");
        } catch (ScriptException e) {
            MessageUtility.reply(message, "Line Number: " + e.getLineNumber());
            MessageUtility.reply(message, "Error: " + e.getMessage());
            return;
        }
        if (result == null){
            MessageUtility.reply(message, "Executed.");
            return;
        }
        MessageUtility.reply(message, "OUTPUT: " + result.toString());
    }
}
