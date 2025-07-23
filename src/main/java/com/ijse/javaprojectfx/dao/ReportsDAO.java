package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.ReportDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Reports functionality
 * Handles database operations for report-related queries
 */
public class ReportsDAO {

    /**
     * Get all course names from the database
     * @return List of course names
     */
    public List<String> getAllCourseNames() {
        List<String> courseNames = new ArrayList<>();
        String sql = "SELECT course_name FROM courses ORDER BY course_name";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                courseNames.add(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching course names: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch course names", e);
        }
        return courseNames;
    }

    /**
     * Get filtered reports based on search criteria
     * @param studentIdOrName Student ID or name filter
     * @param courseName Course name filter
     * @param from Start date filter
     * @param to End date filter
     * @return List of filtered reports
     */
    public List<ReportDTO> getFilteredReports(String studentIdOrName, String courseName, LocalDate from, LocalDate to) {
        List<ReportDTO> reports = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT s.student_name, cs.subject, a.attendance_date, a.status ");
        sql.append("FROM attendance a ");
        sql.append("JOIN students s ON a.student_id = s.student_id ");
        sql.append("JOIN class_schedules cs ON a.schedule_id = cs.schedule_id ");
        sql.append("JOIN courses c ON s.course_id = c.course_id ");
        sql.append("WHERE 1=1 ");

        List<Object> parameters = new ArrayList<>();

        // Add student filter
        if (studentIdOrName != null && !studentIdOrName.trim().isEmpty()) {
            sql.append("AND (s.student_id LIKE ? OR s.student_name LIKE ?) ");
            String searchPattern = "%" + studentIdOrName.trim() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        // Add course filter
        if (courseName != null && !courseName.trim().isEmpty()) {
            sql.append("AND c.course_name = ? ");
            parameters.add(courseName);
        }

        // Add date range filters
        if (from != null) {
            sql.append("AND a.attendance_date >= ? ");
            parameters.add(Date.valueOf(from));
        }

        if (to != null) {
            sql.append("AND a.attendance_date <= ? ");
            parameters.add(Date.valueOf(to));
        }

        sql.append("ORDER BY a.attendance_date DESC");

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReportDTO report = new ReportDTO(
                            rs.getString("student_name"),
                            rs.getString("subject"),
                            rs.getDate("attendance_date").toString(),
                            rs.getString("status")
                    );
                    reports.add(report);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching filtered reports: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch filtered reports", e);
        }

        return reports;
    }

    /**
     * Get all reports without any filters
     * @return List of all reports
     */
    public List<ReportDTO> getAllReports() {
        return getFilteredReports("", null, null, null);
    }

    /**
     * Get total count of attendance records
     * @return Total number of attendance records
     */
    public int getTotalReportsCount() {
        String sql = "SELECT COUNT(*) as total FROM attendance";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total reports count: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch total reports count", e);
        }
        return 0;
    }

    /**
     * Get attendance statistics by status
     * @return Array containing [total, present, absent, late] counts
     */
    public int[] getAttendanceStatistics() {
        String sql = "SELECT " +
                "COUNT(*) as total, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as present, " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) as absent, " +
                "SUM(CASE WHEN status = 'Late' THEN 1 ELSE 0 END) as late " +
                "FROM attendance";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new int[]{
                        rs.getInt("total"),
                        rs.getInt("present"),
                        rs.getInt("absent"),
                        rs.getInt("late")
                };
            }
        } catch (SQLException e) {
            System.err.println("Error fetching attendance statistics: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch attendance statistics", e);
        }
        return new int[]{0, 0, 0, 0};
    }

    /**
     * Get reports for a specific student
     * @param studentId Student ID
     * @return List of reports for the student
     */
    public List<ReportDTO> getReportsByStudent(String studentId) {
        List<ReportDTO> reports = new ArrayList<>();
        String sql = "SELECT s.student_name, cs.subject, a.attendance_date, a.status " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.student_id " +
                "JOIN class_schedules cs ON a.schedule_id = cs.schedule_id " +
                "WHERE s.student_id = ? " +
                "ORDER BY a.attendance_date DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReportDTO report = new ReportDTO(
                            rs.getString("student_name"),
                            rs.getString("subject"),
                            rs.getDate("attendance_date").toString(),
                            rs.getString("status")
                    );
                    reports.add(report);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching reports by student: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch reports by student", e);
        }

        return reports;
    }

    /**
     * Get reports for a specific course
     * @param courseName Course name
     * @return List of reports for the course
     */
    public List<ReportDTO> getReportsByCourse(String courseName) {
        List<ReportDTO> reports = new ArrayList<>();
        String sql = "SELECT s.student_name, cs.subject, a.attendance_date, a.status " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.student_id " +
                "JOIN class_schedules cs ON a.schedule_id = cs.schedule_id " +
                "JOIN courses c ON s.course_id = c.course_id " +
                "WHERE c.course_name = ? " +
                "ORDER BY a.attendance_date DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReportDTO report = new ReportDTO(
                            rs.getString("student_name"),
                            rs.getString("subject"),
                            rs.getDate("attendance_date").toString(),
                            rs.getString("status")
                    );
                    reports.add(report);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching reports by course: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch reports by course", e);
        }

        return reports;
    }

    /**
     * Get reports for a specific date range
     * @param fromDate Start date
     * @param toDate End date
     * @return List of reports within the date range
     */
    public List<ReportDTO> getReportsByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<ReportDTO> reports = new ArrayList<>();
        String sql = "SELECT s.student_name, cs.subject, a.attendance_date, a.status " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.student_id " +
                "JOIN class_schedules cs ON a.schedule_id = cs.schedule_id " +
                "WHERE a.attendance_date BETWEEN ? AND ? " +
                "ORDER BY a.attendance_date DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReportDTO report = new ReportDTO(
                            rs.getString("student_name"),
                            rs.getString("subject"),
                            rs.getDate("attendance_date").toString(),
                            rs.getString("status")
                    );
                    reports.add(report);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching reports by date range: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch reports by date range", e);
        }

        return reports;
    }

    /**
     * Check if database connection is available
     * @return true if connection is available, false otherwise
     */
    public boolean isConnectionAvailable() {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error checking database connection: " + e.getMessage());
            return false;
        }
    }
}