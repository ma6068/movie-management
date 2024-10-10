package com.moviemanagement;

import com.moviemanagement.dto.CreateUpdateActorDto;
import com.moviemanagement.enums.Gender;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.ActorService;
import com.moviemanagement.resource.ActorsResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ActorsResourceTest_Create {

    @InjectMocks
    private ActorsResource actorsResource;

    @Mock
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateActor_Success() {
        // Mock DTO
        CreateUpdateActorDto actorDto = new CreateUpdateActorDto();
        actorDto.setFirstName("Denzel");
        actorDto.setLastName("Washington");
        actorDto.setGender(Gender.MALE);
        actorDto.setBornDate(new Date(1954, 12, 28));

        // Mock service response
        when(actorService.createActor(actorDto)).thenReturn(new BasicResponse(true, "Actor created"));

        // Calling the method
        Response response = actorsResource.create(actorDto);

        // Verifying the response
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(actorService, times(1)).createActor(actorDto);
    }

    @Test
    void testCreateActor_Failure() {
        // Mock DTO
        CreateUpdateActorDto actorDto = new CreateUpdateActorDto();

        // Mocking an exception
        when(actorService.createActor(actorDto)).thenThrow(new RuntimeException("Error"));

        // Calling the method
        Response response = actorsResource.create(actorDto);

        // Verifying the response
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
