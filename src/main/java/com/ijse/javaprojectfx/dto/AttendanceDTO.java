package com.ijse.javaprojectfx.dto;

import java.time.LocalDate;

public class AttendanceDTO {
    private String attendanceId;
    private String studentId;
    private String scheduleId;
    private LocalDate date;
    private String status;

    public AttendanceDTO(String attendanceId, String studentId, String scheduleId, LocalDate date, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.scheduleId = scheduleId;
        this.date = date;
        this.status = status;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    // Optionally, add setters if needed
    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
