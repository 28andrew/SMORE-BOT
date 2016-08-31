package me.andrew28.smorebot.commands;

import me.andrew28.smorebot.SmoreBot;
import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.RegexCommand;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * @author Andrew Tran
 */
@Command(command = "(@)?(Smore)?(bot)?( )?How (do you|to) make a (@)?Smore(\\?)?",
        caseSensetive = false,
        help = "",
        usage = "")
public class SmoreRecipe implements RegexCommand{
    @Override
    public void handle(User user, Message message, MessageReceivedEvent event) {
        String fireLighter = MessageUtility.choose("flint and steel", "match", "electric overpriced lighter", "less wet twigs", "live electrical wire");
        String[] recipeAdverbs = new String[4];
        recipeAdverbs[0] = MessageUtility.choose("Walmart", "Target", "Walgreens", "CVS");
        recipeAdverbs[1] = MessageUtility.choose("fluffy", "airy", "yellowish", "creamy");
        recipeAdverbs[2] = MessageUtility.choose("crispy", "crunchy", "dry");
        recipeAdverbs[3] = MessageUtility.choose("salty", "declious");
        String year = String.format("%04d", SmoreBot.getInstance().getRandom().nextInt(10000));
        String[] recipe = {
            String.format("Find some twigs in the forest that are from a **%s** tree", MessageUtility.choose("Birch", "Oak", "Spruce", "Redwood", "Water", "Ducky", "Discord")),
            String.format("Find a **%s** also so you can start a fire **%s**", fireLighter, MessageUtility.choose("amazingly", "normally")),
            String.format("Place the twigs down in a **%s** formation", MessageUtility.choose("star", "circle", "square", "octagon", "cylinder", "horse", "duck", "cow")),
            String.format("Light the fire using the **%s** you got earlier", fireLighter),
            String.format("While the fire is burning, go to your not-local **%s** for **%s** marshmallows, **%s** graham crackers, and **%s** off-brand Hersheys chocolate", recipeAdverbs),
            String.format("Run back to the fire you lit using the **%s** you bought earlier", fireLighter),
            String.format("Find the best stick of the year **%s** and stick one of your **%s** marshmallow on to it", year, recipeAdverbs[1]),
            String.format("Put the **%s** marshmallow over it until it turns **%s** or else it will burn", recipeAdverbs[1], MessageUtility.choose("brown", "purple", "blue")),
            String.format("Put the marshmallow and the **%s** off-brand Hersheys chocolate between 2 2x2 **%s** graham crackers and press firmly but not too firmly or else it will break", recipeAdverbs[3], recipeAdverbs[2]),
            String.format("Let it cool for **%s** seconds and enjoy it as it is the best smore of the year **%s**", SmoreBot.getInstance().getRandom().nextInt(100), year)
        };
        String msgRecipe = "**RECIPE FOR SMORE**";
        int i = 1;
        for (String recipeElement : recipe){
            msgRecipe += "\n" + i + ". " + recipeElement;
            i++;
        }
        MessageUtility.reply(message, msgRecipe);
    }
}
