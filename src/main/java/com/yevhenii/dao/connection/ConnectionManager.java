package com.yevhenii.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {

    <T> T withConnection(Connected<T> execution) throws SQLException;

    Connection getConnection() throws SQLException;
}
