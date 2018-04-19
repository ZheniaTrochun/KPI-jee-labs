package com.yevhenii.services;

import com.yevhenii.model.Movie;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MovieService {
    Movie save(Movie movie);

    List<Movie> getAll();

    boolean delete(Integer id);

    Movie update(Movie movie);
}
