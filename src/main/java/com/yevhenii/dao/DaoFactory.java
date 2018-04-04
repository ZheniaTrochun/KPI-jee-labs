package com.yevhenii.dao;

import com.yevhenii.dao.abstraction.Dao;
import com.yevhenii.dao.abstraction.PaginatedDao;
import com.yevhenii.model.Movie;

public class DaoFactory {
    private static final MovieDao MOVIE_DAO = new MovieDao("org.h2.Driver", "jdbc:h2:~/test");

    @SuppressWarnings("unchecked")
    public static Dao getDaoForEntity(Class entityClass) {
        if (entityClass == Movie.class) {

            return MOVIE_DAO;
        }

        throw new RuntimeException("No dao class exists for class " + entityClass);
    }

    @SuppressWarnings("unchecked")
    public static PaginatedDao getPaginatedDaoForEntity(Class entityClass) {
        if (entityClass == Movie.class) {

            return MOVIE_DAO;
        }

        throw new RuntimeException("No dao class exists for class " + entityClass);
    }

    public static MovieDao getMovieDao() {

        return MOVIE_DAO;
    }
}
