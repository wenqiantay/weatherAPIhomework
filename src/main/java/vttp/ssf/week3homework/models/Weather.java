package vttp.ssf.week3homework.models;

import java.io.StringReader;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Component
public class Weather {
    
    private String id;
    private String main;
    private String description;
    private String icon;
    private String cityName;
    
    //getter and setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    //Json to Weather
    public static Weather toWeather(String json){

        Weather weather = new Weather();

        try {

            JsonReader reader = Json.createReader(new StringReader(json));
            JsonObject jsonObj = reader.readObject();
            JsonArray weatherArray = jsonObj.getJsonArray("weather");
            
            JsonObject weatherObject = weatherArray.getJsonObject(0);

            weather.setId(String.valueOf(weatherObject.getInt("id")));
            weather.setMain(weatherObject.getString("main"));
            weather.setDescription(weatherObject.getString("description"));
            weather.setIcon(weatherObject.getString("icon"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
}
