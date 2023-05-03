package exercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(@JsonProperty("id") int id,
                     @JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName) {
}