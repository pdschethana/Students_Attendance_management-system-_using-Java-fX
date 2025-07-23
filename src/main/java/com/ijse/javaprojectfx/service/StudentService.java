package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.StudentDAO;
import com.ijse.javaprojectfx.dto.StudentDTO;

import java.util.List;

public class StudentService {
    private final StudentDAO dao = new StudentDAO();

    public List<StudentDTO> getAllStudents() { return dao.getAllStudents(); }
    public boolean addStudent(StudentDTO s)   { return dao.addStudent(s); }
    public boolean updateStudent(StudentDTO s){ return dao.updateStudent(s); }
    public boolean deleteStudent(String id)   { return dao.deleteStudent(id); }
    public StudentDTO findStudent(String id)  { return dao.findStudentById(id); }
}
