package com.moviemanagement;

import com.moviemanagement.dto.CreateUpdateActorDto;
import com.moviemanagement.enums.Gender;
import com.moviemanagement.resource.ActorsResource;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.ActorService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActorsResourceTest_Update {

    @InjectMocks
    private ActorsResource actorsResource;

    @Mock
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testUpdateActor_Success() {
        // Arrange
        CreateUpdateActorDto dto = new CreateUpdateActorDto();
        dto.setFirstName("Denzel");
        dto.setLastName("Washington");
        dto.setGender(Gender.MALE);
        dto.setBornDate(new Date(1954, 12, 28));

        when(actorService.updateActor(any(CreateUpdateActorDto.class)))
                .thenReturn(new BasicResponse(true, "Actor updated successfully"));

        // Act
        Response response = actorsResource.update(dto);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(true, basicResponse.isSuccess());
        assertEquals("Actor updated successfully", basicResponse.getMessage());

        verify(actorService, times(1)).updateActor(any(CreateUpdateActorDto.class));
    }

    @Test
    public void testUpdateActor_Error() {
        // Arrange
        CreateUpdateActorDto dto = new CreateUpdateActorDto();
        when(actorService.updateActor(any(CreateUpdateActorDto.class)))
                .thenThrow(new RuntimeException("Actor update failed"));

        // Act
        Response response = actorsResource.update(dto);

        // Assert
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        BasicResponse basicResponse = (BasicResponse) response.getEntity();
        assertEquals(false, basicResponse.isSuccess());
        assertEquals("Actor update failed", basicResponse.getMessage());

        verify(actorService, times(1)).updateActor(any(CreateUpdateActorDto.class));
    }
}
