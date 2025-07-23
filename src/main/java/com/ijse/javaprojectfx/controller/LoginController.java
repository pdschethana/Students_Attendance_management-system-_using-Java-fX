package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private RadioButton adminid;

    @FXML
    private RadioButton lecturerid;

    @FXML
    private TextField logid;

    @FXML
    private TextField logpsw;

    // Store current logged-in user info
    public static String currentUserId;
    public static String currentUserName;
    public static String currentUserRole;

    public void loginOnAction(ActionEvent actionEvent) {
        // Get input values
        String userId = logid.getText().trim();
        String password = logpsw.getText().trim();

        // Validation for empty fields
        if (userId.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields!");
            return;
        }

        // Check if a role is selected
        if (!adminid.isSelected() && !lecturerid.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "Role Selection Error", "Please select a role (Admin or Lecturer)!");
            return;
        }

        try {
            boolean loginSuccessful = false;
            String userName = "";

            if (adminid.isSelected()) {
                // Validate admin login
                loginSuccessful = validateAdminLogin(userId, password);
                if (loginSuccessful) {
                    userName = getAdminName(userId);
                    currentUserRole = "Admin";
                }
            } else if (lecturerid.isSelected()) {
                // Validate lecturer login
                loginSuccessful = validateLecturerLogin(userId, password);
                if (loginSuccessful) {
                    userName = getLecturerName(userId);
                    currentUserRole = "Lecturer";
                }
            }

            if (loginSuccessful) {
                // Store current user info
                currentUserId = userId;
                currentUserName = userName;

                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Login Successful",
                        "Welcome " + userName + "!");

                // Navigate to appropriate dashboard
                Parent root;
                if (adminid.isSelected()) {
                    root = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/view/lecturerdashboard.fxml"));
                }

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard - " + currentUserName);
                stage.show();

            } else {
                // Show error message for invalid credentials
                showAlert(Alert.AlertType.ERROR, "Login Failed",
                        "Invalid credentials! Please check your ID and password.");

                // Clear password field for security
                logpsw.clear();
            }

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error",
                    "Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Validate admin login credentials
     */
    private boolean validateAdminLogin(String adminId, String password) throws SQLException {
        String query = "SELECT admin_id FROM admin WHERE admin_id = ? AND password = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, adminId);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a record is found
            }
        }
    }

    /**
     * Validate lecturer login credentials
     */
    private boolean validateLecturerLogin(String lecturerId, String password) throws SQLException {
        String query = "SELECT lecturer_id FROM lecturers WHERE lecturer_id = ? AND password = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lecturerId);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a record is found
            }
        }
    }

    /**
     * Get admin name by admin ID
     */
    private String getAdminName(String adminId) throws SQLException {
        String query = "SELECT admin_name FROM admin WHERE admin_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, adminId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("admin_name");
                }
            }
        }
        return "Admin"; // Default fallback
    }

    /**
     * Get lecturer name by lecturer ID
     */
    private String getLecturerName(String lecturerId) throws SQLException {
        String query = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lecturerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("lecturer_name");
                }
            }
        }
        return "Lecturer"; // Default fallback
    }

    /**
     * Show alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clear all fields
     */
    @FXML
    private void clearFields() {
        logid.clear();
        logpsw.clear();
        adminid.setSelected(false);
        lecturerid.setSelected(false);
    }

    /**
     * Initialize method - called after loading FXML
     */
    @FXML
    private void initialize() {
        // Test database connection on startup
        if (!DBConnection.getInstance().testConnection()) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Failed to connect to database! Please check your database configuration.");
        }

        // Make radio buttons mutually exclusive
        adminid.setOnAction(e -> {
            if (adminid.isSelected()) {
                lecturerid.setSelected(false);
            }
        });

        lecturerid.setOnAction(e -> {
            if (lecturerid.isSelected()) {
                adminid.setSelected(false);
            }
        });
    }
}