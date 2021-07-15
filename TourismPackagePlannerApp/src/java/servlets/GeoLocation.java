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

/**
 *
 * @author Lab-DIS
 */
public class GeoLocation {
    private String city;
    private String countryCode;
    private String lat;
    private String lon;
    private String limit = "10";
    private String key = "af8c9b20c128c59eb1955068bfc7c9a4";

    public GeoLocation(String city, String countryCode) {
        this.city = city;
        this.countryCode = countryCode;
    }
    
    public void processUrl() throws JSONException{
        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+city+"&limit="+limit+"&appid="+key;
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        String salida = target.request(MediaType.APPLICATION_JSON).get(String.class);
        
        JSONArray jsonArray = new JSONArray(salida);
        int i = 0;
        while(i < jsonArray.length()){
            JSONObject jsonArrayPos = jsonArray.getJSONObject(i);
            if(jsonArrayPos.getString("country").equals(countryCode)){
                lat = jsonArrayPos.getString("lat");
                lon = jsonArrayPos.getString("lon");
                break;
            }
            i++;
        }
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
    
}
