package com.moviemanagement.services;

import com.moviemanagement.dto.CreateMovieDto;
import com.moviemanagement.dto.MovieDto;
import com.moviemanagement.dto.UpdateMovieDto;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.mapper.MovieMapper;
import com.moviemanagement.repository.ActorsRepository;
import com.moviemanagement.repository.MoviesRepository;
import com.moviemanagement.response.BasicResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MovieService {

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    ActorsRepository actorsRepository;

    public List<MovieDto> listAllMovies() {
        var movies = moviesRepository.findAll();
        return MovieMapper.toDtoList(movies);
    }

    public List<MovieDto> listMoviesWithPagination(int page, int size) {
        var movies = moviesRepository.findAll(page, size);
        return MovieMapper.toDtoList(movies);
    }

    public List<MovieDto> searchMoviesByTitle(String title) {
        var movies = moviesRepository.findByTitle(title);
        return MovieMapper.toDtoList(movies);
    }

    public BasicResponse createMovie(CreateMovieDto createMovieDto) {
        var movieActors = actorsRepository.findByIds(createMovieDto.getActorIds());
        var movie = MovieMapper.toEntity(createMovieDto, movieActors);
        moviesRepository.create(movie);
        return new BasicResponse(true, "Movie created successfully");
    }

    public BasicResponse updateMovie(UpdateMovieDto updateMovieDto) {
        Movie movie = moviesRepository.findById(updateMovieDto.getImdbID());
        if (movie == null) {
            return new BasicResponse(false, "Movie not found");
        }
        var movieActors = actorsRepository.findByIds(updateMovieDto.getActorIds());
        MovieMapper.toEntity(movie, updateMovieDto, movieActors);
        moviesRepository.update(movie);
        return new BasicResponse(true, "Movie updated successfully");
    }

    public BasicResponse deleteMovie(String imdbID) {
        Movie movie = moviesRepository.findById(imdbID);
        if (movie == null) {
            return new BasicResponse(false, "Movie not found");
        }
        moviesRepository.delete(imdbID);
        return new BasicResponse(true, "Movie deleted successfully");
    }

    public MovieDto findMovieById(String imdbID) {
        var movie = moviesRepository.findById(imdbID);
        return MovieMapper.toDto(movie);
    }
}
