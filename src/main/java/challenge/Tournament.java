/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author macbookpro
 */
public final class Tournament {

    private List players;
    private String name;

    /**
     *
     * @param name
     * @param players
     */
    public Tournament(String name, List players) {
        if (players == null) {
            throw new RuntimeException("There is no players for the tournament");
        }
        if (players.size() < 2) {
            throw new RuntimeException("The tournament must have at least 2 players");
        }
        this.name = name;
        this.players = players;
    }

    /**
     *
     * @param fileName
     */
    public Tournament(String fileName) {
        loadTournament(fileName);
    }

    private boolean isAPlayer(List data) {
        if (data != null) {
            if (data.size() == 2) {
                return (data.get(0) instanceof String && data.get(1) instanceof String);
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public List getFirstPlace() {
        return getFirstPlace(this.players);
    }

    /**
     *
     * @return
     */
    public List getSecondPlace() {
        return getSecondPlace(this.players);
    }

    private List getFirstPlace(List players) {
        List list1 = (List) players.get(0);
        List list2 = (List) players.get(1);
        if (isAPlayer(list1) && isAPlayer(list2)) {
            return new Game(players).Play();
        } else {
            List winner1 = getFirstPlace(list1);
            List winner2 = getFirstPlace(list2);
            List match = new ArrayList();
            match.add(winner1);
            match.add(winner2);
            return new Game(match).Play();
        }
    }

    private List getSecondPlace(List players) {
        List list1 = (List) players.get(0);
        List list2 = (List) players.get(1);
        if (isAPlayer(list1) && isAPlayer(list2)) {
            return new Game(players).Play();
        } else {
            List player1 = getFirstPlace(list1);
            List player2 = getFirstPlace(list2);
            List match = new ArrayList();
            match.add(player1);
            match.add(player2);
            if (isTheSamePlayer(new Game(match).Play(), player2)) {
                return player1;
            } else {
                return player2;
            }
        }
    }

    /**
     *
     * @param player1
     * @param player2
     * @return
     */
    public boolean isTheSamePlayer(List player1, List player2) {
        if (isAPlayer(player1) && isAPlayer(player2)) {
            String namePlayer = (String) player1.get(0);
            String strategy = (String) player1.get(1);
            return (namePlayer.equals((String) player2.get(0))
                    && strategy.equals((String) player2.get(1)));
        }
        return false;
    }

    /**
     *
     */
    public void saveTournamentToFile() {
        BufferedWriter writer = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("matches", this.players);
            obj.put("name", this.name);

            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            writer = new BufferedWriter(new FileWriter(this.name.replace(" ", "") + ".txt"));
            writer.write(out.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error saving the tournament to file");
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     *
     * @param fileName
     */
    public void loadTournament(String fileName) {
        this.players = new ArrayList();
        try {
            String contents = getContents(fileName);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(contents);
            JSONObject json = (JSONObject) obj;
            this.players = (List) json.get("matches");
            this.name = (String) json.get("name");
        } catch (Exception e) {
            throw new RuntimeException("Error loading the tournament");
        }
    }

    private String getContents(String fileName) {
        String contents = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            contents = sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error reading the tournament file");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return contents;
    }

}
