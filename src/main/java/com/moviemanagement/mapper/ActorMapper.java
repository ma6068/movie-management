package com.moviemanagement.mapper;

import com.moviemanagement.dto.ActorDto;
import com.moviemanagement.dto.CreateUpdateActorDto;
import com.moviemanagement.entity.Actor;
import com.moviemanagement.entity.Movie;
import java.util.List;
import java.util.stream.Collectors;

public class ActorMapper {

    // Create ActorDto from Actor entity
    public static ActorDto toDto(Actor actor) {
        if (actor == null) {
            return null; // Handle null case
        }
        return new ActorDto(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getGender(), actor.getBornDate(), null);
    }

    // Create list of Actor dtos from list of Actor entities
    public static List<ActorDto> toDtoList(List<Actor> actors) {
        return actors.stream()
                .map(ActorMapper::toDto)
                .collect(Collectors.toList());
    }

    // Create Actor from CreateUpdateActorDto
    public static Actor toEntity(CreateUpdateActorDto dto, List<Movie> movies) {
        if (dto == null) {
            return null; // Handle null case
        }
        return new Actor(dto.getFirstName(), dto.getLastName(), dto.getGender(), dto.getBornDate(), movies);
    }

    // Convert ActorDto to Actor entity
    public static void toEntity(Actor actor, CreateUpdateActorDto dto, List<Movie> movies) {
        actor.setFirstName(dto.getFirstName());
        actor.setLastName(dto.getLastName());
        actor.setGender(dto.getGender());
        actor.setBornDate(dto.getBornDate());
        actor.setMovies(movies);
    }
}
