package com.yevhenii.servlets;

import com.yevhenii.dao.MovieDao;
import com.yevhenii.model.Movie;
import com.yevhenii.sevice.MovieService;

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
    private final MovieService service = new MovieService();

    @Override
    public void init() throws ServletException {

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

        req.setAttribute("allMovies", service.findAll());

        RequestDispatcher view = req.getRequestDispatcher("all.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");

        if ("put".equalsIgnoreCase(method)) {
//            PUT request
            Movie movie = constructMovie(req);
            movie.setId(new Integer(req.getParameter("id")));
            service.updateMovie(movie);
        } else if ("delete".equalsIgnoreCase(method)) {
//            DELETE request
            Integer id = new Integer(req.getParameter("id"));
            service.deleteMovie(id);
        } else {
//            actual POST
            service.createMovie(constructMovie(req));
        }

//        reload page after any update
        doGet(req, resp);
    }

    private Movie constructMovie(HttpServletRequest req) {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        Integer year = new Integer(req.getParameter("year"));
        Double imdbScore = new Double(req.getParameter("imdbScore"));

        return new Movie(name, author, year, genre, imdbScore);
    }

//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Integer id = new Integer(req.getParameter("id"));
//        String name = req.getParameter("name");
//        String author = req.getParameter("author");
//        String genre = req.getParameter("genre");
//        Integer year = new Integer(req.getParameter("year"));
//        Double imdbScore = new Double(req.getParameter("imdbScore"));
//
//        Movie movie = new Movie(id, name, author, year, genre, imdbScore);
//
//        dao.update(movie);
//
//        doGet(req, resp);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        Integer id = new Integer(req.getParameter("id"));
//
//        dao.delete(id);
//
//        doGet(req, resp);
//    }
}
