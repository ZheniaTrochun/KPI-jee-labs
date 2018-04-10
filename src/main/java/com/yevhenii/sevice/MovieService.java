package com.yevhenii.sevice;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import java.util.List;

public class MovieService {
    private static final MovieDao MOVIE_DAO = MovieDao.getInstance();

    public void deleteMovie(Integer id) {
        MOVIE_DAO.delete(id);
    }

    public void updateMovie(Movie movie) {
        MOVIE_DAO.update(movie);
    }

    public void createMovie(Movie movie) {
        MOVIE_DAO.save(movie);
    }

    public List<Movie> findAll() {
        return MOVIE_DAO.findAll();
    }
}
