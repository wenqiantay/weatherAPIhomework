package vttp.ssf.week3homework.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp.ssf.week3homework.models.SearchParams;
import vttp.ssf.week3homework.models.Weather;
import vttp.ssf.week3homework.models.WeatherCache;
import vttp.ssf.week3homework.services.WeatherService;

@Controller
@RequestMapping
public class WeatherController {
    
    @Autowired
    private WeatherService weatherSvc;

    @GetMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getWeather(@RequestParam MultiValueMap<String,String> queryParam){

        ModelAndView mav = new ModelAndView();

        SearchParams param = new SearchParams(queryParam.getFirst("cityname"));

        Optional<WeatherCache> optWeatherCache = weatherSvc.getWeather(param);

        WeatherCache weatherCache = optWeatherCache.get();

        String weatherPayload = weatherCache.getPayload();

        Weather weather = Weather.toWeather(weatherPayload);

        mav.setViewName("weather");
        mav.addObject("cityname", param.city());
        mav.addObject("weather", weather);
        mav.addObject("isCached", weatherCache.isCached());

        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

}
