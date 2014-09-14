/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge;

import java.util.List;

/**
 *
 * @author macbookpro
 */
public class Player implements Comparable {

    /**
     *
     */
    public enum PLAYER_STRATEGY {

        /**
         *
         */
        S, // SCISSORS

        /**
         *
         */
                P, // PAPER

        /**
         *
         */
                R; // ROCK 
    }
    
    private final String name;
    private final PLAYER_STRATEGY strategy;

    /**
     * @param playerInfo
     */
    public Player(List playerInfo) {
        if (playerInfo == null) {
            throw new RuntimeException("The player's data is null");
        }
        if (playerInfo.size() != 2) {
            throw new RuntimeException("The player must have a two fields: a name and a strategy");
        }
        String strat = (String) playerInfo.get(1);
        this.name = (String) playerInfo.get(0);
        this.strategy = PLAYER_STRATEGY.valueOf(strat.toUpperCase());
    }

    /**
     * @param name
     * @param strategy
     */
    public Player(String name, String strategy) {
        this.name = name;
        this.strategy = PLAYER_STRATEGY.valueOf(strategy.toUpperCase());
    }

    /**
     * @param name
     * @param strategy
     */    
    public Player(String name, PLAYER_STRATEGY strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    /**
     * @return 
     */
    public String[] toArray() {
        String[] result = { this.getName(), this.getStrategy().toString() };
        return result;
    }

    /**
     */    
    @Override
    public String toString() {
        return "[\"" + this.getName() + "\",\"" + this.getStrategy().toString() + "\"]";
    }

    /**
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public PLAYER_STRATEGY getStrategy() {
        return strategy;
    }

    /**
     *
     * @param data
     * @return
     */
    public boolean isAPlayer(List data) {
        return (data.get(0) instanceof String && data.get(1) instanceof String);
    }

    /**
     * @return
     */    
    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
