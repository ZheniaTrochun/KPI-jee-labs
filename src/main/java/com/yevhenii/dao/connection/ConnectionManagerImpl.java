package com.yevhenii.dao.connection;

import com.yevhenii.dao.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManagerImpl<E, K> implements ConnectionManager<E, K> {

    private String url;

    public ConnectionManagerImpl(String driver, String url) {
        try {
            Class.forName(driver);
            this.url = url;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T withConnection(Connected<T> execution) throws SQLException {
        T result;

        try (Connection connection = DriverManager.getConnection(url, "sa", "")) {
            result = execution.connected(connection);
        }

        return result;
    }

}
