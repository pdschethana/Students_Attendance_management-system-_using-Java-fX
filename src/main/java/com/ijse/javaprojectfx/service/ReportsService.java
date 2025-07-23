package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.ReportsDAO;
import com.ijse.javaprojectfx.dto.ReportDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service layer for Reports functionality
 * Handles business logic and validation for report operations
 */
public class ReportsService {

    private final ReportsDAO reportsDAO;

    public ReportsService() {
        this.reportsDAO = new ReportsDAO();
    }

    // Constructor for dependency injection (useful for testing)
    public ReportsService(ReportsDAO reportsDAO) {
        this.reportsDAO = reportsDAO;
    }

    /**
     * Get all available course names
     * @return List of course names
     */
    public List<String> getAllCourseNames() {
        try {
            return reportsDAO.getAllCourseNames();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve course names: " + e.getMessage(), e);
        }
    }

    /**
     * Get filtered reports based on search criteria
     * @param studentIdOrName Student ID or name filter
     * @param courseName Course name filter
     * @param fromDate Start date filter
     * @param toDate End date filter
     * @return List of filtered reports
     */
    public List<ReportDTO> getFilteredReports(String studentIdOrName, String courseName,
                                              LocalDate fromDate, LocalDate toDate) {

        // Validate date range
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // Validate future dates
        LocalDate today = LocalDate.now();
        if (fromDate != null && fromDate.isAfter(today)) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
        if (toDate != null && toDate.isAfter(today)) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }

        try {
            return reportsDAO.getFilteredReports(studentIdOrName, courseName, fromDate, toDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve filtered reports: " + e.getMessage(), e);
        }
    }

    /**
     * Get all reports without any filters
     * @return List of all reports
     */
    public List<ReportDTO> getAllReports() {
        try {
            return reportsDAO.getAllReports();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all reports: " + e.getMessage(), e);
        }
    }

    /**
     * Get attendance statistics from a list of reports
     * @param reports List of reports to analyze
     * @return Map containing attendance statistics
     */
    public Map<String, Long> getAttendanceStatistics(List<ReportDTO> reports) {
        if (reports == null || reports.isEmpty()) {
            return Map.of(
                    "total", 0L,
                    "present", 0L,
                    "absent", 0L,
                    "late", 0L
            );
        }

        Map<String, Long> statusCounts = reports.stream()
                .collect(Collectors.groupingBy(
                        report -> report.getStatus().toLowerCase(),
                        Collectors.counting()
                ));

        return Map.of(
                "total", (long) reports.size(),
                "present", statusCounts.getOrDefault("present", 0L),
                "absent", statusCounts.getOrDefault("absent", 0L),
                "late", statusCounts.getOrDefault("late", 0L)
        );
    }

    /**
     * Get attendance percentage for a specific student
     * @param studentName Student name
     * @param reports List of reports
     * @return Attendance percentage
     */
    public double getStudentAttendancePercentage(String studentName, List<ReportDTO> reports) {
        if (reports == null || reports.isEmpty() || studentName == null) {
            return 0.0;
        }

        List<ReportDTO> studentReports = reports.stream()
                .filter(report -> studentName.equalsIgnoreCase(report.getStudentName()))
                .collect(Collectors.toList());

        if (studentReports.isEmpty()) {
            return 0.0;
        }

        long presentCount = studentReports.stream()
                .filter(report -> "present".equalsIgnoreCase(report.getStatus()))
                .count();

        return (double) presentCount / studentReports.size() * 100;
    }

    /**
     * Get course-wise attendance summary
     * @param reports List of reports
     * @return Map of course names to attendance statistics
     */
    public Map<String, Map<String, Long>> getCourseWiseAttendance(List<ReportDTO> reports) {
        if (reports == null || reports.isEmpty()) {
            return Map.of();
        }

        return reports.stream()
                .collect(Collectors.groupingBy(
                        ReportDTO::getSubject,
                        Collectors.groupingBy(
                                report -> report.getStatus().toLowerCase(),
                                Collectors.counting()
                        )
                ));
    }

    /**
     * Validate report data
     * @param report Report to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidReport(ReportDTO report) {
        if (report == null) {
            return false;
        }

        return report.getStudentName() != null && !report.getStudentName().trim().isEmpty() &&
                report.getSubject() != null && !report.getSubject().trim().isEmpty() &&
                report.getDate() != null && !report.getDate().trim().isEmpty() &&
                report.getStatus() != null && isValidStatus(report.getStatus());
    }

    /**
     * Check if attendance status is valid
     * @param status Status to check
     * @return true if valid status
     */
    private boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }

        String lowerStatus = status.toLowerCase().trim();
        return lowerStatus.equals("present") ||
                lowerStatus.equals("absent") ||
                lowerStatus.equals("late");
    }

    /**
     * Get reports for a specific date range with additional filtering
     * @param fromDate Start date
     * @param toDate End date
     * @param minAttendancePercentage Minimum attendance percentage filter
     * @return List of filtered reports
     */
    public List<ReportDTO> getReportsWithAttendanceFilter(LocalDate fromDate, LocalDate toDate,
                                                          double minAttendancePercentage) {
        List<ReportDTO> allReports = getFilteredReports("", null, fromDate, toDate);

        if (minAttendancePercentage <= 0) {
            return allReports;
        }

        // Group by student and filter based on attendance percentage
        Map<String, List<ReportDTO>> studentReports = allReports.stream()
                .collect(Collectors.groupingBy(ReportDTO::getStudentName));

        return studentReports.entrySet().stream()
                .filter(entry -> {
                    List<ReportDTO> reports = entry.getValue();
                    long presentCount = reports.stream()
                            .filter(report -> "present".equalsIgnoreCase(report.getStatus()))
                            .count();
                    double percentage = (double) presentCount / reports.size() * 100;
                    return percentage >= minAttendancePercentage;
                })
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }

    /**
     * Get total count of reports
     * @return Total number of reports
     */
    public int getTotalReportsCount() {
        try {
            return reportsDAO.getTotalReportsCount();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get total reports count: " + e.getMessage(), e);
        }
    }
}