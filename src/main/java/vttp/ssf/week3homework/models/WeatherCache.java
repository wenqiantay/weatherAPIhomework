package vttp.ssf.week3homework.models;

import org.springframework.stereotype.Component;

@Component
public class WeatherCache {
    
    private boolean isCached;
    private String payload;

    public boolean isCached() {
        return isCached;
    }
    public void setCached(boolean isCached) {
        this.isCached = isCached;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }

    public WeatherCache(){

    }
    
    public WeatherCache(boolean isCached, String payload) {
        this.isCached = isCached;
        this.payload = payload;
    }

    
}
