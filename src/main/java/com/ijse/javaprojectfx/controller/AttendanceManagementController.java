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

    @FXML private TextField attid;
    @FXML private ComboBox<String> attstudentid, attscheduleid, attstatus;
    @FXML private DatePicker attdate;
    @FXML private TableView<AttendanceDTO> tblAttendance;
    @FXML private TableColumn<AttendanceDTO, String> colattid, colattstudent, colattschedule, colattdate, colattstatus;

    private final AttendanceService svc = new AttendanceService();

    @FXML public void initialize() {
        colattid.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAttendanceId()));
        colattstudent.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStudentId()));
        colattschedule.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getScheduleId()));
        colattdate.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));
        colattstatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        attstatus.getItems().addAll("Present", "Absent", "Late");
        loadAll();
    }

    private void loadAll() {
        tblAttendance.setItems(FXCollections.observableArrayList(svc.getAll()));
    }

    public void onAdd() {
        AttendanceDTO dto = new AttendanceDTO(attid.getText(), attstudentid.getValue(), attscheduleid.getValue(), attdate.getValue(), attstatus.getValue());
        if (svc.add(dto)) { loadAll(); clear(); }
    }

    public void onUpdate() {
        AttendanceDTO dto = new AttendanceDTO(attid.getText(), attstudentid.getValue(), attscheduleid.getValue(), attdate.getValue(), attstatus.getValue());
        if (svc.update(dto)) { loadAll(); clear(); }
    }

    public void onDelete() {
        if (svc.delete(attid.getText())) { loadAll(); clear(); }
    }

    public void onClear() { clear(); }

    public void onSearch() {
        AttendanceDTO dto = svc.search(attid.getText());
        if (dto != null) {
            attstudentid.setValue(dto.getStudentId());
            attscheduleid.setValue(dto.getScheduleId());
            attdate.setValue(dto.getDate());
            attstatus.setValue(dto.getStatus());
        } else new Alert(Alert.AlertType.INFORMATION, "Not found").show();
    }

    private void clear() {
        attid.clear();
        attstudentid.getSelectionModel().clearSelection();
        attscheduleid.getSelectionModel().clearSelection();
        attdate.setValue(null);
        attstatus.getSelectionModel().clearSelection();
    }

    public void backattendancemanagementtoadmindshbrd(ActionEvent actionEvent) {
        try {
            // Fixed: Corrected the resource path to match the pattern used in other methods
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("admin dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
