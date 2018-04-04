package com.yevhenii.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Connected<E> {

    E connected(Connection connection) throws SQLException;
}
