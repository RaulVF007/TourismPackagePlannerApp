/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.util.ArrayList;
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
public class Hotels {
    private ArrayList<String> Hotels;
    private ArrayList<String> Landmarks;
    
    private String lat;
    private String lon;
    private String checkIn;
    private String checkOut;
    private String rooms;

    public Hotels(String lat, String lon, String checkIn, String checkOut, String rooms) {
        this.Landmarks = new ArrayList<>();
        this.Hotels = new ArrayList<>();
        this.lat = lat;
        this.lon = lon;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.rooms = rooms;
    }
    
    public void processUrl() throws JSONException{
        String key = "892e793c04mshbf4f5e3a9a52d97p123074jsn24ad8a380007";
        String url = "https://hotels-com-free.p.rapidapi.com/srle/listing/v1/brands/hotels.com?lat="+lat+"&lon="+lon+"&checkIn="+checkIn+"&checkOut="+checkOut+"&rooms="+rooms+"&locale=en_US&amp;currency=USD&pageNumber=1&rapidapi-key="+key+"&rapidapi-host=hotels-com-free.p.rapidapi.com";
        //String url = "https://hotels-com-free.p.rapidapi.com/srle/listing/v1/brands/hotels.com?lat=40.4168&lon=-3.7038&checkIn=2021-01-27&checkOut=2021-01-28&rooms=1&locale=en_US&currency=USD&pageNumber=1&rapidapi-key=892e793c04mshbf4f5e3a9a52d97p123074jsn24ad8a380007&rapidapi-host=hotels-com-free.p.rapidapi.com";
            
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        String salida = target.request(MediaType.APPLICATION_JSON).get(String.class);
            
        JSONObject jsonObj = new JSONObject(salida);
        JSONObject jsonData = jsonObj.getJSONObject("data");
        JSONObject jsonBody = jsonData.getJSONObject("body");        
        JSONObject jsonSerachResults = jsonBody.getJSONObject("searchResults");
        JSONArray jsonArrayResults = jsonSerachResults.getJSONArray("results");
        
        Hotels.clear();
        Landmarks.clear();
        getHotelInfo(jsonArrayResults);
    }
            
    public void getHotelInfo(JSONArray jsonArrayResults) throws JSONException{
        int i = 0;
        int max = 10; //implementar como parametro de entrada
        while(i < jsonArrayResults.length()){
            JSONObject jsonHotel = jsonArrayResults.getJSONObject(i);
            Hotels.add(jsonHotel.getString("name")+"    |   Star rating: "+jsonHotel.getString("starRating")
                +"  |   <a href=\""+jsonHotel.getJSONObject("optimizedThumbUrls").getString("srpDesktop")+"\">Imagen</a>");
            JSONArray jsonArrayLandmarks = jsonHotel.getJSONArray("landmarks");
            int j = 0;
            while(j < jsonArrayLandmarks.length()){
                JSONObject jsonLandmark = jsonArrayLandmarks.getJSONObject(j);
                if(!Landmarks.contains(jsonLandmark.getString("label"))){
                    Landmarks.add(jsonLandmark.getString("label"));
                }
                j++;
            }
            i++;
        }
    }

    public ArrayList<String> getHotels() {
        return Hotels;
    }

    public ArrayList<String> getLandmarks() {
        return Landmarks;
    }
    
}
