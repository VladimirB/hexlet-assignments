package exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArticleDto(@JsonProperty("name") String name,
                         @JsonProperty("body") String body,
                         @JsonProperty("category") CategoryDto category) {
}
