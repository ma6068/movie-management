package com.moviemanagement;

import com.moviemanagement.resource.MoviesResource;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.MovieService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MoviesResourceTest_Delete {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MoviesResource moviesResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        String imdbID = "tt1234567";

        // Mocking the service response
        when(movieService.deleteMovie(imdbID)).thenReturn(new BasicResponse(true, "Movie deleted successfully"));

        // Act
        Response response = moviesResource.delete(imdbID);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(true, basicResponse.isSuccess());
        assertEquals("Movie deleted successfully", basicResponse.getMessage());

        // Verify that the service method was called once
        verify(movieService, times(1)).deleteMovie(imdbID);
    }

    @Test
    public void testDelete_NotFound() {
        // Arrange
        String imdbID = "tt1234567";

        // Mocking the service to return a failure response (not found scenario)
        when(movieService.deleteMovie(imdbID)).thenReturn(new BasicResponse(false, "Movie not found"));

        // Act
        Response response = moviesResource.delete(imdbID);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(false, basicResponse.isSuccess());
        assertEquals("Movie not found", basicResponse.getMessage());

        // Verify that the service method was called once
        verify(movieService, times(1)).deleteMovie(imdbID);
    }

    @Test
    public void testDelete_Error() {
        // Arrange
        String imdbID = "tt1234567";

        // Mocking the service to throw an exception
        when(movieService.deleteMovie(imdbID)).thenThrow(new RuntimeException("Error deleting movie"));

        // Act
        Response response = moviesResource.delete(imdbID);

        // Assert
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(false, basicResponse.isSuccess());
        assertEquals("Error deleting movie", basicResponse.getMessage());

        // Verify that the service method was called once
        verify(movieService, times(1)).deleteMovie(imdbID);
    }
}
