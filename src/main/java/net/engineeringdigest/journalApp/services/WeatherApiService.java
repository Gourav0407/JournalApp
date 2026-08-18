package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherApiService {

    private final AppCache appCache;

    private final String apiKey;

    private final RestTemplate template;

    private final RedisService redisService;

    @Autowired
    public WeatherApiService(@Value("${weather.api}") String apiKey, AppCache appCache, RestTemplate template, RedisService redisService) {
        this.apiKey = apiKey;
        this.appCache=appCache;
        this.template=template;
        this.redisService=redisService;
    }




    public WeatherResponse getWeather(String city){

        WeatherResponse weatherResponse= redisService.getValue("weather_for"+city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }

        URI uri= UriComponentsBuilder.fromUriString(appCache.getConfig(AppCache.Keys.WEATHER_API))
                .queryParam("q",city)
                .queryParam("limit",5)
                .queryParam("appid", apiKey)
                .build()
                .toUri();

        ResponseEntity<WeatherResponse> response=template.exchange(uri, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body=response.getBody();
        if(body!=null) redisService.setValue("weather_for"+city,body,600L);
        HttpStatus status=response.getStatusCode();
        return body;
    }

}