package me.andrew28.smorebot.types;

import me.andrew28.smorebot.ConfigManager;

/**
 * @author Andrew Tran
 */
public enum Rank {
    NORMAL(0), HELPER(1), ADMIN(2);
    private int weight;
    private Rank(Integer weight){
        this.weight = weight;
    }
    public static Boolean inheritsRank(ConfigManager.SmoreUser user, Rank rank){
        return user.getRank().getWeight() >= rank.getWeight();
    }
    public Integer getWeight(){
        return weight;
    }
}
