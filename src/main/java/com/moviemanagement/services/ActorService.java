package com.moviemanagement.services;

import com.moviemanagement.dto.*;
import com.moviemanagement.entity.Actor;
import com.moviemanagement.mapper.ActorMapper;
import com.moviemanagement.repository.ActorsRepository;
import com.moviemanagement.repository.MoviesRepository;
import com.moviemanagement.response.BasicResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ActorService {

    @Inject
    ActorsRepository actorsRepository;

    @Inject
    MoviesRepository moviesRepository;

    public List<ActorDto> listAllActors() {
        var actors = actorsRepository.findAll();
        return ActorMapper.toDtoList(actors);
    }

    public List<ActorDto> listActorsWithPagination(int page, int size) {
        var actors = actorsRepository.findAll(page, size);
        return ActorMapper.toDtoList(actors);
    }

    public BasicResponse createActor(CreateUpdateActorDto createActorDto) {
        var actorMovies = moviesRepository.findByIds(createActorDto.getMovieIds());
        var actor = ActorMapper.toEntity(createActorDto, actorMovies);
        actorsRepository.create(actor);
        return new BasicResponse(true, "Actor created successfully");
    }

    public BasicResponse updateActor(CreateUpdateActorDto updateActorDto) {
        Actor actor = actorsRepository.findById(updateActorDto.getId());
        if (actor == null) {
            return new BasicResponse(false, "Movie not found");
        }
        var actorMovies = moviesRepository.findByIds(updateActorDto.getMovieIds());
        ActorMapper.toEntity(actor, updateActorDto, actorMovies);
        actorsRepository.update(actor);
        return new BasicResponse(true, "Actor updated successfully");
    }

    public BasicResponse deleteActor(Long id) {
        Actor actor = actorsRepository.findById(id);
        if (actor == null) {
            return new BasicResponse(false, "Actor not found");
        }
        actorsRepository.delete(id);
        return new BasicResponse(true, "Actor deleted successfully");
    }

    public ActorDto findActorById(Long id) {
        var actor = actorsRepository.findById(id);
        return ActorMapper.toDto(actor);
    }
}
