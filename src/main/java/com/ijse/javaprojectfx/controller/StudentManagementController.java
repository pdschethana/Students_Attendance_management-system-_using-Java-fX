package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dto.StudentDTO;
import com.ijse.javaprojectfx.service.StudentService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class StudentManagementController {

    @FXML private TextField stuid, stuname, studetails, stucrsid, stusearchbar;
    @FXML private TableView<StudentDTO> studenttable;
    @FXML private TableColumn<StudentDTO,String> stucolid, stucolname, stucoldetails, stucolcrsid;

    private final StudentService service = new StudentService();

    @FXML public void initialize() {
        stucolid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStudentId()));
        stucolname.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStudentName()));
        stucoldetails.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getContactDetails()));
        stucolcrsid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseId()));

        studenttable.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, s) -> {
                    if (s != null) {
                        stuid.setText(s.getStudentId());
                        stuname.setText(s.getStudentName());
                        studetails.setText(s.getContactDetails());
                        stucrsid.setText(s.getCourseId());
                    }
                });

        loadStudents();
    }

    private void loadStudents() {
        List<StudentDTO> list = service.getAllStudents();
        studenttable.setItems(FXCollections.observableArrayList(list));
    }

    @FXML public void stuaddOnAction() {
        StudentDTO s = new StudentDTO(stuid.getText(), stuname.getText(), studetails.getText(), stucrsid.getText());
        if (service.addStudent(s)) loadStudents();
    }

    @FXML public void stuUpdateOnAction() {
        StudentDTO s = new StudentDTO(stuid.getText(), stuname.getText(), studetails.getText(), stucrsid.getText());
        if (service.updateStudent(s)) loadStudents();
    }

    @FXML public void stuDeleteOnAction() {
        if (service.deleteStudent(stuid.getText())) {
            loadStudents();
            clearForm();
        }
    }

    @FXML public void stuSearchOnAction() {
        StudentDTO s = service.findStudent(stusearchbar.getText());
        if (s != null) {
            studenttable.setItems(FXCollections.observableArrayList(s));
        }
    }

    private void clearForm() {
        stuid.clear(); stuname.clear(); studetails.clear(); stucrsid.clear();
    }

    public void backstudentfromdashboard(ActionEvent actionEvent) {
    }
}
