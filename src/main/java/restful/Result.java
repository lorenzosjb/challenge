/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;


import dao.Operations;
import java.io.IOException;
import java.io.PrintWriter;

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
        name = "Result",
        urlPatterns = "/api/championship/result",
        description = "Receives the championship data and computes it to identify the winner.",
        displayName = "Process a new championship")
public class Result extends HttpServlet {

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
        String status = "no given parameters";

        // Check parameters
        String first = request.getParameter("first");
        String second = request.getParameter("second");
        if (!EmptyOrNull(first) && !EmptyOrNull(second)) {
            // Save Champions to database
            Operations operation = new Operations();
            boolean saveFirstPlace = operation.saveChampion(first, true);
            boolean saveSecondPlace = operation.saveChampion(second, false);
            if (saveFirstPlace && saveSecondPlace)
                status = "success";
            else
                status = "error saving champions";
        }

        // Send Result of the operation
        try (PrintWriter out = response.getWriter()) {
            JSONObject obj = new JSONObject();
            obj.put("status", status);
            out.print(obj.toJSONString());
            out.flush();
        }
    }

    private boolean EmptyOrNull(String variable) {
        boolean result = true;
        if (variable != null) {
            result = variable.trim().equals("");
        }
        return result;
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
        return "Stores the first and second place of a tournament, each user is stored with their respective scores";
    }
    
}
