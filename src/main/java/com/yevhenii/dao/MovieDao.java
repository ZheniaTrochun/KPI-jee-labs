package com.yevhenii.dao;

import com.yevhenii.dao.abstraction.PaginatedDao;
import com.yevhenii.model.Movie;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton
 */
public class MovieDao extends PaginatedDao<Movie, Integer> {

    private static MovieDao instance;

    private static final Object lock = new Object();

    MovieDao(int pageSize) {
        super(Movie.class, "Movie", pageSize);
    }

    MovieDao() {
        super(Movie.class, "Movie");
    }

    MovieDao(EntityManagerFactory entityManagerFactory) {
        super(Movie.class, entityManagerFactory);
    }

    public static MovieDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null)
                    instance = new MovieDao();
            }
        }

        return instance;
    }

    @Override
    protected Integer extractId(Movie entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateSchemaQuery() {
        return "CREATE TABLE Movie (\n" +
                "    id int NOT NULL AUTO_INCREMENT,\n" +
                "    name varchar(50),\n" +
                "    author varchar(50),\n" +
                "    year int,\n" +
                "    genre varchar(255),\n" +
                "    imdbScore double,\n" +
                "    PRIMARY KEY (id),\n" +
                ");";
    }

    @Override
    protected String getDropSchemaQuery() {
        return "DROP TABLE Movie;";
    }
}
