package com.moviemanagement.repository;

import com.moviemanagement.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class MoviesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // List all movies
    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }

    // List movies with pagination support
    public List<Movie> findAll(int page, int pageSize) {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    // Search for a movie by title
    public List<Movie> findByTitle(String title) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))", Movie.class)
            .setParameter("title", title)
            .getResultList();
    }

    // Create a new movie
    @Transactional
    public void create(Movie movie) {
        entityManager.persist(movie);
    }

    // Update an existing movie
    @Transactional
    public void update(Movie movie) {
        entityManager.merge(movie);
    }

    // Delete a movie by IMDb ID
    @Transactional
    public void delete(String imdbID) {
        Movie movie = entityManager.find(Movie.class, imdbID);
        if (movie != null) {
            entityManager.remove(movie);
        }
    }

    // Find a movie by IMDb ID
    public Movie findById(String imdbID) {
        return entityManager.find(Movie.class, imdbID);
    }
}
