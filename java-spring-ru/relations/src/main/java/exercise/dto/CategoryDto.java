package exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryDto(@JsonProperty("id") long id,
                          @JsonProperty("name") String name) {
}
