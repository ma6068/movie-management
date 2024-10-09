package com.moviemanagement.validation;

import com.moviemanagement.repository.MoviesRepository;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueImdbIDValidator implements ConstraintValidator<UniqueImdbID, String> {

    @Inject
    MoviesRepository moviesRepository;

    @Override
    public boolean isValid(String imdbID, ConstraintValidatorContext context) {
        if (imdbID != null && !imdbID.isBlank()) {
            return moviesRepository.findById(imdbID) == null;
        }
        return true;
    }
}
