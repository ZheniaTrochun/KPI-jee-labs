package com.yevhenii.jsf;


//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import java.util.Arrays;
import java.util.List;

//@ManagedBean(name = "helloWorld", eager = true)
//@ViewScoped
public class MovieBean {

    private final MovieDao dao = MovieDao.getInstance();

    public MovieBean() {
    }


    public List<Movie> getAll() {
        return dao.findAll();
    }

    public void delete(Integer id) {
        dao.delete(id);
    }

    public void update(Integer id, String name, String genre, Integer year, String author, Double rating) {
        Movie movie = new Movie(id, name, author, year, genre, rating);
        dao.update(movie);
    }

    public void create(String name, String genre, Integer year, String author, Double rating) {
        Movie movie = new Movie(name, author, year, genre, rating);
        dao.save(movie);
    }
}
