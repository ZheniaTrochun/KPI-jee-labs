package com.yevhenii.services;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovieServiceBean implements MovieService {;

    @Inject
    private MovieDao dao;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Movie save(Movie movie) {
        Integer id = dao.save(movie).getId();
        movie.setId(id);

        return movie;
    }

    @Override
    public List<Movie> getAll() {
        return dao.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Movie update(Movie movie) {
        return dao.update(movie) != null ? movie : null;
    }
}
