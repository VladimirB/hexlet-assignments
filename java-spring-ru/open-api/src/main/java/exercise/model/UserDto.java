package exercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(@JsonProperty("id") long id,
                      @JsonProperty("first_name") String firstName,
                      @JsonProperty("last_name") String lastName,
                      @JsonProperty("email") String email) {
}
