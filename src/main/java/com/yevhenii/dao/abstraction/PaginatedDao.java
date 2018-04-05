package com.yevhenii.dao.abstraction;


import com.yevhenii.dao.connection.ConnectionManager;

import java.sql.SQLException;
import java.util.List;

public abstract class PaginatedDao <E, K> extends AbstractDao<E, K> {

    private int pageSize = 10;

    public PaginatedDao(Class<E> type, List<String> fields, String tableName, ConnectionManager manager, int pageSize) {
        super(type, fields, tableName, manager);
        this.pageSize = pageSize;
    }

    public PaginatedDao(Class<E> type, List<String> fields, String tableName, ConnectionManager manager) {
        super(type, fields, tableName, manager);
    }

    public List<E> findAllByPage(int page) throws SQLException {

        return connectionManager.withConnection(connection ->
                extractAllEntities(
                    connection
                            .prepareStatement(String.format("%s LIMIT %d, %d", ALL_SEARCH_QUERY,
                                    (page - 1) * pageSize, pageSize))
                            .executeQuery()
                )
        );
    }

    public List<E> findByQueryAndPage(String query, int page) throws SQLException {

        return connectionManager.withConnection(connection ->
                extractAllEntities(
                        connection
                                .prepareStatement(String.format("%s LIMIT %d, %d", query, (page - 1) * pageSize, pageSize))
                                .executeQuery()
                )
        );
    }
}
