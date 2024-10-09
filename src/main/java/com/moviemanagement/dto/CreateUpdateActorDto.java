package com.moviemanagement.dto;

import com.moviemanagement.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;

public class CreateUpdateActorDto {

    private Long id;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "First name must be less than or equal to 255 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name must be less than or equal to 255 characters")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Past(message = "Born date must be a past date")
    @Temporal(TemporalType.DATE)
    private Date bornDate;

    private List<String> movieIds;

    // Constructors
    public CreateUpdateActorDto() {
    }

    public CreateUpdateActorDto(String firstName, String lastName, Gender gender, Date bornDate, List<String> movieIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.movieIds = movieIds;
    }

    // Getters and setters
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

    public List<String> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<String> movieIds) {
        this.movieIds = movieIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
