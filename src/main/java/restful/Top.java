/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

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

/**
 *
 * @author macbookpro
 */

@WebServlet(
        name = "Top", 
        urlPatterns = "/api/championship/top",
        description = "Retrieves the top players of all championships. Returns the list of player names based on the count provided.",
        displayName = "Retrieves the top players of all championships.")
public class Top extends HttpServlet {

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
        int count = getNumber(request.getParameter("count"));
        if (count < 1) {
            count = 10;
        }
        
        List players = new Operations().getTopChampionsNames(count);
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            obj.put("players", players);
            out.print(obj.toJSONString());
            out.flush();
        }
    }
    
    // Helpers
    
    private int getNumber(String value) {
        int result = -1;
        if (value != null) {
            String v = value.trim();
            try {
                result = Integer.parseInt(v);
            }
            catch (NumberFormatException ex) {
                result = -1;
            }
        }
        return result;
    }

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
        int result = new Operations().cleanChampions();
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            if (result == 1)
                obj.put("status", "success");
            else
                obj.put("status", "error");
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
        return "Retrieves the top players of all championships. Returns the list of player names based on the count provided.";
    }

}
