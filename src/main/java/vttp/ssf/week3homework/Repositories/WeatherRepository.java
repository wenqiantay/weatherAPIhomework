package vttp.ssf.week3homework.Repositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepository {
    
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    //Exist city
    public boolean isCached(String city){

        return template.hasKey(city);
    }

    //Set city payload ex 600
    public void saveInCache(String city, String payload){

        ValueOperations<String, String> valueOps = template.opsForValue();

        valueOps.set(city, payload, Duration.ofMinutes(10));
    }

    //get city
    public String getCache(String city){

        ValueOperations<String, String> valueOps = template.opsForValue();

        return valueOps.get(city);
    }
}
