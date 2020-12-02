package com.maciej.checkflix.omdbservice.mapper;

import com.maciej.checkflix.omdbservice.domain.Movie;
import com.maciej.checkflix.omdbservice.domain.MovieDto;
import com.maciej.checkflix.omdbservice.domain.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieDto mapToMovieDto(Movie movie) {
        return new MovieDto(
            movie.getTitle(),
                movie.getYear(),
                movie.getImdbID(),
                movie.getType().getLabel(),
                movie.getPoster()
        );
    }

    public Movie mapToMovie(MovieDto movieDto) {
        return new Movie(
            movieDto.getTitle(),
            movieDto.getYear(),
            movieDto.getImdbID(),
            Type.from(movieDto.getType()),
            movieDto.getPoster()
        );
    }

    public List<MovieDto> mapToMovieDtoList(List<Movie> movieList) {
        return movieList.stream()
                .map(this::mapToMovieDto)
                .collect(Collectors.toList());
    }

    public List<Movie> mapToMovieList(List<MovieDto> movieDtoList) {
        return movieDtoList.stream()
                .map(this::mapToMovie)
                .collect(Collectors.toList());
    }
}
