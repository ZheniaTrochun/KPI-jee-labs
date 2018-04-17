package com.yevhenii.jsf;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.yevhenii.model.Movie;
import com.yevhenii.services.MovieService;

import java.util.List;

@ManagedBean(name = "movieBean", eager = true)
@SessionScoped
public class MovieBean {

//    private final Movieservice service = Movieservice.getInstance();

    @EJB
    private MovieService service;

    public MovieBean() {
//        try {
//            InitialContext ctx = new InitialContext();
//            service = (MovieService) ctx.lookup("MovieServiceBean");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }

    }

    public List<Movie> getAll() {
        return service.findAll();
    }

    public void delete(Integer id) {
        service.delete(id);
    }

    public void update(Integer id, String name, String genre, Integer year, String author, Double rating) {
        Movie movie = new Movie(id, name, author, year, genre, rating);
        service.update(movie);
    }

    public void create(String name, String genre, Integer year, String author, Double rating) {
        Movie movie = new Movie(name, author, year, genre, rating);
        service.save(movie);
    }
}
