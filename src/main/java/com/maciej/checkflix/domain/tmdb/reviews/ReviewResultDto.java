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
public class ResultDto {
    @JsonProperty("author")
    public String author;

    @JsonProperty("author_details")
    public AuthorDetailsDto authorDetails;

    @JsonProperty("content")
    public String content;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("id")
    public String id;

    @JsonProperty("updated_at")
    public String updatedAt;

    @JsonProperty("url")
    public String url;
}
