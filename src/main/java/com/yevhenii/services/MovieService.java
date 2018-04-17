package com.yevhenii.services;

import com.yevhenii.model.Movie;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MovieService {

    Movie save(Movie movie);

    boolean update(Movie movie);

    boolean delete(Integer id);

    List<Movie> findAll();

    Movie find(Integer id);
}
