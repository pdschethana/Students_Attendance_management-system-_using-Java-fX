package com.ijse.javaprojectfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentManagementController {
    public void stuaddOnAction(ActionEvent actionEvent) {
    }

    public void stuUpdateOnAction(ActionEvent actionEvent) {
    }

    public void stuDeleteOnAction(ActionEvent actionEvent) {
    }

    public void stuSearchOnAction(ActionEvent actionEvent) {
    }

    public void backstudentfromdashboard(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Admin dashboard"); // Also fixed the typo "coursr" -> "Course"
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
