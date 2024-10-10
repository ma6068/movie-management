package com.moviemanagement.resource;

import com.moviemanagement.dto.CreateMovieDto;
import com.moviemanagement.dto.UpdateMovieDto;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.MovieService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoviesResource {

    private static final Logger logger = LoggerFactory.getLogger(MoviesResource.class);

    @Inject
    MovieService movieService;

    // List all movies
    @GET
    @Path("/listAll")
    public Response listAll() {
        try {
            logger.info("Fetching all movies");
            var movies = movieService.listAllMovies();
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
            var movies = movieService.listMoviesWithPagination(page, size);
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
            var movies = movieService.searchMoviesByTitle(title);
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
            var response = movieService.createMovie(createMovieDto);
            return Response.status(Response.Status.CREATED)
                .entity(new BasicResponse(response.isSuccess(), response.getMessage()))
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
            var response = movieService.updateMovie(updateMovieDto);
            return Response.ok(new BasicResponse(response.isSuccess(), response.getMessage())).build();
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
            var response = movieService.deleteMovie(imdbID);
            return Response.ok(new BasicResponse(response.isSuccess(), response.getMessage())).build();
        } catch (Exception e) {
            logger.error("Error deleting movie", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new BasicResponse(false, e.getMessage()))
                .build();
        }
    }

    // Find an actor by ID
    @GET
    @Path("/get/{imdbID}")
    public Response findById(@PathParam("imdbID") String imdbID) {
        try {
            logger.info("Fetching movie with IMDb ID: {}", imdbID);
            var movie = movieService.findMovieById(imdbID);
            if (movie == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new BasicResponse(true, "Movie not found"))
                    .build();
            }
            return Response.ok(movie).build();
        } catch (Exception e) {
            logger.error("Error fetching movie", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new BasicResponse(false, e.getMessage()))
                .build();
        }
    }
}
