package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.CourseDAO;
import com.ijse.javaprojectfx.dto.CourseDTO;

import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    public List<CourseDTO> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public boolean addCourse(CourseDTO course) {
        return courseDAO.addCourse(course);
    }

    public boolean updateCourse(CourseDTO course) {
        return courseDAO.updateCourse(course);
    }

    public boolean deleteCourse(String courseId) {
        return courseDAO.deleteCourse(courseId);
    }

    public CourseDTO getCourseById(String courseId) {
        return courseDAO.getCourseById(courseId);
    }
}
