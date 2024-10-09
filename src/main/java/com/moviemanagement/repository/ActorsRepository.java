package com.moviemanagement.repository;

import com.moviemanagement.entity.Actor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ActorsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // List all actors
    public List<Actor> findAll() {
        return entityManager.createQuery("SELECT a FROM Actor a LEFT JOIN FETCH a.movies", Actor.class).getResultList();
    }

    // List actors with pagination support
    public List<Actor> findAll(int page, int pageSize) {
        return entityManager.createQuery("SELECT a FROM Actor a LEFT JOIN FETCH a.movies", Actor.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    // Create a new actor
    @Transactional
    public void create(Actor actor) {
        entityManager.persist(actor);
    }

    // Update an existing actor
    @Transactional
    public void update(Actor actor) {
        entityManager.merge(actor);
    }

    // Delete an actor by ID
    @Transactional
    public void delete(Long id) {
        Actor actor = entityManager.find(Actor.class, id);
        if (actor != null) {
            entityManager.remove(actor);
        }
    }

    // Find an actor by ID
    public Actor findById(Long id) {
        return entityManager.find(Actor.class, id);
    }

    // Find actors by IDs
    public List<Actor> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of(); // Return an empty list if no IDs are provided
        }
        return entityManager.createQuery("SELECT a FROM Actor a LEFT JOIN FETCH a.movies WHERE a.id IN :ids", Actor.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
