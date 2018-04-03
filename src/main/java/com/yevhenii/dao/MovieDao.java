package com.yevhenii.dao;

import com.yevhenii.dao.abstraction.PaginatedDao;
import com.yevhenii.model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton
 */
public class MovieDao extends PaginatedDao<Movie, Integer> {

    private static final int PAGE_SIZE = 30;

    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO Movies (name, author, year, genre, imdbScore) VALUES (%s, %s, %d, %s, %f)";
    private static final String UPDATE_QUERY_TEMPLATE = "UPDATE Movies SET name = %s, author = %s, year = %d, genre = %s, imdbScore = %f WHERE id = %d";
    private static final String CREATE_SCHEMA_QUERY = "CREATE TABLE Movies (\n" +
            "    id int NOT NULL AUTO_INCREMENT,\n" +
            "    name varchar(50),\n" +
            "    author varchar(50),\n" +
            "    year int,\n" +
            "    genre varchar(255),\n" +
            "    imdbScore double,\n" +
            "    PRIMARY KEY (id),\n" +
            ");";
    private static final String DROP_SCHEMA_QUERY = "DROP TABLE Movies";

    MovieDao(int pageSize, String driver, String url) {

        super(Movie.class,
                Arrays.asList("id", "name", "author", "year", "genre", "imdbScore"),
                "Movies",
                CREATE_SCHEMA_QUERY,
                DROP_SCHEMA_QUERY,
                driver,
                url,
                pageSize);
    }

    public MovieDao(String driver, String url) {

        super(Movie.class,
                Arrays.asList("id", "name", "author", "year", "genre", "imdbScore"),
                "Movies",
                CREATE_SCHEMA_QUERY,
                DROP_SCHEMA_QUERY,
                driver,
                url);
    }

    @Override
    protected String createInsertQuery(Movie entity) {

        return String.format(INSERT_QUERY_TEMPLATE,
                entity.getName(),
                entity.getAuthor(),
                entity.getYear(),
                entity.getGenre(),
                entity.getImdbScore());
    }

    @Override
    protected String createUpdateQuery(Movie entity) {

        return String.format(UPDATE_QUERY_TEMPLATE,
                entity.getName(),
                entity.getAuthor(),
                entity.getYear(),
                entity.getGenre(),
                entity.getImdbScore(),
                entity.getId());
    }

    @Override
    public List<Movie> findByQuery(String query) {
        return null;
    }
}
