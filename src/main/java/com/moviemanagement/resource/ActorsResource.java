package com.moviemanagement.resource;

import com.moviemanagement.dto.CreateUpdateActorDto;
import com.moviemanagement.response.BasicResponse;
import com.moviemanagement.services.ActorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorsResource {

    private static final Logger logger = LoggerFactory.getLogger(ActorsResource.class);

    @Inject
    ActorService actorService;

    // List all actors
    @GET
    @Path("/listAll")
    public Response listAll() {
        try {
            logger.info("Fetching all actors");
            var actors = actorService.listAllActors();
            return Response.ok(actors).build();
        } catch (Exception e) {
            logger.error("Error fetching actors", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // List actors with pagination support
    @GET
    @Path("/list/page={page}/size={size}")
    public Response listActors(@PathParam("page") int page, @PathParam("size") int size) {
        try {
            logger.info("Fetching actors with pagination page: {}, size: {}", page, size);
            var actors = actorService.listActorsWithPagination(page, size);
            return Response.ok(actors).build();
        } catch (Exception e) {
            logger.error("Error fetching paginated actors", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Create a new actor
    @POST
    @Path("/create")
    public Response create(@Valid CreateUpdateActorDto createActorDto) {
        try {
            logger.info("Creating a new actor: {}", createActorDto.getFirstName() + " " + createActorDto.getLastName());
            var response = actorService.createActor(createActorDto);
            return Response.status(Response.Status.CREATED)
                    .entity(new BasicResponse(response.isSuccess(), response.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Error creating actor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, "Error while trying to create new actor. Please check your data"))
                    .build();
        }
    }

    // Update an existing actor
    @PUT
    @Path("/update/{actorId}")
    public Response update(@PathParam("actorId") Long actorId, @Valid CreateUpdateActorDto updateActorDto) {
        try {
            logger.info("Updating actor with ID: {}", actorId);
            var response = actorService.updateActor(updateActorDto);
            return Response.ok(new BasicResponse(response.isSuccess(), response.getMessage())).build();
        } catch (Exception e) {
            logger.error("Error updating actor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Delete an actor
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            logger.info("Deleting actor with ID: {}", id);
            var response = actorService.deleteActor(id);
            return Response.ok(new BasicResponse(response.isSuccess(), response.getMessage())).build();
        } catch (Exception e) {
            logger.error("Error deleting actor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }

    // Find an actor by ID
    @GET
    @Path("/get/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            logger.info("Fetching actor with ID: {}", id);
            var actor = actorService.findActorById(id);
            if (actor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new BasicResponse(true, "Actor not found"))
                    .build();
            }
            return Response.ok(actor).build();
        } catch (Exception e) {
            logger.error("Error fetching actor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BasicResponse(false, e.getMessage()))
                    .build();
        }
    }
}
