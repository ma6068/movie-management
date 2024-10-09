package com.moviemanagement.mapper;

import com.moviemanagement.dto.ActorDto;
import com.moviemanagement.dto.CreateMovieDto;
import com.moviemanagement.dto.MovieDto;
import com.moviemanagement.dto.UpdateMovieDto;
import com.moviemanagement.entity.Actor;
import com.moviemanagement.entity.Movie;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    public static MovieDto toDto(Movie movie) {
        List<ActorDto> actorDtos = movie.getActors().stream()
            .map(actor -> new ActorDto(actor.getFirstName(), actor.getLastName(), actor.getGender(), actor.getBornDate()))
            .collect(Collectors.toList());

        return new MovieDto(movie.getImdbID(), movie.getTitle(), movie.getYearCreated(), movie.getGenre(),
                movie.getDescription(), movie.getPictures(), actorDtos);
    }

    public static List<MovieDto> toDtoList(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Movie toEntity(CreateMovieDto dto, List<Actor> actors) {
        return new Movie(dto.getImdbID(), dto.getTitle(), dto.getYearCreated(), dto.getGenre(),
                dto.getDescription(), dto.getPictures(), actors);
    }

    public static void toEntity(Movie movie, UpdateMovieDto dto, List<Actor> actors) {
        movie.setTitle(dto.getTitle());
        movie.setYearCreated(dto.getYearCreated());
        movie.setGenre(dto.getGenre());
        movie.setDescription(dto.getDescription());
        movie.setPictures(dto.getPictures());
        movie.setActors(actors);
    }
}
