
package com.ijse.javaprojectfx.service;

import com.ijse.javaprojectfx.dao.ClassScheduleDAO;
import com.ijse.javaprojectfx.dto.ClassScheduleDTO;

import java.sql.SQLException;
import java.util.List;

public class ClassScheduleService {
    private final ClassScheduleDAO dao = new ClassScheduleDAO();

    public boolean addSchedule(ClassScheduleDTO dto) throws SQLException {
        return dao.addSchedule(dto);
    }

    public boolean updateSchedule(ClassScheduleDTO dto) throws SQLException {
        return dao.updateSchedule(dto);
    }

    public boolean deleteSchedule(String scheduleId) throws SQLException {
        return dao.deleteSchedule(scheduleId);
    }

    public List<ClassScheduleDTO> getAllSchedules() throws SQLException {
        return dao.getAllSchedules();
    }
}
