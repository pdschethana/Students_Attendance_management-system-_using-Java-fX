package com.ijse.javaprojectfx.dto;

public class ClassScheduleDTO {
    private String scheduleId;
    private String courseId;
    private String subject;
    private String date;
    private String lecturerId;
    private String startTime;
    private String endTime;

    public ClassScheduleDTO() {}

    public ClassScheduleDTO(String scheduleId, String courseId, String subject, String date, String lecturerId, String startTime, String endTime) {
        this.scheduleId = scheduleId;
        this.courseId = courseId;
        this.subject = subject;
        this.date = date;
        this.lecturerId = lecturerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getScheduleId() { return scheduleId; }
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLecturerId() { return lecturerId; }
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
