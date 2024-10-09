package com.moviemanagement.resource;

import com.moviemanagement.dto.ActorDto;
import com.moviemanagement.entity.Actor;
import com.moviemanagement.repository.ActorsRepository;
import com.moviemanagement.response.BasicResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorsResource {

    private static final Logger logger = LoggerFactory.getLogger(ActorsResource.class);

    @Inject
    ActorsRepository actorsRepository;

    // List all actors
    @GET
    @Path("/listAll")
    public Response listAll() {
        try {
            logger.info("Fetching all actors");
            List<Actor> actors = actorsRepository.findAll();
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
            List<Actor> actors = actorsRepository.findAll(page, size);
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
    public Response create(@Valid ActorDto actorDto) {
        try {
            logger.info("Creating a new actor: {}", actorDto.getFirstName() + " " + actorDto.getLastName());
            actorsRepository.create(convertToEntity(actorDto));
            return Response.status(Response.Status.CREATED)
                    .entity(new BasicResponse(true, "Actor created successfully"))
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
    public Response update(@PathParam("actorId") Long actorId, @Valid ActorDto actorDto) {
        try {
            logger.info("Updating actor with ID: {}", actorId);

            // check if actor exists
            var actor = actorsRepository.findById(actorId);
            if (actor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new BasicResponse(false, "Actor with that ID does not exist"))
                    .build();
            }

            // update actor
            actorsRepository.update(convertToEntity(actor, actorDto));
            return Response.ok(new BasicResponse(true, "Actor updated successfully")).build();
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

            // check if actor exists
            var actor = actorsRepository.findById(id);
            if (actor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new BasicResponse(false, "Actor with that ID does not exist"))
                    .build();
            }

            // delete actor
            actorsRepository.delete(id);
            return Response.ok(new BasicResponse(true, "Actor deleted successfully")).build();
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
            var actor = actorsRepository.findById(id);
            if (actor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new BasicResponse(false, "Actor not found"))
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

    private Actor convertToEntity(ActorDto dto) {
        return new Actor(dto.getFirstName(), dto.getLastName(), dto.getGender(), dto.getBornDate());
    }

    private Actor convertToEntity(Actor actor, ActorDto dto) {
        actor.setFirstName(dto.getFirstName());
        actor.setLastName(dto.getLastName());
        actor.setGender(dto.getGender());
        actor.setBornDate(dto.getBornDate());
        return actor;
    }
}
