package exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherShortDto(@JsonProperty("name") String cityName,
                              @JsonProperty("temperature") String temperature) {}
