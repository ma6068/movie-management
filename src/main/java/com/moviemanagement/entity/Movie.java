package com.moviemanagement.entity;

import com.moviemanagement.enums.Genre;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "imdb_id", nullable = false, unique = true)
    private String imdbID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year_created")
    private int yearCreated;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "movie_pictures", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "picture_url")
    private List<String> pictures;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

    // Constructors
    public Movie() {
    }

    public Movie(String imdbID, String title, int yearCreated, Genre genre, String description, List<String> pictures) {
        this.imdbID = imdbID;
        this.title = title;
        this.yearCreated = yearCreated;
        this.genre = genre;
        this.description = description;
        this.pictures = pictures;
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

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
