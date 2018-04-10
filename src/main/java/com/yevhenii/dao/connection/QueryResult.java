package com.yevhenii.dao.connection;

import java.util.Objects;

public class QueryResult<T> {
    private final T data;

    public QueryResult(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }

    public T getOrElse(T defaultResult) {
        return Objects.isNull(data) ? defaultResult : data;
    }

    public static <T> QueryResult<T> empty() {
        return new QueryResult<>(null);
    }
}
