package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.CourseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<CourseDTO> getAllCourses() {
        List<CourseDTO> courses = new ArrayList<>();
        String sql = "SELECT course_id, course_name FROM courses";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(new CourseDTO(
                        rs.getString("course_id"),
                        rs.getString("course_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public boolean addCourse(CourseDTO course) {
        String sql = "INSERT INTO courses (course_id, course_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseId());
            ps.setString(2, course.getCourseName());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCourse(CourseDTO course) {
        String sql = "UPDATE courses SET course_name=? WHERE course_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCourse(String courseId) {
        String sql = "DELETE FROM courses WHERE course_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CourseDTO getCourseById(String courseId) {
        String sql = "SELECT course_id, course_name FROM courses WHERE course_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CourseDTO(
                        rs.getString("course_id"),
                        rs.getString("course_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
