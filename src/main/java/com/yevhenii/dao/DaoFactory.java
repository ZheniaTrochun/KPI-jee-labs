package com.yevhenii.dao;

import com.yevhenii.dao.abstraction.Dao;
import com.yevhenii.dao.abstraction.PaginatedDao;
import com.yevhenii.dao.connection.ConnectionManager;
import com.yevhenii.dao.connection.ConnectionManagerImpl;
import com.yevhenii.model.Movie;

public class DaoFactory {
    private static ConnectionManager connectionManager =
            new ConnectionManagerImpl("org.h2.Driver", "jdbc:h2:~/test", "sa", "");

    private static final MovieDao MOVIE_DAO = new MovieDao(connectionManager);

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

    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static void setConnectionManager(ConnectionManager connectionManager) {
        DaoFactory.connectionManager = connectionManager;
    }
}
