package com.yevhenii.controller;

import com.yevhenii.model.Movie;
import com.yevhenii.services.MovieService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@Path("/movie")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MovieController {

    @Inject
    private MovieService service;

    @GET
    public List<Movie> getAll() {
        return service.getAll();
    }

    @POST
    public Movie create(Movie movie) {
        return service.save(movie);
    }

    @PUT
    public Movie update(Movie movie) {
        return service.update(movie);
    }

    @DELETE
    @Path(("/{id}"))
    public boolean delete(@PathParam("id") Integer id) {
        return service.delete(id);
    }
}
