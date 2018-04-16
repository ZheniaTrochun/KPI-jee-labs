package com.yevhenii.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet(name = "movieServlet", urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {

    private final MovieDao dao = MovieDao.getInstance();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void init() throws ServletException {

        dao.dropSchema();
        dao.createSchema();
        Arrays.asList(
                new Movie("1", "1", 1, "1", 1.0),
                new Movie("2", "2", 2, "2", 2.0),
                new Movie("3", "3", 3, "3", 3.0),
                new Movie("4", "4", 4, "4", 4.0),
                new Movie("5", "5", 5, "5", 5.0)
        ).forEach(dao::save);

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("allMovies", dao.findAll());

        RequestDispatcher view = req.getRequestDispatcher("all.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Movie movie = constructMovie(req);

        System.out.println("Movie to create: " + movie);

        dao.save(movie);
    }

    private Movie constructMovie(HttpServletRequest req) {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        Integer year = new Integer(req.getParameter("year"));
        Double imdbScore = new Double(req.getParameter("imdbScore"));

        return new Movie(name, author, year, genre, imdbScore);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Movie movie = MAPPER.readValue(req.getReader()
                    .lines()
                    .collect(Collectors.joining()),
                Movie.class);

        System.out.println("movie to update: " + movie);

        dao.update(movie);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = new Integer(req.getParameter("id"));

        System.out.println("Movie to delete id: " + id);

        dao.delete(id);
    }
}
