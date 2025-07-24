package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dto.ClassScheduleDTO;
import com.ijse.javaprojectfx.service.ClassScheduleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ClassScheduleController {
    @FXML private TextField txtScheduleId;
    @FXML private TextField txtCourseId;
    @FXML private TextField txtSubject;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtLecturerId;
    @FXML private TextField txtStartTime;
    @FXML private TextField txtEndTime;

    @FXML private TableView<ClassScheduleDTO> tblSchedule;
    @FXML private TableColumn<ClassScheduleDTO, String> colId;
    @FXML private TableColumn<ClassScheduleDTO, String> colCourse;
    @FXML private TableColumn<ClassScheduleDTO, String> colSubject;
    @FXML private TableColumn<ClassScheduleDTO, String> colDate;
    @FXML private TableColumn<ClassScheduleDTO, String> colLecturer;
    @FXML private TableColumn<ClassScheduleDTO, String> colStartTime;
    @FXML private TableColumn<ClassScheduleDTO, String> colEndTime;

    private final ClassScheduleService service = new ClassScheduleService();

    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getScheduleId()));
        colCourse.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCourseId()));
        colSubject.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSubject()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));
        colLecturer.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLecturerId()));
        colStartTime.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStartTime()));
        colEndTime.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEndTime()));
        loadTable();
    }

    private void loadTable() {
        try {
            ObservableList<ClassScheduleDTO> list = FXCollections.observableArrayList(service.getAllSchedules());
            tblSchedule.setItems(list);
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        ClassScheduleDTO dto = new ClassScheduleDTO(
                txtScheduleId.getText(),
                txtCourseId.getText(),
                txtSubject.getText(),
                datePicker.getValue().toString(),
                txtLecturerId.getText(),
                txtStartTime.getText(),
                txtEndTime.getText()
        );
        try {
            if (service.addSchedule(dto)) {
                showAlert("Success", "Schedule added successfully");
                loadTable();
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        ClassScheduleDTO dto = new ClassScheduleDTO(
                txtScheduleId.getText(),
                txtCourseId.getText(),
                txtSubject.getText(),
                datePicker.getValue().toString(),
                txtLecturerId.getText(),
                txtStartTime.getText(),
                txtEndTime.getText()
        );
        try {
            if (service.updateSchedule(dto)) {
                showAlert("Updated", "Schedule updated successfully");
                loadTable();
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            if (service.deleteSchedule(txtScheduleId.getText())) {
                showAlert("Deleted", "Schedule deleted successfully");
                loadTable();
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void backToDashboardOnAction(ActionEvent actionEvent) {

        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Admin Dashboard"); // Also fixed the typo "coursr" -> "Course"
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

    }
}
