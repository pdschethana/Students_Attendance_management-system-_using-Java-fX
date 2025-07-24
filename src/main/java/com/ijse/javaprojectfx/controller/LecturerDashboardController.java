package com.ijse.javaprojectfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LecturerDashboardController {

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnMarkAttendance;

    @FXML
    private Button btnViewReports;

    @FXML
    private Button btnLogout;

    @FXML
    public void initialize() {
        lblWelcome.setText("Welcome, Dr. John Smith"); // Dynamically set later
    }

    public void Gotoattendancemanagementpage(ActionEvent actionEvent) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/attendancemanagement.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("attendance management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotologin(ActionEvent actionEvent) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotologinpage(MouseEvent mouseEvent) {

    }
}
