package com.moviemanagement.dto;

import com.moviemanagement.enums.Genre;
import jakarta.validation.constraints.*;

import java.util.List;

public class UpdateMovieDto {
    @NotNull(message = "IMDb ID cannot be null")
    @NotBlank(message = "IMDb ID cannot be blank")
    @Size(min = 7, max = 50, message = "IMDb ID must be between 7 and 50 characters")
    private String imdbID;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be less than or equal to 255 characters")
    private String title;

    @Min(value = 1888, message = "Year must be no earlier than 1888")
    @Max(value = 2024, message = "Year must be no later than 2024")
    private int yearCreated;

    //@NotNull(message = "Genre cannot be null")
    private Genre genre;

    @Size(max = 5000, message = "Description must be less than or equal to 5000 characters") // Assuming TEXT type in DB can hold up to 5000 chars
    private String description;

    @NotEmpty(message = "At least one picture URL must be provided")
    private List<@NotBlank(message = "Picture URL cannot be blank") @Size(max = 255, message = "Picture URL must be less than or equal to 255 characters") String> pictures;

    // Constructors
    public UpdateMovieDto() {}

    public UpdateMovieDto(String imdbID, String title, int yearCreated, Genre genre, String description, List<String> pictures) {
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
}
