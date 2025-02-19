package com.mobiversa.payment.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
public class DBConnection {

	private static Logger logger = Logger.getLogger(DBConnection.class);

	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Not Found MySQL JDBC Driver");
			e.printStackTrace();

		}

		Connection connection = null;

		try {
			
			connection = DriverManager.getConnection(PropertyLoader.getFileData().getProperty("JdbcUrl"),
					PropertyLoader.getFileData().getProperty("Username"), PropertyLoader.getFileData().getProperty("Password"));

		} catch (SQLException e) {
			logger.error("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			// logger.info("Connection Established!");
		} else {
			logger.info("Failed to make connection!");
		}
		return connection;

	}

	
	public static Connection getConnectionForEzysettle() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Not Found MySQL JDBC Driver");
			e.printStackTrace();
		}

		Connection connection = null;

		try {
			
			connection = DriverManager.getConnection(PropertyLoader.getFileData().getProperty("JdbcUrl"),
					PropertyLoader.getFileData().getProperty("Username"), PropertyLoader.getFileData().getProperty("Password"));

		} catch (SQLException e) {
			logger.error("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			// logger.info("Connection Established!");
		} else {
			logger.info("Failed to make connection!");
		}
		return connection;

	}
	
}
