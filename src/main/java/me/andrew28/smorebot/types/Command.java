package me.andrew28.smorebot.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Andrew Tran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {
    String command();
    String help();
    String usage();
    boolean caseSensetive() default true;
    Rank requiredRank() default Rank.NORMAL;
}
