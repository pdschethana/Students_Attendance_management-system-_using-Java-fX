package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dto.LecturerDTO;
import com.ijse.javaprojectfx.service.LecturerService;
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

public class LecturerManagementController {

    public TextField lecid;
    public TextField lecname;
    public TextField leccontact;
    public TextField lecsubject;
    public TextField lecsearchbar;
    public TableView<LecturerDTO> tablevi;
    public TableColumn<LecturerDTO, String> collecid;
    public TableColumn<LecturerDTO, String> collecname;
    public TableColumn<LecturerDTO, String> collecdetails;
    public TableColumn<LecturerDTO, String> collecsubject;

    private final LecturerService service = new LecturerService();

    @FXML
    public void initialize() {
        collecid.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getLecturerId()));
        collecname.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getLecturerName()));
        collecdetails.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getContactDetails()));
        collecsubject.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSubject()));

        loadAllLecturers();
    }

    private void loadAllLecturers() {
        ObservableList<LecturerDTO> list = FXCollections.observableArrayList(service.getAllLecturers());
        tablevi.setItems(list);
    }

    public void backlecturerfromdashboard(ActionEvent actionEvent) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLecturer(ActionEvent event) {
        LecturerDTO dto = new LecturerDTO(
                lecid.getText(),
                lecname.getText(),
                leccontact.getText(),
                lecsubject.getText()
        );
        if (service.addLecturer(dto)) {
            loadAllLecturers();
            clearFields();
        }
    }

    public void updateLecturer(ActionEvent event) {
        LecturerDTO dto = new LecturerDTO(
                lecid.getText(),
                lecname.getText(),
                leccontact.getText(),
                lecsubject.getText()
        );
        if (service.updateLecturer(dto)) {
            loadAllLecturers();
            clearFields();
        }
    }

    public void deleteLecturer(ActionEvent event) {
        String id = lecid.getText();
        if (service.deleteLecturer(id)) {
            loadAllLecturers();
            clearFields();
        }
    }

    public void searchLecturer(ActionEvent event) {
        String id = lecsearchbar.getText();
        LecturerDTO dto = service.searchLecturer(id);
        if (dto != null) {
            lecid.setText(dto.getLecturerId());
            lecname.setText(dto.getLecturerName());
            leccontact.setText(dto.getContactDetails());
            lecsubject.setText(dto.getSubject());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Lecturer Not Found");
            alert.show();
        }
    }

    private void clearFields() {
        lecid.clear();
        lecname.clear();
        leccontact.clear();
        lecsubject.clear();
    }
}
