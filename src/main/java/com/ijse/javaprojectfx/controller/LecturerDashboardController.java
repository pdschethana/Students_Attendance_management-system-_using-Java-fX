package com.ijse.javaprojectfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LecturerDashboardController {

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnViewSchedule;

    @FXML
    private Button btnMarkAttendance;

    @FXML
    private Button btnViewReports;

    @FXML
    private Button btnManageProfile;

    @FXML
    private Button btnLogout;

    @FXML
    public void initialize() {
        lblWelcome.setText("Welcome, Dr. John Smith"); // Dynamically set later
    }
}
