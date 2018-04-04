package com.yevhenii.dao.abstraction;

import com.yevhenii.dao.connection.ConnectionManager;
import com.yevhenii.dao.connection.ConnectionManagerImpl;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public abstract class AbstractDao<E, T> implements Dao<E, T> {

    protected Class<E> type;
    protected List<String> fields;
    protected String tableName;

    protected ConnectionManager<E, T> connectionManager;

    protected final String ALL_SEARCH_QUERY;
    protected final String ID_SEARCH_QUERY;
    protected final String DELETE_QUERY;

    protected final String DROP_SCHEMA_QUERY;

    public AbstractDao(Class<E> type, List<String> fields, String tableName, String driver, String url) {

        this.type = type;
        this.fields = fields;
        this.tableName = tableName;

        ALL_SEARCH_QUERY = String.format("SELECT * FROM %s", tableName);
        ID_SEARCH_QUERY = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        DELETE_QUERY = String.format("DELETE %s WHERE id = ?", tableName);

        this.connectionManager = new ConnectionManagerImpl<>(driver, url);

        this.DROP_SCHEMA_QUERY = String.format("DROP TABLE %s", tableName);
    }

    @Override
    public Optional<E> findOne(T id) throws SQLException {

        return Optional.ofNullable(
                connectionManager.withConnection(connection -> {
                    PreparedStatement statement = connection.prepareStatement(ID_SEARCH_QUERY);
                    statement.setObject(1, id);

                    ResultSet rs = statement.executeQuery();

                    if (checkCompability(rs.getMetaData()))
                        throw new SQLException("Unmatched fields!");

                    return extractEntity(rs);
                })
        );
    }

    @Override
    public List<E> findAll() throws SQLException {

        return connectionManager.withConnection(connection -> {
            ResultSet rs = connection.prepareStatement(ALL_SEARCH_QUERY).executeQuery();

            if (checkCompability(rs.getMetaData()))
                throw new SQLException("Unmatched fields!");

            return extractAllEntities(rs);
        });
    }

    @Override
    public boolean delete(T id) throws SQLException {

        return connectionManager.withConnection(connection -> {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setObject(1, id);

            return statement.execute();
        });
    }

    @Override
    public T save(E entity) throws SQLException {

        return connectionManager.withConnection(connection -> {
            PreparedStatement statement = connection.prepareStatement(createInsertQuery(entity), Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("Creating failed, no rows affected.");

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    return (T) generatedKeys.getObject(1);
                }
                else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            }
        });
    }

    @Override
    public E update(E entity) throws SQLException {

        return connectionManager.withConnection(connection -> {
            PreparedStatement statement = connection.prepareStatement(createUpdateQuery(entity));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("Creating failed, no rows affected.");

            return entity;
        });
    }

    @Override
    public void createSchema() throws SQLException {
        connectionManager
                .withConnection(conn -> conn.prepareStatement(getCreateSchemaQuery()).execute());
    }

    @Override
    public void dropSchema() throws SQLException {
        connectionManager
                .withConnection(conn -> conn.prepareStatement(DROP_SCHEMA_QUERY).execute());
    }

    protected abstract String createInsertQuery(E entity);

    protected abstract String createUpdateQuery(E entity);

    protected abstract String getCreateSchemaQuery();


    protected List<String> rsFields(ResultSetMetaData metaData) throws SQLException {
        List<String> fieldNames = new LinkedList<>();

        for (int i = 1; i <= metaData.getColumnCount(); i++)
            fieldNames.add(metaData.getColumnName(i));

        return fieldNames;
    }

    protected boolean checkCompability(ResultSetMetaData meta) throws SQLException {

        return (meta.getColumnCount() == fields.size()) &&
                fields.containsAll(rsFields(meta));
    }

    protected List<E> extractAllEntities(ResultSet rs) throws SQLException {
        List<String> rsFields = rsFields(rs.getMetaData());

        List<E> entities = new LinkedList<>();
        try {
            while (rs.next()) {
                entities.add(extractEntity(rs, fields));
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();

            throw new SQLException("SELECT failed due to invalid data returned!");
        }

        return entities;
    }

    private E extractEntity(ResultSet rs) throws SQLException {

        E entity;

        if (!rs.next())
            return null;

        try {
            entity = type.newInstance();
            int fieldCount = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= fieldCount; i++) {
                Field field = type.getDeclaredField(fields.get(i - 1));
                field.setAccessible(true);
                field.set(entity, rs.getObject(i));
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();

            throw new SQLException("SELECT failed due to invalid data returned!");
        }

        return entity;
    }

    private E extractEntity(ResultSet rs, List<String> fields) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {

        E entity = type.newInstance();
        int fieldCount = rs.getMetaData().getColumnCount();

        for (int i = 1; i <= fieldCount; i++) {
            Field field = type.getDeclaredField(fields.get(i-1));
            field.setAccessible(true);
            field.set(entity, rs.getObject(i));
        }

        return entity;
    }
}
