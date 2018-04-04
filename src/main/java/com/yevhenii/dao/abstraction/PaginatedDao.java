package com.yevhenii.dao.abstraction;


import java.sql.SQLException;
import java.util.List;

public abstract class PaginatedDao <E, K> extends AbstractDao<E, K> {

    private int pageSize;

    public PaginatedDao(Class<E> type, List<String> fields, String tableName, String driver, String url, int pageSize) {
        super(type, fields, tableName, driver, url);
        this.pageSize = pageSize;
    }

    public PaginatedDao(Class<E> type, List<String> fields, String tableName, String driver, String url) {

        super(type, fields, tableName, driver, url);
    }

    List<E> findAllByPage(int page) throws SQLException {
        return connectionManager.withConnection(connection ->
                extractAllEntities(
                    connection
                    .prepareStatement(String.format("%s LIMIT %d, %d", ALL_SEARCH_QUERY, pageSize, (page - 1) * pageSize))
                    .executeQuery()
                )
        );
    }
}
