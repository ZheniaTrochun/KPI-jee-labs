package com.yevhenii;

import com.yevhenii.dao.DaoFactory;
import com.yevhenii.dao.abstraction.Dao;
import com.yevhenii.dao.abstraction.PaginatedDao;
import com.yevhenii.model.Movie;

import java.sql.SQLException;
import java.util.stream.IntStream;

public class Application {
    public static void main(String[] args) {
        PaginatedDao<Movie, Integer> dao = DaoFactory.getPaginatedDaoForEntity(Movie.class);

        try {
            dao.createSchema();
            for (int i = 0; i < 15; i++) {

                dao.save(new Movie("" + i, "2", 123, "4", 0.0));
            }
            System.out.println(dao.findAllByPage(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
