package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.AttendanceDAO;
import com.ijse.javaprojectfx.dto.AttendanceDTO;

import java.util.List;

public class AttendanceService {
    private final AttendanceDAO dao = new AttendanceDAO();
    public List<AttendanceDTO> getAll() { return dao.getAll(); }
    public boolean add(AttendanceDTO dto) { return dao.add(dto); }
    public boolean update(AttendanceDTO dto) { return dao.update(dto); }
    public boolean delete(String id) { return dao.delete(id); }
    public AttendanceDTO search(String id) { return dao.search(id); }
}
