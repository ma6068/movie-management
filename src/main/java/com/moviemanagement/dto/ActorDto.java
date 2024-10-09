package com.moviemanagement.dto;

import com.moviemanagement.enums.Gender;

import java.util.Date;
import java.util.List;

public class ActorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date bornDate;
    private List<MovieDto> movies;

    // Constructors
    public ActorDto() {
    }

    public ActorDto(Long id, String firstName, String lastName, Gender gender, Date bornDate, List<MovieDto> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.movies = movies;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public List<MovieDto> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }
}
