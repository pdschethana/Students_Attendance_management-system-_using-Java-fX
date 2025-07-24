package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dto.AttendanceDTO;
import com.ijse.javaprojectfx.service.AttendanceService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AttendanceManagementController {

    @FXML
    private TextField attid;
    @FXML
    private TextField attstudentid;
    @FXML
    private TextField attscheduleid;
    @FXML
    private TextField attstatus;
    @FXML
    private DatePicker attdate;
    @FXML
    private TableView<AttendanceDTO> tblAttendance;
    @FXML
    private TableColumn<AttendanceDTO, String> colattid, colattstudent, colattschedule, colattdate, colattstatus;

    private final AttendanceService svc = new AttendanceService();

    @FXML
    public void initialize() {
        colattid.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAttendanceId()));
        colattstudent.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStudentId()));
        colattschedule.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getScheduleId()));
        colattdate.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));
        colattstatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        // Set up table row selection to populate fields
        tblAttendance.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                attid.setText(newSelection.getAttendanceId());
                attstudentid.setText(newSelection.getStudentId());
                attscheduleid.setText(newSelection.getScheduleId());
                attdate.setValue(newSelection.getDate());
                attstatus.setText(newSelection.getStatus());
            }
        });

        loadAll();
    }

    private void loadAll() {
        tblAttendance.setItems(FXCollections.observableArrayList(svc.getAll()));
    }

    @FXML
    public void onAdd() {
        try {
            if (validateFields()) {
                AttendanceDTO dto = new AttendanceDTO(
                        attid.getText(),
                        attstudentid.getText(),
                        attscheduleid.getText(),
                        attdate.getValue(),
                        attstatus.getText()
                );

                if (svc.add(dto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Attendance added successfully!").show();
                    loadAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to add attendance!").show();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void onUpdate() {
        try {
            if (validateFields()) {
                AttendanceDTO dto = new AttendanceDTO(
                        attid.getText(),
                        attstudentid.getText(),
                        attscheduleid.getText(),
                        attdate.getValue(),
                        attstatus.getText()
                );

                if (svc.update(dto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Attendance updated successfully!").show();
                    loadAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update attendance!").show();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void onDelete() {
        try {
            String id = attid.getText();
            if (id.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select an attendance record to delete").show();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this attendance record?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (svc.delete(id)) {
                        new Alert(Alert.AlertType.INFORMATION, "Attendance deleted successfully!").show();
                        loadAll();
                        clear();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete attendance!").show();
                    }
                }
            });
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void onClear() {
        clear();
    }

    @FXML
    public void onSearch() {
        try {
            String id = attid.getText();
            if (id.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter an attendance ID to search").show();
                return;
            }

            AttendanceDTO dto = svc.search(id);
            if (dto != null) {
                attstudentid.setText(dto.getStudentId());
                attscheduleid.setText(dto.getScheduleId());
                attdate.setValue(dto.getDate());
                attstatus.setText(dto.getStatus());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No attendance record found with ID: " + id).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (attid.getText().isEmpty() ||
                attstudentid.getText().isEmpty() ||
                attscheduleid.getText().isEmpty() ||
                attdate.getValue() == null ||
                attstatus.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return false;
        }

        if (!attstatus.getText().matches("Present|Absent|Late")) {
            new Alert(Alert.AlertType.WARNING, "Status must be either Present, Absent, or Late").show();
            return false;
        }

        return true;
    }

    private void clear() {
        attid.clear();
        attstudentid.clear();
        attscheduleid.clear();
        attdate.setValue(null);
        attstatus.clear();
        tblAttendance.getSelectionModel().clearSelection();
    }

    @FXML
    public void backattendancemanagementtoadmindshbrd(ActionEvent actionEvent) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading admin dashboard: " + e.getMessage()).show();
        }
    }
}


