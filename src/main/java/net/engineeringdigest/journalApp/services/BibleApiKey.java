package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class BibleApiKey {
    private static String apiKey="RY2mXKH029aGgDaV20pwnHayPGTLy3DYdJdT2GHCc3e071be";
    private  static String URL="https://api.openweathermap.org/geo/1.0/direct?q=Ludhiana&limit=5&appid=62f3f6a9902e04bd7b1b15f3ac310c72";

    @Autowired
    private RestTemplate template;

    public WeatherResponse getQuote(String city){

        URI uri= UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("q",city)
                .queryParam("limit",5)
                .queryParam("appid",apiKey)
                .build()
                .toUri();

        HttpHeaders headers= new HttpHeaders();
        headers.set("key", "value");

        User user= User.builder().userName("fsfds").password("dsdfd").build();
        HttpEntity<User> entity= new HttpEntity<>(user,headers);

        String finalApi= URL.replace("CITY", city).replace("API_KEY",apiKey);
        ResponseEntity<WeatherResponse> response=template.exchange(finalApi, HttpMethod.POST,entity, WeatherResponse.class);
        WeatherResponse body=response.getBody();
        HttpStatus status=response.getStatusCode();
        return body;



    }
}
