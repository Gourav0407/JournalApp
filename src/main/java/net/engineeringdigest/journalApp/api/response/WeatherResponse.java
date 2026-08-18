package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    private Data data;

    @Getter
    @Setter
    public class Data{
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int humidity;
        private int visibility;
        private int windDeg;
    }



}
