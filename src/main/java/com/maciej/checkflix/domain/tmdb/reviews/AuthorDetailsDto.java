package com.maciej.checkflix.domain.tmdb.reviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDetailsDto {
    @JsonProperty("name")
    public String name;

    @JsonProperty("username")
    public String username;

    @JsonProperty("avatar_path")
    public Object avatarPath;

    @JsonProperty("rating")
    public Object rating;
}
