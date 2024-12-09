package vttp.ssf.week3homework;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {

    private final Logger logger = Logger.getLogger(AppConfig.class.getName());
    
    //Get all the redis configuration into the class --> using dependency injection
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.database}")
    private int redisDatabase;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean("redis-0") //only give primitive types, dont get java object
    public RedisTemplate<String, String> createRedisTemplate(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        //Sets the database (> select 0)
        config.setDatabase(redisDatabase);
        //Set the user name and password if they are not set
        if(!redisUsername.trim().equals("")){
            logger.info("Setting Redis username and password");
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        //Create a connection to the database
        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        //Create a factory to connect to Redis
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        //Create the redis template
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); //key of the map
        
        return redisTemplate;
    }
}

