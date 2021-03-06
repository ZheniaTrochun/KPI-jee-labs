package com.yevhenii.dao;

import com.yevhenii.dao.connection.ConnectionManager;
import com.yevhenii.dao.connection.ConnectionManagerImpl;
import com.yevhenii.model.Movie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieDaoTest {

    private ConnectionManager connectionManager =
            new ConnectionManagerImpl("org.h2.Driver", "jdbc:h2:~/test", "sa", "");

    private MovieDao dao = new MovieDao(connectionManager);

    @Before
    public void setUp() {
        try {
            dao.createSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            dao.dropSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOne() throws SQLException {
        Movie entity = new Movie("1", "2", 123, "4", 0.0);
        int id = dao.save(entity);

        Optional<Movie> actual = dao.findOne(id);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(entity, actual.get());
    }

    @Test
    public void findAll() throws SQLException {
        List<Movie> expected = Arrays.asList(
                new Movie("1", "1", 1, "1", 1.0),
                new Movie("2", "2", 2, "2", 2.0),
                new Movie("3", "3", 3, "3", 3.0),
                new Movie("4", "4", 4, "4", 4.0),
                new Movie("5", "5", 5, "5", 5.0)
        );

        for (Movie element : expected) {
            dao.save(element);
        }

        List<Movie> actual = dao.findAll();

        Assert.assertEquals(actual.size(), expected.size());

        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i), expected.get(i));
        }
    }

    @Test
    public void delete() throws SQLException {
        int id = dao.save(new Movie("1", "2", 123, "4", 0.0));

        int lenBefore = dao.findAll().size();
        dao.delete(id);
        int lenAfter = dao.findAll().size();

        Assert.assertEquals(lenBefore - 1, lenAfter);
        Assert.assertFalse(dao.findOne(id).isPresent());
    }

    @Test
    public void save() throws SQLException {
        int lenBefore = dao.findAll().size();
        int id = dao.save(new Movie("1", "2", 123, "4", 0.0));
        int lenAfter = dao.findAll().size();

        Assert.assertEquals(lenBefore + 1, lenAfter);
        Assert.assertNotEquals(0, id);
        Assert.assertTrue(dao.findOne(id).isPresent());
    }

    @Test
    public void update() throws SQLException {
        Movie entity = new Movie("1", "2", 123, "4", 0.0);
        int id = dao.save(entity);

        entity.setId(id);
        entity.setImdbScore(9.9);

        Movie actual = dao.update(entity);

        Assert.assertEquals(entity, actual);
        Assert.assertEquals(dao.findOne(id).get().getImdbScore(), Double.valueOf(9.9));
    }
}