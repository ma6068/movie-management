package com.moviemanagement;

import com.moviemanagement.services.ActorService;
import com.moviemanagement.resource.ActorsResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActorsResourceTest_ListAll {

    @InjectMocks
    private ActorsResource actorsResource;

    @Mock
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll_Success() {
        // Mocking the service
        when(actorService.listAllActors()).thenReturn(Collections.emptyList());

        // Calling the method
        Response response = actorsResource.listAll();

        // Verifying the response
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(actorService, times(1)).listAllActors();
    }

    @Test
    void testListAll_Failure() {
        // Mocking an exception
        when(actorService.listAllActors()).thenThrow(new RuntimeException("Error fetching actors"));

        // Calling the method
        Response response = actorsResource.listAll();

        // Verifying the response
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
