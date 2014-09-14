/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package challenge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author macbookpro
 */
public class Game {
    
    private Player[] players;
    
    /**
     * @param players
     */
    public Game(List players)
    {
        if (players == null) {
            throw new RuntimeException("There is no players to play the game");
        }
        if (players.size() != 2) {
            throw new RuntimeException("The game can be played only with two players");
        }
        
        int i = 0;
        this.players = new Player[2];
        Iterator it = players.iterator();
        while (it.hasNext())
        {
            List playerInfo = (List) it.next();
            this.players[i++] = new Player(playerInfo);  
        }
    }
    
    /**
     * @return
     */
    public List Play()
    {
        Player p = null;
        
        int first = 1;       
        try {
            // Check if there is a draw
            if (this.players[0].getStrategy() == this.players[1].getStrategy()) {
                first = 0;
            }
            // Check if first player is the winner
            else if (this.players[0].getStrategy() == Player.PLAYER_STRATEGY.R &&
                        this.players[1].getStrategy() == Player.PLAYER_STRATEGY.S) {
                first = 0;
            }
            else if (this.players[0].getStrategy() == Player.PLAYER_STRATEGY.S &&
                        this.players[1].getStrategy() == Player.PLAYER_STRATEGY.P) {
                first = 0;
            }
            else if (this.players[0].getStrategy() == Player.PLAYER_STRATEGY.P &&
                        this.players[1].getStrategy() == Player.PLAYER_STRATEGY.R) {
                first = 0;
            }
            p = new Player(this.players[first].getName(), this.players[first].getStrategy());
        }
        catch(Exception e) {
            throw new RuntimeException("Error playing with the players");
        }
        
        List result = new ArrayList();
        result.add(p.getName());
        result.add(p.getStrategy().toString());
        return result;
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return "[" + this.players[0].toString() + "," + this.players[1].toString() + "]";
    }
    
}
