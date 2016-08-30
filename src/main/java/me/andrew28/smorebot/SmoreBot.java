package me.andrew28.smorebot;

import me.andrew28.smorebot.types.*;
import me.andrew28.smorebot.util.ClassFinder;
import me.andrew28.smorebot.util.MessageUtility;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.*;

/**
 * @author Andrew Tran
 */
public class SmoreBot {
    public String token;
    public String tokenCensored = "";
    private ArrayList<CommandEntity> commands = new ArrayList<>();

    public static BufferedReader getConsole() {
        return console;
    }

    private static BufferedReader console;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    private Timer timer;

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    private Random random;

    private static SmoreBot instance;

    private JDA JDA;

    private String currentGame;

    public ArrayList<CommandEntity> getCommands() {
        return commands;
    }

    public static void main(String[] args){

        if (console == null){
            console = new BufferedReader(new InputStreamReader(System.in));
        }

        if (instance != null){
            if (instance.getTimer() != null){
                instance.getTimer().cancel();
            }
        }
        instance = new SmoreBot();
        getInstance().setTimer(new Timer());
        getInstance().setRandom(new Random());

        if (args.length < 1){
            System.out.println("No token specified!");
            System.exit(0);
        }

        getInstance().setToken(args[0]);

        String token = getInstance().getToken();
        String tokenCensored = "";
        for (Integer i = 15; i < token.length() - 20; i++){
            tokenCensored += "*";
        }
        tokenCensored = token.substring(0, 15) + tokenCensored + token.substring(token.length() - 20, token.length());
        getInstance().setTokenCensored(tokenCensored);

        System.out.println("me.andrew28.smorebot.SmoreBot by xXAndrew28Xx");
        System.out.println("Token: " + tokenCensored);

        try {
            getInstance().setJDA(new JDABuilder().setBotToken(token).addListener(new MessageListener()).buildBlocking());
            System.out.println("Successfully authenicated with Discord");

        } catch (LoginException e) {
            System.out.println("INVALID TOKEN: " + token);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknown Error: ");
            e.printStackTrace();
        }

        ConfigManager.init();
        System.out.println("Config initiated.");

        System.out.println("Registering commands in me.andrew28.smorebot.commands");
        getInstance().searchCommands("me.andrew28.smorebot.commands");
        System.out.println("Finished registering commands.");


        getInstance().getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                getInstance().updateGame();
            }
        }, 0, 35 * 1000);
        new Thread(() -> {
            //Asynchronously
            while(true){
                try {
                    String command = getConsole().readLine();
                    System.out.println("> " + command);
                    if (command.equalsIgnoreCase("update game")){
                        getInstance().updateGame();
                        System.out.println("Game has been updated to " + getInstance().getCurrentGame());
                    }else if (command.equalsIgnoreCase("stop")){
                        System.out.println("Stopping SmoreBot..");
                        System.exit(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenCensored() {
        return tokenCensored;
    }

    public void setTokenCensored(String tokenCensored) {
        this.tokenCensored = tokenCensored;
    }

    public static SmoreBot getInstance() {
        return instance;
    }

    public static void setInstance(SmoreBot instance) {
        SmoreBot.instance = instance;
    }

    public JDA getJDA() {
        return JDA;
    }

    public void setJDA(JDA JDA) {
        this.JDA = JDA;
    }

    public void updateGame(){
        List<User> users = getJDA().getUsers();
        Boolean found = false;
        String game = "";
        while (!found){
            User user = users.get(getRandom().nextInt(users.size() - 1));
            if (user.getCurrentGame() != null){
                game = user.getCurrentGame().getName();
                found = true;
            }
        }
        currentGame = game;
        getJDA().getAccountManager().setGame(game);
    }

    public String getCurrentGame(){
        return currentGame;
    }

    public void searchCommands(String packageName){
        Class klass = SmoreBot.class;

        CodeSource codeSource = klass.getProtectionDomain().getCodeSource();
        File file = null;
        try {
            file = new File(URLDecoder.decode(codeSource.getLocation().getPath(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Could not detect jar file ran to search for commands in");
            e.printStackTrace();
        }
        for (Class clazz : ClassFinder.getClasses(file, packageName)){
            if (ICommand.class.isAssignableFrom(clazz)){
                if (clazz.isAnnotationPresent(Command.class)){
                    try {
                        registerCommand((ICommand) clazz.newInstance());
                    } catch (Exception e){
                        System.out.println("Failed to register command " + clazz.getCanonicalName());
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(clazz.getCanonicalName() + " does not have the annotation me.andrew28.smorebot.types.Command");
                }
            }
        }
    }

    public void registerCommand(ICommand command){
        if (command.getClass().isAnnotationPresent(Command.class)){
            CommandEntity ce = new CommandEntity(command, command.getClass().getDeclaredAnnotation(Command.class));
            commands.add(ce);
            System.out.println("Registered command: " + command.getClass().getCanonicalName());
        }else{
            throw new IllegalArgumentException("ICommand doesn't have the annotation me.andrew28.smorebot.types.Command");
        }
    }
    /*public String getHelp(){
        String help = "SmoreBot Help: " + "\n" + "RANK : **COMMAND** : HELP" + "\n```";
        ArrayList<CommandEntity> commands = getInstance().getCommands();
        commands.sort(new Comparator<CommandEntity>() {
            @Override
            public int compare(CommandEntity o1, CommandEntity o2) {
                return o1.getAnnotation().requiredRank().getWeight().compareTo(o2.getAnnotation().requiredRank().getWeight());
            }
        });
        for (CommandEntity ce : commands){
            Command annotation = ce.getAnnotation();
            help += "\n : " + annotation.requiredRank() + " : **" + annotation.command() + "** : " + annotation.help();
        }
        help += "```";
        return help;
    }*/
    public String getHelp(){
        String help = "```xl\nSmoreBot is a fun bot made by @Andrew#1763\n```\n";
        for (Integer i = 0; i < 20; i++){
            help = "\n" + help;
        }
        help = "-\n" + help;
        ArrayList<CommandEntity> commands = getInstance().getCommands();
        commands.sort(new Comparator<CommandEntity>() {
            @Override
            public int compare(CommandEntity o1, CommandEntity o2) {
                return o1.getAnnotation().requiredRank().getWeight().compareTo(o2.getAnnotation().requiredRank().getWeight());
            }
        });

        Rank[] ranks = {Rank.NORMAL, Rank.HELPER, Rank.ADMIN};
        for (Rank rank : ranks){
            help += "\n```xl\n" + rank.name().toUpperCase() + ": " + "\n";
            for (CommandEntity ce : commands){
                if (ce.getAnnotation().requiredRank() == rank){
                    help += "\n#\n" + ce.getCommand().getClass().getSimpleName();
                    help += "\n" + ce.getAnnotation().command();
                    help += "\n" + ce.getAnnotation().usage();
                    help += "\n" + ce.getAnnotation().help();
                    help += "\n#\n";
                }
            }
            help += "\n```";
        }
        return help;
    }
    public boolean checkRank(Command annotation, MessageReceivedEvent e){
        if (annotation.requiredRank() != Rank.NORMAL){
            ConfigManager.SmoreUser user = ConfigManager.getUser(e.getAuthor().getId());
            if(!Rank.inheritsRank(user, annotation.requiredRank())){
                MessageUtility.reply(e.getMessage(), "You don't quite have the correct permissions! You need " + annotation.requiredRank().name() + " or higher! :floppy_disk:");
                return false;
            }
        }
        return true;
    }
    /**
     * @author Andrew Tran
     */
    public static class MessageListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent e){
            if (e.getAuthor().getId().equals("219970974047666176")){
                return;
            }
            if (e.isPrivate()){
                MessageUtility.reply(e.getMessage(), SmoreBot.getInstance().getHelp());
            }else{
                Iterator<CommandEntity> it = ((ArrayList<CommandEntity>)getInstance().getCommands().clone()).iterator();
                while (it.hasNext()){
                    CommandEntity ce = it.next();
                    Command annotation = ce.getAnnotation();
                    ICommand command = ce.getCommand();
                    if (command instanceof MatchCommand){
                        if (annotation.caseSensetive()){

                            if (e.getMessage().getStrippedContent().equals(annotation.command())){
                                if (!getInstance().checkRank(annotation, e)){
                                    return;
                                }
                                ((MatchCommand) command).handle(e.getAuthor(), e.getMessage(), e);
                                System.out.println(e.getAuthor().getUsername() + " has activated " + command.getClass().getCanonicalName());
                            }
                        }else{

                            if (e.getMessage().getStrippedContent().equalsIgnoreCase(annotation.command())){
                                if (!getInstance().checkRank(annotation, e)){
                                    return;
                                }
                                ((MatchCommand) command).handle(e.getAuthor(), e.getMessage(), e);
                                System.out.println(e.getAuthor().getUsername() + " has activated " + command.getClass().getCanonicalName());
                            }
                        }
                    }else if (command instanceof RegexCommand){
                        String regex = annotation.command();
                        if (!annotation.caseSensetive()){
                            regex = "(?i)" + regex; //Makes regex caseinsensetive
                        }
                        if (e.getMessage().getStrippedContent().matches(regex)){
                            if (!getInstance().checkRank(annotation, e)){
                                return;
                            }
                            ((RegexCommand) command).handle(e.getAuthor(), e.getMessage(), e);
                            System.out.println(e.getAuthor().getUsername() + " has activated " + command.getClass().getCanonicalName());
                        }
                    }else if (command instanceof StartsWithCommand){
                        if (!annotation.caseSensetive()){
                            if (e.getMessage().getStrippedContent().toLowerCase().startsWith(annotation.command().toLowerCase())){
                                if (!getInstance().checkRank(annotation, e)){
                                    return;
                                }
                                String message = e.getMessage().getStrippedContent();
                                ((StartsWithCommand) command).handle(e.getAuthor(), e.getMessage(), message.substring(annotation.command().length()), e);
                                System.out.println(e.getAuthor().getUsername() + " has activated " + command.getClass().getCanonicalName());
                            }
                        }else{
                            if (e.getMessage().getStrippedContent().startsWith(annotation.command())){
                                if (!getInstance().checkRank(annotation, e)){
                                    return;
                                }
                                String message = e.getMessage().getStrippedContent();
                                ((StartsWithCommand) command).handle(e.getAuthor(), e.getMessage(), message.substring(annotation.command().length()), e);
                                System.out.println(e.getAuthor().getUsername() + " has activated " + command.getClass().getCanonicalName());
                            }
                        }
                    }
                }
            }
        }
    }
}
