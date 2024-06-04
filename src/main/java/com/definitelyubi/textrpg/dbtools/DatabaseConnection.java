package com.definitelyubi.textrpg.dbtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private final Connection connection;

    public DatabaseConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        logger.info("Database connection established");
    }

    public Connection getConnection() {
        return connection;
    }

    public synchronized void close() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing database connection", e);
        }
    }

}
