package com.yevhenii.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConnectionManager<E, K> {

    <T> T withConnection(Connected<T> execution) throws SQLException;

    @FunctionalInterface
    interface Connected<E> {

        E connected(Connection connection) throws SQLException;
    }
}
