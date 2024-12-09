package vttp.ssf.week3homework.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp.ssf.week3homework.Repositories.WeatherRepository;
import vttp.ssf.week3homework.models.SearchParams;
import vttp.ssf.week3homework.models.WeatherCache;

@Service
public class WeatherService {
    
    private String GET_URL = "https://api.openweathermap.org/data/2.5/weather";
    private String DEFAULT_UNIT = "metrics";

    @Value("${api.key}")
    private String API_KEY;

    @Autowired
    private WeatherRepository weatherRepo;
    
    public Optional<WeatherCache> getWeather(SearchParams param){

        //check if in cache, get payload from cache
        if(weatherRepo.isCached(param.city())) {

            String payload = weatherRepo.getCache(param.city());

            WeatherCache weatherCache = new WeatherCache(true, payload);

            return Optional.of(weatherCache);
            
        }

        //if not in cache then get from API

        String url = UriComponentsBuilder
                    .fromUriString(GET_URL)
                    .queryParam("appid", API_KEY)
                    .queryParam("q", param.city())
                    .queryParam("units", DEFAULT_UNIT)
                    .toUriString();
        
        RequestEntity<Void> req = RequestEntity
                                .get(url)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();
        
        try {
            
            //Create a rest template
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody();

            //Save payload into cache
            weatherRepo.saveInCache(param.city(), payload);

            WeatherCache weatherCache = new WeatherCache(false, payload);

            return Optional.of(weatherCache);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            return Optional.empty();
        }
        
    }
}
