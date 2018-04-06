package com.yevhenii.servlets;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "movieServlet", urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {

    private final MovieDao dao = MovieDao.getInstance();

    @Override
    public void init() throws ServletException {

        try {
            dao.createSchema();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            req.setAttribute("allMovies", dao.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher view = req.getRequestDispatcher("all.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        Integer year = new Integer(req.getParameter("year"));
        Double imdbScore = new Double(req.getParameter("imdbScore"));

        Movie movie = new Movie(name, author, year, genre, imdbScore);

        try {
            dao.save(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = new Integer(req.getParameter("id"));
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        Integer year = new Integer(req.getParameter("year"));
        Double imdbScore = new Double(req.getParameter("imdbScore"));

        Movie movie = new Movie(id, name, author, year, genre, imdbScore);

        try {
            dao.update(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = new Integer(req.getParameter("id"));

        try {
            dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }
}
