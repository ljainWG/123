package com.wg.dabms.util.dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    public static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        final String URL = "jdbc:mysql://localhost:3306/project";
        final String USER = "root";
        final String PASSWORD = "admin";

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                LOGGER.info("Connecting to the database...");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                LOGGER.info("Database connection established.");
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
                throw new SQLException("JDBC Driver not found", e);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error connecting to the database", e);
                throw new SQLException("Error connecting to the database", e);
            }
        } else {
            LOGGER.info("Using existing database connection.");
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed.");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing the database connection", e);
            }
        } else {
            LOGGER.info("No database connection to close.");
        }
    }
}
