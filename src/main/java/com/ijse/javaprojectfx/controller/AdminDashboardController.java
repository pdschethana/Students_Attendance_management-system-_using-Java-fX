package com.ijse.javaprojectfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    public void studentmanageOnaction(ActionEvent actionEvent) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/studentmanagement.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Student Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void coursemanageOnaction(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/coursemanagement.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Course Management"); // Also fixed the typo "coursr" -> "Course"
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lecturemanagementOnAction(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/lecturermanagement.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("lecturer Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Implementation needed
    }

    public void classshedulOnAction(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/classsheduling.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("shedule classes");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Implementation needed
    }

    public void attendancemanagementOnAction(ActionEvent actionEvent) {
        // Implementation needed
    }

    public void reportonAction(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("reports");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Implementation needed
    }

    public void logoutAction(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("login"); // Also fixed the typo "coursr" -> "Course"
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Implementation needed
    }
}