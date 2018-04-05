package com.yevhenii.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManagerImpl implements ConnectionManager {

    private String url;

    private String user;
    private String password;

    public ConnectionManagerImpl(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);

            this.url = url;
            this.user = user;
            this.password = password;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            result = execution.connected(connection);
        }

        return result;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
