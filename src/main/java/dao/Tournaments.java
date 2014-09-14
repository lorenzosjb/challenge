/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 *
 * @author macbookpro
 */

@DatabaseTable(tableName = "tournaments")
public class Tournaments {

    @DatabaseField(generatedId = true)
    private int tournament_id;
    
    @DatabaseField
    private String name;
       
    public Tournaments() {
        // ORMLite needs a no-arg constructor
    }
    
    public Tournaments(String name) {
        this.name = name;
    }

    /**
     * @return the tournament_id
     */
    public int getTournament_id() {
        return tournament_id;
    }

    /**
     * @param tournament_id the tournament_id to set
     */
    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    

}
