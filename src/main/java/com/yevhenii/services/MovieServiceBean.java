package com.yevhenii.services;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class MovieServiceBean implements MovieService {

    private static final MovieDao dao = MovieDao.getInstance();

    @Override
    public Movie save(Movie movie) {
        Integer id = dao.save(movie);
        movie.setId(id);

        return movie;
    }

    @Override
    public boolean update(Movie movie) {
        return dao.update(movie);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public List<Movie> findAll() {
        return dao.findAll();
    }

    @Override
    public Movie find(Integer id) {
        return dao.findOne(id).orElse(null);
    }
}
