/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import restful.RESTfulClientMoneda;

/**
 *
 * @author Lab-DIS
 */
public class Flights {
    
    private String currency; //Euros, Pounds, Dollars
    private String from;
    private String to;
    private String departureDate;
    private String returnDate;

    private String airline;
    private String rawPrice;
    private String leaveDate;
    
    private Double cost;
    
    public Flights(String currency, String from, String to, String departureDate, String returnDate) {
        this.currency = currency;
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }
    
    public void processUrl() throws JSONException{
        checkRoundTrip();
        
        String key = "e60df4b6e4mshd316d9a65794320p10a9f3jsndb8a2f189b99";
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/"+from+"/"+to+"/"+departureDate+"?"+returnDate+"rapidapi-key="+key+"&rapidapi-host=skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";
        //String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/SFO-sky/LAX-sky/2021-09-01?inboundpartialdate=2021-12-01&rapidapi-key=e60df4b6e4mshd316d9a65794320p10a9f3jsndb8a2f189b99&rapidapi-host=skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        String salida = target.request(MediaType.APPLICATION_JSON).get(String.class);

        JSONObject jsonObj = new JSONObject(salida);
        JSONArray jsonArrayCarriers = jsonObj.getJSONArray("Carriers");
        JSONObject jsonCarriers = jsonArrayCarriers.getJSONObject(0);

        JSONObject jsonDates = jsonObj.getJSONObject("Dates");
        JSONArray jsonArrayOutboundDates = jsonDates.getJSONArray("OutboundDates");
        JSONObject jsonOutboundDates = jsonArrayOutboundDates.getJSONObject(0);

        airline = jsonCarriers.getString("Name");
        rawPrice = jsonOutboundDates.getString("Price");
        leaveDate = jsonOutboundDates.getString("PartialDate");

        RESTfulClientMoneda m = new RESTfulClientMoneda();
        cost = Double.parseDouble(m.conversionCoin(String.class, rawPrice, currency)); //rest
    }
    
    public void checkRoundTrip(){
        if(returnDate.equals("")){
            returnDate = "";
        }
        returnDate = "inboundpartialdate"+returnDate+"&";
    }
    
    public String getAirline() {
        return airline;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public Double getCost() {
        return cost;
    }
}
