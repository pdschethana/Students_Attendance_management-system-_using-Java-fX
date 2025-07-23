package com.ijse.javaprojectfx.controller;

import com.ijse.javaprojectfx.dao.ReportsDAO;
import com.ijse.javaprojectfx.dto.ReportDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.print.PrinterJob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReportsController {

    @FXML
    private TextField reportstudentid;
    @FXML
    private ComboBox<String> reportcourseid;
    @FXML
    private DatePicker reportfromdate;
    @FXML
    private DatePicker reporttodate;
    @FXML
    private TableView<ReportDTO> tblReports;
    @FXML
    private TableColumn<ReportDTO, String> colreportstudent;
    @FXML
    private TableColumn<ReportDTO, String> colreportsubject;
    @FXML
    private TableColumn<ReportDTO, String> colreportdate;
    @FXML
    private TableColumn<ReportDTO, String> colreportstatus;
    @FXML
    private Label lblTotalReports;
    @FXML
    private Label lblPresentCount;
    @FXML
    private Label lblAbsentCount;
    @FXML
    private Label lblLateCount;

    private final ReportsDAO reportsDAO = new ReportsDAO();
    private ObservableList<ReportDTO> currentReports = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        colreportstudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colreportsubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colreportdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colreportstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load courses into ComboBox
        loadCourses();

        // Load all reports initially
        loadAllReports();
    }

    private void loadCourses() {
        try {
            List<String> courses = reportsDAO.getAllCourseNames();
            reportcourseid.setItems(FXCollections.observableArrayList(courses));
        } catch (Exception e) {
            showAlert("Error", "Failed to load courses: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadAllReports() {
        try {
            List<ReportDTO> reports = reportsDAO.getFilteredReports("", null, null, null);
            currentReports.clear();
            currentReports.addAll(reports);
            tblReports.setItems(currentReports);
            updateSummaryLabels();
        } catch (Exception e) {
            showAlert("Error", "Failed to load reports: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onGenerate(ActionEvent event) {
        try {
            String studentId = reportstudentid.getText().trim();
            String courseName = reportcourseid.getValue();
            LocalDate from = reportfromdate.getValue();
            LocalDate to = reporttodate.getValue();

            List<ReportDTO> reports = reportsDAO.getFilteredReports(studentId, courseName, from, to);
            currentReports.clear();
            currentReports.addAll(reports);
            tblReports.setItems(currentReports);

            updateSummaryLabels();

            if (reports.isEmpty()) {
                showAlert("Information", "No records found for the selected criteria.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to generate report: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onClear(ActionEvent event) {
        reportstudentid.clear();
        reportcourseid.setValue(null);
        reportfromdate.setValue(null);
        reporttodate.setValue(null);
        loadAllReports();
    }

    @FXML
    public void onExportPDF(ActionEvent event) {
        if (currentReports.isEmpty()) {
            showAlert("Warning", "No data to export. Please generate a report first.", Alert.AlertType.WARNING);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) tblReports.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Note: You'll need to implement PDF generation using a library like iText
            showAlert("Information", "PDF export functionality needs to be implemented with iText library.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void onExportExcel(ActionEvent event) {
        if (currentReports.isEmpty()) {
            showAlert("Warning", "No data to export. Please generate a report first.", Alert.AlertType.WARNING);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));

        Stage stage = (Stage) tblReports.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                exportToCSV(file);
                showAlert("Success", "Report exported successfully to: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                showAlert("Error", "Failed to export report: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void onPrint(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(tblReports.getScene().getWindow())) {
            boolean success = job.printPage(tblReports);
            if (success) {
                job.endJob();
                showAlert("Success", "Report sent to printer successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to print report.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void reportbackOnAction(ActionEvent actionEvent) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/view/admindashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to navigate to admin dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateSummaryLabels() {
        int total = currentReports.size();
        long presentCount = currentReports.stream().filter(r -> "Present".equals(r.getStatus())).count();
        long absentCount = currentReports.stream().filter(r -> "Absent".equals(r.getStatus())).count();
        long lateCount = currentReports.stream().filter(r -> "Late".equals(r.getStatus())).count();

        lblTotalReports.setText(String.valueOf(total));
        lblPresentCount.setText(String.valueOf(presentCount));
        lblAbsentCount.setText(String.valueOf(absentCount));
        lblLateCount.setText(String.valueOf(lateCount));
    }

    private void exportToCSV(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            // Write header
            writer.append("Student Name,Subject,Date,Status\n");

            // Write data
            for (ReportDTO report : currentReports) {
                writer.append(report.getStudentName()).append(",");
                writer.append(report.getSubject()).append(",");
                writer.append(report.getDate()).append(",");
                writer.append(report.getStatus()).append("\n");
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}