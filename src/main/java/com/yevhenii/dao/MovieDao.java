package com.yevhenii.dao;

import com.yevhenii.model.Movie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Singleton
 */
@Stateless
public class MovieDao {

    @PersistenceContext
    private EntityManager em;

    public Optional<Movie> findOne(Integer id) {
        try {
            return Optional.of(
                    em.createQuery("select m from Movie m where m.id = :id", Movie.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    public List<Movie> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);
        CriteriaQuery<Movie> all = cq.select(root);
        TypedQuery<Movie> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }

    public Movie update(Movie entity) {
        return em.merge(entity);
    }

    public boolean delete(Integer id) {
        try {
            em.remove(findOne(id).orElse(null));

            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();

            return false;
        }
    }

    public Movie save(Movie entity) {
        em.persist(entity);

        return entity;
    }
}
