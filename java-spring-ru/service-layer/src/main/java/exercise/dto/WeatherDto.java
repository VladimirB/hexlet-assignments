package exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherDto(@JsonProperty("name") String name,
                         @JsonProperty("temperature") int temperature,
                         @JsonProperty("cloudy") String cloudy,
                         @JsonProperty("wind") String wind,
                         @JsonProperty("humidity") String humidity) {}
