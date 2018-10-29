package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String params, ExecutionInterface<T> executionInterface) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(params)) {
            return executionInterface.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new ExistStorageException("unknown");
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface ExecutionInterface<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}