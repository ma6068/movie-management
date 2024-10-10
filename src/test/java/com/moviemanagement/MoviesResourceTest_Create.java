package com.moviemanagement;

import com.moviemanagement.dto.CreateMovieDto;
import com.moviemanagement.enums.Genre;
import com.moviemanagement.resource.MoviesResource;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.MovieService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MoviesResourceTest_Create {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MoviesResource moviesResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testCreateMovie_Success() {
        // Arrange
        var pictures = new ArrayList<String>();
        pictures.add("https://via.placeholder.com/150/0000FF/808080 ?Text=PAKAINFO.com");
        pictures.add("https://via.placeholder.com/150/FF0000/FFFFFF?Text=yttags.com");

        CreateMovieDto dto = new CreateMovieDto();
        dto.setImdbID("43242634543534");
        dto.setTitle("New Movie Title");
        dto.setGenre(Genre.COMEDY);
        dto.setYearCreated(2020);
        dto.setDescription("New Movie Description");
        dto.setPictures(pictures);

        // Mocking the service response
        when(movieService.createMovie(any(CreateMovieDto.class)))
                .thenReturn(new BasicResponse(true, "Movie created successfully"));

        // Act
        Response response = moviesResource.create(dto);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(true, basicResponse.isSuccess());
        assertEquals("Movie created successfully", basicResponse.getMessage());

        // Verify that the service method was called once
        verify(movieService, times(1)).createMovie(any(CreateMovieDto.class));
    }

    @Test
    public void testCreateMovie_Error() {
        // Arrange
        var pictures = new ArrayList<String>();
        pictures.add("https://via.placeholder.com/150/0000FF/808080 ?Text=PAKAINFO.com");
        pictures.add("https://via.placeholder.com/150/FF0000/FFFFFF?Text=yttags.com");

        CreateMovieDto dto = new CreateMovieDto();
        dto.setImdbID("324324234324324");
        dto.setTitle("New Movie Title");
        dto.setGenre(Genre.COMEDY);
        dto.setYearCreated(2020);
        dto.setDescription("New Movie Description");
        dto.setPictures(pictures);

        // Mocking the service to throw an exception
        when(movieService.createMovie(any(CreateMovieDto.class)))
                .thenThrow(new RuntimeException("Movie creation failed"));

        // Act
        Response response = moviesResource.create(dto);

        // Assert
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(false, basicResponse.isSuccess());
        assertEquals("Error while trying to create new movie. Please check your data", basicResponse.getMessage());

        // Verify that the service method was called once
        verify(movieService, times(1)).createMovie(any(CreateMovieDto.class));
    }
}
