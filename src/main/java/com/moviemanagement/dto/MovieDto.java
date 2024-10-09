package com.moviemanagement.dto;

import com.moviemanagement.entity.Actor;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.enums.Genre;
import java.util.List;

public class MovieDto {

    private String imdbID;
    private String title;
    private int yearCreated;
    private Genre genre;
    private String description;
    private List<String> pictures;
    private List<ActorDto> actors;

    // Constructors
    public MovieDto() {}

    public MovieDto(String imdbID, String title, int yearCreated, Genre genre, String description, List<String> pictures, List<ActorDto> actors) {
        this.imdbID = imdbID;
        this.title = title;
        this.yearCreated = yearCreated;
        this.genre = genre;
        this.description = description;
        this.pictures = pictures;
        this.actors = actors;
    }

    // Getters and setters
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(int yearCreated) {
        this.yearCreated = yearCreated;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<ActorDto> getActors() {
        return actors;
    }

    public void setActors(List<ActorDto> actors) {
        this.actors = actors;
    }
}
