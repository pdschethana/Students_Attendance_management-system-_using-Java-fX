package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.LecturerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {

    public List<LecturerDTO> getAllLecturers() {
        List<LecturerDTO> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lecturers.add(new LecturerDTO(
                        rs.getString("lecturer_id"),
                        rs.getString("lecturer_name"),
                        rs.getString("contact_details"),
                        rs.getString("subject")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    public boolean addLecturer(LecturerDTO dto) {
        String sql = "INSERT INTO lecturers (lecturer_id, lecturer_name, password, contact_details, subject, email) VALUES (?, ?, 'defaultpass', ?, ?, '')";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getLecturerId());
            ps.setString(2, dto.getLecturerName());
            ps.setString(3, dto.getContactDetails());
            ps.setString(4, dto.getSubject());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLecturer(LecturerDTO dto) {
        String sql = "UPDATE lecturers SET lecturer_name = ?, contact_details = ?, subject = ? WHERE lecturer_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getLecturerName());
            ps.setString(2, dto.getContactDetails());
            ps.setString(3, dto.getSubject());
            ps.setString(4, dto.getLecturerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLecturer(String id) {
        String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LecturerDTO searchLecturer(String id) {
        String sql = "SELECT * FROM lecturers WHERE lecturer_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new LecturerDTO(
                        rs.getString("lecturer_id"),
                        rs.getString("lecturer_name"),
                        rs.getString("contact_details"),
                        rs.getString("subject")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
