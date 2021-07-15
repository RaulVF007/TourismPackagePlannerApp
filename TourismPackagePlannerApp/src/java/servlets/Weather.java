package servlets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import restful.RESTfulClientConversor;

public class Weather {
    
    private String city;
    private String u;
    private Long dateDifference;
    
    private String mainTemp;
    private String mainFeelT;
    private String mainTMin;
    private String mainTMax;
    private String mainHumidity;
    
    private String weatherDesc;
    private String weatherMain;
    
    private String windSpeed;
    String key = "af8c9b20c128c59eb1955068bfc7c9a4";
    
    public Weather(String city, String u, Long dateDifference) throws JSONException {
        this.city = city;
        this.u = u;
        this.dateDifference = dateDifference;
    }
    
    public void processUrl() throws JSONException{
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+key; 
        //String url = "http://api.openweathermap.org/data/2.5/forecast?q=Madrid&appid="+key; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        String salida = target.request(MediaType.APPLICATION_JSON).get(String.class);
        
        JSONObject jsonObj = new JSONObject(salida);
        JSONArray jsonArrayList = jsonObj.getJSONArray("list");
        JSONObject jsonList = jsonArrayList.getJSONObject(Integer.parseInt(dateDifference.toString())); //index is selected day

        JSONObject jsonMain = new JSONObject(jsonList.getString("main"));
        JSONObject jsonWind = new JSONObject(jsonList.getString("wind"));
        JSONArray jsonArrayWeather = jsonList.getJSONArray("weather");
        JSONObject jsonWeather = jsonArrayWeather.getJSONObject(0);

        //From main
        mainTemp = jsonMain.getString("temp");
        mainFeelT = jsonMain.getString("feels_like");
        mainTMin = jsonMain.getString("temp_min");
        mainTMax = jsonMain.getString("temp_max");
        mainHumidity = jsonMain.getString("humidity");

        //From weather
        weatherMain = jsonWeather.getString("main");
        weatherDesc = jsonWeather.getString("description");

        //From wind
        windSpeed = jsonWind.getString("speed");
        
    }
    
    public String convertTemp(String T, String u){
        //Convert temperature unit
        RESTfulClientConversor tempConverter = new RESTfulClientConversor();
        String convertedTemp = tempConverter.conversionTemp(String.class, T, u); //temperatura en kelvin y (C o F) temperatura a convertir
        String[] temp = convertedTemp.split("\\.");
        return temp[0];
    }
    
    public String getCity() {
        return city;
    }

    public String getMainTemp() {
        return convertTemp(mainTemp, u);
    }

    public String getMainFeelT() {
        return convertTemp(mainFeelT, u);
    }

    public String getMainTMin() {
        return convertTemp(mainTMin, u);
    }

    public String getMainTMax() {
        return convertTemp(mainTMax, u);
    }

    public String getMainHumidity() {
        return mainHumidity;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
    
}
