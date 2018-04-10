package com.yevhenii.dao.abstraction;

import java.util.List;
import java.util.Optional;

public interface Dao<E, T> {
    Optional<E> findOne(T id);

    List<E> findAll();

    T save(E entity);

    boolean delete(T id);

    boolean update(E entity);

    void createSchema();

    void dropSchema();
}
