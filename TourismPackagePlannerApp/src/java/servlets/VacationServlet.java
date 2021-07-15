/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import org.json.JSONException;
import soap.SOAPFlightCalculator_Service;

/**
 *
 * @author Lab-DIS
 */
@WebServlet(name = "VacationServlet", urlPatterns = {"/VacationServlet"})
public class VacationServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/ws.docencia.ces.siani.es/a18/TrabajoIS_SOAP_FlightCalculator/SOAPFlightCalculator.wsdl")
    private SOAPFlightCalculator_Service service;

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
            throws ServletException, IOException, JSONException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String city = request.getParameter("city");
            String countryCode = request.getParameter("countryCode");
            String unit = request.getParameter("unit");
            String currency = request.getParameter("currency");
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String departureDate = request.getParameter("departureDate");
            String returnDate = request.getParameter("returnDate");
            String rooms = request.getParameter("rooms");
            String adults = request.getParameter("adults");
            String minors = request.getParameter("minors");
            String budget = request.getParameter("budget");
            
            Weather weather = new Weather(city, unit, Miscellaneous.getDateDifference(departureDate));
            weather.processUrl();
            
            Flights flights = new Flights(currency, from, to, departureDate, returnDate);
            flights.processUrl();
            
            GeoLocation geoLoc = new GeoLocation(city, countryCode);
            geoLoc.processUrl();
            
            Hotels hotels = new Hotels(geoLoc.getLat(), geoLoc.getLon(), departureDate, returnDate, rooms);
            hotels.processUrl();
            
            Double total = Double.parseDouble(budget)-calculateFlightPrice(Integer.parseInt(minors), Integer.parseInt(adults), flights.getCost()); //soap
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Weather at: " + city + " in arrival</h1>");
            out.println("<h2>"+weather.getWeatherMain()+"</h2>");
            out.println("<h3>"+weather.getWeatherDesc()+"</h3>");
            out.println("<p>Temperature: "+weather.getMainTemp()+unit+"  |   Feels like: "+weather.getMainFeelT()+unit+"</p>");
            out.println("<p>Max: "+weather.getMainTMax()+unit+"  |   Min: "+weather.getMainTMin()+unit+"</p>");
            out.println("<p>Humidity: "+weather.getMainHumidity()+"</p>");
            out.println("<p>Wind speed: "+weather.getWindSpeed()+"</p>");
            out.println("<br>");
            out.println("<h1>Fly with: " + flights.getAirline() + "</h1>");
            out.println("<h2>Price of flight: "+Miscellaneous.decimalNormalizer(calculateFlightPrice(Integer.parseInt(minors), Integer.parseInt(adults), flights.getCost()))+Miscellaneous.currencySymbol(currency)+"</h2>");
            out.println("<p>Departure date: "+departureDate+"  |   Return date: "+returnDate+"</p>");
            out.println("<br>");
            out.println("<h1>" + "Hotels:" + "</h1>");
            out.println("<p>List of hotels in "+city+" with their star rating and an image of the hotel, unfurtunately, prices for the hotels at the specified times cannot be retrieved so please, look for a hotel you find pleasing for your vacation and look up their price at their website</p>");
            for(String hotelName: hotels.getHotels()){
               out.println("<h4>Hotel: " + hotelName + "</h4>"); 
            }
            out.println("<h1>" + "Landmarks in: " + city + "</h1>");
            for(String landmarks: hotels.getLandmarks()){
               out.println("<p>" + landmarks + "</p>");
            }
            out.println("<br>");
            out.println("<h1>Remaining budget: "+Miscellaneous.decimalNormalizer(total)+Miscellaneous.currencySymbol(currency)+"</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    //calculateFlightPrice(Integer.parseInt(minors), Integer.parseInt(adults), Double.parseDouble(flights.getCost()))
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
        } catch (JSONException ex) {
            Logger.getLogger(VacationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VacationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(VacationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VacationServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private double calculateFlightPrice(int childrenNumber, int adultNumber, double flightPrice) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        soap.SOAPFlightCalculator port = service.getSOAPFlightCalculatorPort();
        return port.calculateFlightPrice(childrenNumber, adultNumber, flightPrice);
    }

    
}
