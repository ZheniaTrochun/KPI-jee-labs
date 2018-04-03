package com.yevhenii.dao.abstraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO <E, T> {
    Optional<E> findOne(T id) throws SQLException;

    List<E> findAll() throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException;

    T save(E entity) throws SQLException;

    boolean delete(T id) throws SQLException;

    E update(E entity) throws SQLException;

    void createSchema() throws SQLException;

    void dropSchema() throws SQLException;
}
