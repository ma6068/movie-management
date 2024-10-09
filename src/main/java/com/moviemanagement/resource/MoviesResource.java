package com.moviemanagement.resource;

import com.moviemanagement.dto.CreateMovieDto;
import com.moviemanagement.dto.UpdateMovieDto;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.repository.MoviesRepository;
import com.moviemanagement.response.BasicResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoviesResource {

    private static final Logger logger = LoggerFactory.getLogger(MoviesResource.class);

    @Inject
    MoviesRepository moviesRepository;

    // List all movies
    @GET
    @Path("/listAll")
    public Response listAll() {
        try {
            logger.info("Fetching all movies");
            List<Movie> movies = moviesRepository.findAll();
            return Response.ok(movies).build();
        } catch (Exception e) {
            logger.error("Error fetching movies", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // List movies with pagination support
    @GET
    @Path("/list/page={page}/size={size}")
    public Response listMovies(@PathParam("page") int page, @PathParam("size") int size) {
        try {
            logger.info("Fetching movies with pagination page: {}, size: {}", page, size);
            List<Movie> movies = moviesRepository.findAll(page, size);
            return Response.ok(movies).build();
        } catch (Exception e) {
            logger.error("Error fetching paginated movies", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Search movie by title
    @GET
    @Path("/search")
    public Response search(@QueryParam("title") String title) {
        try {
            logger.info("Searching for movies with title: {}", title);
            List<Movie> movies = moviesRepository.findByTitle(title);
            return Response.ok(movies).build();
        } catch (Exception e) {
            logger.error("Error searching for movies", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Create a new movie
    @POST
    @Path("/create")
    public Response create(@Valid CreateMovieDto createMovieDto) {
        try {
            logger.info("Creating a new movie with IMDb ID: {}", createMovieDto.getImdbID());
            moviesRepository.create(convertToEntity(createMovieDto));
            return Response.status(Response.Status.CREATED)
                .entity(new BasicResponse(true, "Movie created successfully"))
                .build();
        } catch (Exception e) {
            logger.error("Error creating movie", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new BasicResponse(false, "Error while trying to create new movie. Please check your data"))
                .build();
        }
    }

    // Update an existing movie
    @PUT
    @Path("/update")
    public Response update(@Valid UpdateMovieDto updateMovieDto) {
        try {
            logger.info("Updating movie with IMDb ID: {}", updateMovieDto.getImdbID());

            // check if movie exist
            var movie = moviesRepository.findById(updateMovieDto.getImdbID());
            if (movie == null) {
                return Response.ok(new BasicResponse(false,"Movie with that IMDb ID does not exist")).build();
            }

            // update movie
            moviesRepository.update(convertToEntity(movie, updateMovieDto));
            return Response.ok(new BasicResponse(true,"Movie updated successfully")).build();
        } catch (Exception e) {
            logger.error("Error updating movie", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Delete a movie
    @DELETE
    @Path("/delete/{imdbID}")
    public Response delete(@PathParam("imdbID") String imdbID) {
        try {
            logger.info("Deleting movie with IMDb ID: {}", imdbID);

            // check if movie exist
            var movie = moviesRepository.findById(imdbID);
            if (movie == null) {
                return Response.ok(new BasicResponse(false,"Movie with that IMDb ID does not exist")).build();
            }

            // delete movie
            moviesRepository.delete(imdbID);
            return Response.ok(new BasicResponse(true,"Movie deleted successfully")).build();
        } catch (Exception e) {
            logger.error("Error deleting movie", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    private Movie convertToEntity(CreateMovieDto dto) {
        // Conversion logic from DTO to Entity
        //List<Actor> actors = actorsRepository.findAllById(dto.getActorIds());
        return new Movie(dto.getImdbID(), dto.getTitle(), dto.getYearCreated(), dto.getGenre(), dto.getDescription(), dto.getPictures(), null);
    }

    private Movie convertToEntity(Movie movie, UpdateMovieDto dto) {
        // Conversion logic from DTO to Entity
        //List<Actor> actors = actorsRepository.findAllById(dto.getActorIds());
        movie.setTitle(dto.getTitle());
        movie.setYearCreated(dto.getYearCreated());
        movie.setGenre(dto.getGenre());
        movie.setDescription(dto.getDescription());
        movie.setPictures(dto.getPictures());
        return movie;
    }
}
