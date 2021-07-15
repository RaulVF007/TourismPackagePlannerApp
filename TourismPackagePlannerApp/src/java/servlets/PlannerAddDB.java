/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Planner;
import db.PlannerDBClient;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lab-DIS
 */
@WebServlet(name = "PlannerAddDB", urlPatterns = {"/PlannerAddDB"})
public class PlannerAddDB extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Planner planner = new Planner();
            PlannerDBClient plannerDBClient = new PlannerDBClient();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            String id = "1";
            int count = 1;
            //get next available id
            while(!plannerDBClient.find_JSON(String.class, id).equals("")){
                count = Integer.parseInt(id);
                count++;
                id = Integer.toString(count);
            }
            
            planner.setId(Integer.parseInt(id));
            planner.setFirstName(request.getParameter("firstName"));
            planner.setLastName(request.getParameter("lastName"));
            planner.setCheckIn(formatter.parse(formatter.format(formatter.parse(request.getParameter("checkIn")))));
            planner.setCheckOut(formatter.parse(formatter.format(formatter.parse(request.getParameter("checkOut")))));
            planner.setDestination(request.getParameter("destination"));
            planner.setHotel(request.getParameter("hotel"));
            planner.setFlight(request.getParameter("flight"));
            planner.setBudget(request.getParameter("budget"));
            
            plannerDBClient.create_JSON(planner);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlannerAddDB</title>");     
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Your plan's ID: "+id+"</h1>");
            out.println("<h1>Your plan has been succesfully saved! You can access this anytime from \"view plan\" in the main page using the provided ID.</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(PlannerAddDB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(PlannerAddDB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
