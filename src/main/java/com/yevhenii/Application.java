package com.yevhenii;

import com.yevhenii.dao.MovieDao;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        MovieDao dao = new MovieDao("org.h2.Driver", "jdbc:h2:~/test");

        try {
//            dao.createSchema();
//            dao.save(new Movie("1", "2", 123, "4", 0.0));
            System.out.println(dao.findOne(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
