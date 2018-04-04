package com.yevhenii;

import com.yevhenii.dao.DaoFactory;
import com.yevhenii.dao.abstraction.Dao;
import com.yevhenii.model.Movie;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        Dao<Movie, Integer> dao = DaoFactory.getMovieDao();

        try {
            dao.createSchema();
            dao.save(new Movie("1", "2", 123, "4", 0.0));
            System.out.println(dao.findOne(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
