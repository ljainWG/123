package com.wg.dabms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.util.dbConfig.DBConnection;

public abstract class GenericDAO<T> {

    protected Connection connection;
    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());

    public GenericDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to obtain database connection", e);
        }
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    public T executeGetQuery(String query, Object... params) {
        T entity = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity = mapResultSetToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error occurred while executing query: " + query, e);
        }
        return entity;
    }

    public List<T> executeGetAllQuery(String query, Object... params) {
        List<T> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error occurred while executing query: " + query, e);
        }
        return entities;
    }

    public boolean executeUpdate(String query, Object... params) {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, params);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error occurred while executing update: " + query, e);
        }
        return result;
    }

    public boolean executeDelete(String query, Object... params) {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, params);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error occurred while executing delete: " + query, e);
        }
        return result;
    }

    private void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}
