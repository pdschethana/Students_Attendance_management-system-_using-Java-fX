package com.ijse.javaprojectfx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 * Handles database connections for the EduTrack application
 */
public class DBConnection
{

    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/edutrack";
    private static final String USERNAME = "root"; // Change as per your MySQL setup
    private static final String PASSWORD = "supuni1234##"; // Change as per your MySQL setup

    private static DBConnection instance;
    private Connection connection;

    // Private constructor for singleton pattern
    private DBConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection!");
            e.printStackTrace();
        }
    }

    /**
     * Get singleton instance of DatabaseConnection
     * @return DatabaseConnection instance
     */
    public static DBConnection getInstance() {
        if (instance == null || instance.isConnectionClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Get database connection
     * @return Connection object
     */
    public Connection getConnection() {
        try {
            // Check if connection is still valid, if not reconnect
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Error getting database connection!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Check if connection is closed
     * @return true if connection is closed, false otherwise
     */
    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully!");
            } catch (SQLException e) {
                System.err.println("Error closing database connection!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try {
            Connection testConn = getConnection();
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed!");
            e.printStackTrace();
            return false;
        }
    }
}