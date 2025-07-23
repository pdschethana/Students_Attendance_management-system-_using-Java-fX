package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private final Connection connection = DBConnection.getInstance().getConnection();

    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> list = new ArrayList<>();
        String sql = "SELECT student_id, student_name, contact_details, course_id FROM students";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new StudentDTO(
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("contact_details"),
                        rs.getString("course_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addStudent(StudentDTO s) {
        String sql = "INSERT INTO students (student_id, student_name, contact_details, course_id, enrollment_date) VALUES (?, ?, ?, ?, CURDATE())";
        try (PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1, s.getStudentId());
            ps.setString(2, s.getStudentName());
            ps.setString(3, s.getContactDetails());
            ps.setString(4, s.getCourseId());
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    public boolean updateStudent(StudentDTO s) {
        String sql = "UPDATE students SET student_name=?, contact_details=?, course_id=? WHERE student_id=?";
        try (PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1, s.getStudentName());
            ps.setString(2, s.getContactDetails());
            ps.setString(3, s.getCourseId());
            ps.setString(4, s.getStudentId());
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate()>0;
        } catch(SQLException e) {
            e.printStackTrace(); return false;
        }
    }

    public StudentDTO findStudentById(String id) {
        String sql = "SELECT student_id, student_name, contact_details, course_id FROM students WHERE student_id=?";
        try (PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs=ps.executeQuery()) {
                if (rs.next()) {
                    return new StudentDTO(
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("contact_details"),
                            rs.getString("course_id")
                    );
                }
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }
}
