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

@DatabaseTable(tableName = "champions")
public class Champions {

    @DatabaseField(generatedId = true)
    private int record_id;
    
    @DatabaseField
    private String name;
    
    @DatabaseField
    private int tournament_id;
    
    @DatabaseField
    private int points_earned;
    
    /**
     *
     */
    public Champions() {
        // ORMLite needs a no-arg constructor
    }

    /**
     *
     * @param name
     * @param tournament_id
     * @param points_earned
     */
    public Champions(String name, int tournament_id, int points_earned) {
        this.name = name;
        this.tournament_id = tournament_id;
        this.points_earned = points_earned;
    }

    
    /**
     * @return the record_id
     */
    public int getRecord_id() {
        return record_id;
    }

    /**
     * @param record_id the record_id to set
     */
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
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
     * @return the points_earned
     */
    public int getPoints_earned() {
        return points_earned;
    }

    /**
     * @param points_earned the points_earned to set
     */
    public void setPoints_earned(int points_earned) {
        this.points_earned = points_earned;
    }

}
