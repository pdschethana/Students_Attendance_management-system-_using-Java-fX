package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.AttendanceDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public List<AttendanceDTO> getAll() {
        List<AttendanceDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean add(AttendanceDTO dto) {
        String sql = "INSERT INTO attendance (attendance_id, student_id, schedule_id, attendance_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, dto.getAttendanceId());
            ps.setString(2, dto.getStudentId());
            ps.setString(3, dto.getScheduleId());
            ps.setDate(4, Date.valueOf(dto.getDate()));
            ps.setString(5, dto.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(AttendanceDTO dto) {
        String sql = "UPDATE attendance SET student_id=?, schedule_id=?, attendance_date=?, status=? WHERE attendance_id=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, dto.getStudentId());
            ps.setString(2, dto.getScheduleId());
            ps.setDate(3, Date.valueOf(dto.getDate()));
            ps.setString(4, dto.getStatus());
            ps.setString(5, dto.getAttendanceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM attendance WHERE attendance_id=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public AttendanceDTO search(String id) {
        String sql = "SELECT * FROM attendance WHERE attendance_id=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private AttendanceDTO map(ResultSet rs) throws SQLException {
        return new AttendanceDTO(
                rs.getString("attendance_id"),
                rs.getString("student_id"),
                rs.getString("schedule_id"),
                rs.getDate("attendance_date").toLocalDate(),
                rs.getString("status")
        );
    }
}
