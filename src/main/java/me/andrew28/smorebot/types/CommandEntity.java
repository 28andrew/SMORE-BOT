package me.andrew28.smorebot.types;

import me.andrew28.smorebot.types.Command;
import me.andrew28.smorebot.types.ICommand;

/**
 * @author Andrew Tran
 */
public class CommandEntity {
    private ICommand command;
    private Command annotation;

    public CommandEntity(ICommand command, Command annotation){
        this.command = command;
        this.annotation = annotation;
    }

    public ICommand getCommand() {
        return command;
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public Command getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Command annotation) {
        this.annotation = annotation;
    }


}
