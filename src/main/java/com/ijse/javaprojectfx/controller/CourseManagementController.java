
package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dto.CourseDTO;
import com.ijse.javaprojectfx.service.CourseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseManagementController implements Initializable {

    public TextField courseid;
    public TextField coursename;
    public TextField coursesearchbar;
    public TableView<CourseDTO> crstable;
    public TableColumn<CourseDTO, String> colcourseid;
    public TableColumn<CourseDTO, String> colcoursename;

    private final CourseService courseService = new CourseService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colcourseid.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCourseId()));
        colcoursename.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCourseName()));
        loadCourses();
    }

    private void loadCourses() {
        ObservableList<CourseDTO> courseList = FXCollections.observableArrayList(courseService.getAllCourses());
        crstable.setItems(courseList);
    }

    public void AddcrsOnAction(ActionEvent actionEvent) {
        CourseDTO course = new CourseDTO(courseid.getText(), coursename.getText());
        if (courseService.addCourse(course)) {
            loadCourses();
            clearFields();
        }
    }

    public void updatecrsOnAction(ActionEvent actionEvent) {
        CourseDTO course = new CourseDTO(courseid.getText(), coursename.getText());
        if (courseService.updateCourse(course)) {
            loadCourses();
            clearFields();
        }
    }

    public void delecrsOnAction(ActionEvent actionEvent) {
        String id = courseid.getText();
        if (courseService.deleteCourse(id)) {
            loadCourses();
            clearFields();
        }
    }

    public void searchcrsOnAction(ActionEvent actionEvent) {
        String id = coursesearchbar.getText();
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            courseid.setText(course.getCourseId());
            coursename.setText(course.getCourseName());
        } else {
            clearFields();
        }
    }

    public void backcoursrfromdashboard(ActionEvent actionEvent) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Admin dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        courseid.clear();
        coursename.clear();
        coursesearchbar.clear();
    }
}
