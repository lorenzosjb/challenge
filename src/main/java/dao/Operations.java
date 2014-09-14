/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author macbookpro
 */
public class Operations {
    
    private final String DATABASE_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql552195?zeroDateTimeBehavior=convertToNull"; 
    private final String DATABASE_USR = "sql552195";
    private final String DATABASE_PSW = "mN2%dX6*";
    
    private ConnectionSource connectionSource;
    
    private void connectConnection()
    {
        try {
            this.connectionSource = new JdbcConnectionSource(DATABASE_URL);
            ((JdbcConnectionSource)this.connectionSource).setUsername(DATABASE_USR);
            ((JdbcConnectionSource)this.connectionSource).setPassword(DATABASE_PSW);            
        } catch (Exception ex) {
            this.connectionSource = null;
        }
    }
    
    /**
     *
     * @param name
     * @return
     */
    public int addTournament(String name)
    {
        int result = -1;
        if (getTournamentId(name) < 0) {
            connectConnection();
            try {
                if (this.connectionSource != null) {            
                    Dao<Tournaments,String> tournamentsDao = DaoManager.createDao(this.connectionSource, Tournaments.class);
                    Tournaments t = new Tournaments();
                    t.setName(name);
                    result = tournamentsDao.create(t);
                    this.connectionSource.close();
                }
            } catch (Exception ex) {
                result = -1;
            }
        }
        return result;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public String getTournamentById(int id)
    {
        String result = "";
        connectConnection();
        try {
            if (this.connectionSource != null) {            
                Dao<Tournaments,String> tournamentsDao = DaoManager.createDao(this.connectionSource, Tournaments.class);
                Tournaments t = tournamentsDao.queryForId(Integer.toString(id));
                if (t != null) {
                    result = t.getName();
                }
                this.connectionSource.close();
            }
        } catch (Exception ex) { 
            result = null;
        }
        return result;
    }
    
    /**
     *
     * @param name
     * @return
     */
    public int getTournamentId(String name)
    {
        int result = -1;
        connectConnection();
        try {
            if (this.connectionSource != null) {            
                Dao<Tournaments,String> tournamentsDao = DaoManager.createDao(this.connectionSource, Tournaments.class);
                List<Tournaments> results = tournamentsDao.queryBuilder().where().eq("name", name).query();
                if (results != null) {
                    result = ((Tournaments)results.get(0)).getTournament_id();
                }
                this.connectionSource.close();
            }
        } catch (Exception ex) { 
            result = -1;
        }
        return result;
    }
    
    /**
     *
     * @param name
     * @param tournament_id
     * @param pointsEarned
     * @return
     */
    public int addChampion(String name, int tournament_id, int pointsEarned)
    {
        int result = -1;
        connectConnection();
        try {
            if (this.connectionSource != null) {
                Dao<Champions,String> championsDao = DaoManager.createDao(this.connectionSource, Champions.class);
                Champions c = new Champions();
                c.setName(name.toLowerCase());
                c.setPoints_earned(pointsEarned);
                c.setTournament_id(tournament_id);
                result = championsDao.create(c);
                this.connectionSource.close();
            }
        } catch (Exception ex) {
            result = -1;
        }
        return result;
    }
    
    /**
     *
     * @param champion
     * @return
     */
    public int updateChampion(Champions champion)
    {
        int result = -1;
        connectConnection();
        try {
            if (this.connectionSource != null) {            
                Dao<Champions,String> championsDao = DaoManager.createDao(this.connectionSource, Champions.class);
                result = championsDao.update(champion);
                this.connectionSource.close();
            }   
        } catch (Exception ex) {
            result = -1;
        }
        return result;
    }
    
    /**
     *
     * @param name
     * @return
     */
    public Champions getChampion(String name)
    {
        Champions result = null;
        connectConnection();
        try {
            if (this.connectionSource != null) {
                Dao<Champions,String> championsDao = DaoManager.createDao(this.connectionSource, Champions.class);
                List<Champions> results = championsDao.queryBuilder().where().eq("name", name.toLowerCase()).query();
                if (results != null) {
                    result = ((Champions)results.get(0));
                }
                this.connectionSource.close();
            }
        } catch (Exception ex) { 
            result = null;
        }
        return result;
    }
    
    /**
     *
     * @param championsName
     * @param isFirstPlace
     * @return
     */
    public boolean saveChampion(String championsName, boolean isFirstPlace)
    {
        boolean success;
        Champions champion = getChampion(championsName);
        if (champion == null) {
            int pointEarned = 1;
            if (isFirstPlace) {
                pointEarned = 3;
            }
            int result = addChampion(championsName, 1, pointEarned);
            success = (result == 1);
        }
        else {
            if (isFirstPlace)
                champion.setPoints_earned(champion.getPoints_earned() + 3);
            else
                champion.setPoints_earned(champion.getPoints_earned() + 1);
            int result = updateChampion(champion);
            success = (result == 1);
        }
        return success;
    }

    /**
     *
     * @param count
     * @return
     */
    
    public List getTopChampionsNames(int count) {
        List result = new ArrayList();
        connectConnection();
        try {
            if (this.connectionSource != null) {
                Dao<Champions,String> championsDao = DaoManager.createDao(this.connectionSource, Champions.class);
                GenericRawResults<String[]> rawResults = championsDao.queryRaw(
                            "select name from champions order by points_earned desc LIMIT " + count);
                List queryResults = rawResults.getResults();
                this.connectionSource.close();
                if (queryResults != null) {
                    Iterator it = queryResults.iterator();
                    while (it.hasNext()) {
                        String[] player = (String[])it.next();
                        result.add(Character.toString(player[0].charAt(0)).toUpperCase() + player[0].substring(1));
                    }
                }        
            }
        } catch (Exception ex) { 
            result = null;
        }
        return result;
    }
    
    /**
     *
     * @param count
     * @return
     */
    
    public int cleanChampions() {
        int result = -1;
        connectConnection();
        try {
            if (this.connectionSource != null) {
                Dao<Champions,String> championsDao = DaoManager.createDao(this.connectionSource, Champions.class);
                result = championsDao.updateRaw("delete from champions where record_id > 0");
            }
        } catch (Exception ex) { 
            result = -1;
        }
        return result;
    }
    
}
