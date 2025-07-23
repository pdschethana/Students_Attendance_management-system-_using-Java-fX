package com.ijse.javaprojectfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private RadioButton adminid;

    @FXML
    private RadioButton lecturerid;

    @FXML
    private TextField logid;

    @FXML
    private TextField logpsw;

    public void loginOnAction(ActionEvent actionEvent) {
        try {
            // For simplicity, assuming login is always successful.
            // You can add validation logic here if needed.

            Parent root;
            if (adminid.isSelected()) {
                root = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
            } else if (lecturerid.isSelected()) {
                root = FXMLLoader.load(getClass().getResource("/view/LecturerDashboard.fxml"));
            } else {
                System.out.println("No role selected.");
                return;
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
