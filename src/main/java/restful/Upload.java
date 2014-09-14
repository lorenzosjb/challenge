/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import challenge.Player;
import challenge.Tournament;
import dao.Operations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author macbookpro
 */
@WebServlet(
        name = "Upload",
        urlPatterns = "/api/championship/upload",
        description = "Upload the championship data and computes it to identify the winner.",
        displayName = "Process a new championship")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1, // 1 MB
        maxFileSize         = 1024 * 1024 * 1, // 1 MB
        maxRequestSize      = 1024 * 1024 * 1) // 1 MB
public class Upload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load File
        Part filePart = request.getPart("file");
        InputStream filecontent = filePart.getInputStream();
        InputStreamReader is = new InputStreamReader(filecontent);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();
        while (read != null) {
            sb.append(read);
            read = br.readLine();
        }

        // Process championship
        String result = "";
        if (!EmptyOrNull(sb.toString())) {
            result = processChampionship(sb.toString());
        }
        
        // Save results
        request.getSession().setAttribute("result", result);
        response.sendRedirect("../../newChampionship.jsp");
    }

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
