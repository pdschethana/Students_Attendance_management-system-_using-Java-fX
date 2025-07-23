package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.LecturerDAO;
import com.ijse.javaprojectfx.dto.LecturerDTO;

import java.util.List;

public class LecturerService {

    private final LecturerDAO dao = new LecturerDAO();

    public List<LecturerDTO> getAllLecturers() {
        return dao.getAllLecturers();
    }

    public boolean addLecturer(LecturerDTO dto) {
        return dao.addLecturer(dto);
    }

    public boolean updateLecturer(LecturerDTO dto) {
        return dao.updateLecturer(dto);
    }

    public boolean deleteLecturer(String id) {
        return dao.deleteLecturer(id);
    }

    public LecturerDTO searchLecturer(String id) {
        return dao.searchLecturer(id);
    }
}
