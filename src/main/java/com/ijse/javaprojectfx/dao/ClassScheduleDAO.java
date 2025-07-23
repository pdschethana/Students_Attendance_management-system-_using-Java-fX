package com.ijse.javaprojectfx.dao;

import com.ijse.javaprojectfx.db.DBConnection;
import com.ijse.javaprojectfx.dto.ClassScheduleDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassScheduleDAO {

    public boolean addSchedule(ClassScheduleDTO schedule) throws SQLException {
        String sql = "INSERT INTO class_schedules (schedule_id, course_id, subject, schedule_date, lecturer_id, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, schedule.getScheduleId());
        pst.setString(2, schedule.getCourseId());
        pst.setString(3, schedule.getSubject());
        pst.setString(4, schedule.getDate());
        pst.setString(5, schedule.getLecturerId());
        pst.setString(6, schedule.getStartTime());
        pst.setString(7, schedule.getEndTime());
        return pst.executeUpdate() > 0;
    }

    public boolean updateSchedule(ClassScheduleDTO schedule) throws SQLException {
        String sql = "UPDATE class_schedules SET course_id=?, subject=?, schedule_date=?, lecturer_id=?, start_time=?, end_time=? WHERE schedule_id=?";
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, schedule.getCourseId());
        pst.setString(2, schedule.getSubject());
        pst.setString(3, schedule.getDate());
        pst.setString(4, schedule.getLecturerId());
        pst.setString(5, schedule.getStartTime());
        pst.setString(6, schedule.getEndTime());
        pst.setString(7, schedule.getScheduleId());
        return pst.executeUpdate() > 0;
    }

    public boolean deleteSchedule(String scheduleId) throws SQLException {
        String sql = "DELETE FROM class_schedules WHERE schedule_id=?";
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, scheduleId);
        return pst.executeUpdate() > 0;
    }

    public List<ClassScheduleDTO> getAllSchedules() throws SQLException {
        String sql = "SELECT * FROM class_schedules";
        Connection con = DBConnection.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<ClassScheduleDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new ClassScheduleDTO(
                    rs.getString("schedule_id"),
                    rs.getString("course_id"),
                    rs.getString("subject"),
                    rs.getString("schedule_date"),
                    rs.getString("lecturer_id"),
                    rs.getString("start_time"),
                    rs.getString("end_time")
            ));
        }
        return list;
    }
}
