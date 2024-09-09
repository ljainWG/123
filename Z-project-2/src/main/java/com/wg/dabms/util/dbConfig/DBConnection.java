package com.wg.dabms.util.dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

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
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				throw new SQLException("JDBC Driver not found", e);
			} catch (SQLException e) {
				throw new SQLException("Error connecting to the database", e);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}