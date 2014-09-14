/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import challenge.Player;
import challenge.Tournament;
import dao.Operations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author macbookpro
 */
@WebServlet(
        name = "New",
        urlPatterns = "/api/championship/new",
        description = "Stores the first and second place of a tournament, each user is stored with their respective scores.",
        displayName = "Stores the first and second place of a tournament")
public class New extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String data = request.getParameter("data");
        try (PrintWriter out = response.getWriter()) {
            if (!EmptyOrNull(data)) {
                out.print(processChampionship(data));
            } else {
                out.print(getJSON("status", "no given parameter"));
            }
            out.flush();
        }
    }

    // Helpers
    private boolean EmptyOrNull(String variable) {
        boolean result = true;
        if (variable != null) {
            result = variable.trim().equals("");
        }
        return result;
    }

    private String getJSON(String name, String data) {
        // Send Result of the operation
        JSONObject obj = new JSONObject();
        obj.put(name, data);
        return obj.toJSONString();
    }

    private String processChampionship(String data) {

        // Convert String to an ArrayList
        List matches;
        try {
            String contents = "{\"data\":" + data + "}";
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(contents);
            JSONObject json = (JSONObject) obj;
            matches = (List) json.get("data");
        } catch (Exception e) {
            return getJSON("status", "Error in championship's data");
        }

        // Process the championship
        if (matches != null) {
            try {
                Tournament tournament = new Tournament("", matches);
                List firstPlace = tournament.getFirstPlace();
                List secondPlace = tournament.getSecondPlace();
                Operations operation = new Operations();
                boolean saveFirstPlace = operation.saveChampion(new Player(firstPlace).getName(), true);
                boolean saveSecondPlace = operation.saveChampion(new Player(secondPlace).getName(), false);
                if (saveFirstPlace && saveSecondPlace) {
                    return getJSON("winner", new Player(firstPlace).toString());
                } else {
                    return getJSON("status", "Error saving the champions records");
                }
            } catch (Exception ex) {
                return getJSON("status", "Error processing the championship's data");
            }
        } else {
            return getJSON("status", "Error format of championship's data");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            obj.put("status", "not implemented");
            out.print(obj.toJSONString());
            out.flush();
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            obj.put("status", "not implemented");
            out.print(obj.toJSONString());
            out.flush();
        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            obj.put("status", "not implemented");
            out.print(obj.toJSONString());
            out.flush();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Receives the championship data and computes it to identify the winner. "
                + "The first and second place are stored into a database with their respectives scores. "
                + "Returns the winner of the championship";
    }

}
