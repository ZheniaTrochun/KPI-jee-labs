package com.yevhenii.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {

    <T> QueryResult<T> withConnection(Connected<T> execution);

    Connection getConnection() throws SQLException;
}
