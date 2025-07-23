package com.ijse.javaprojectfx.dto;

public class StudentDTO {
    private String studentId;
    private String studentName;
    private String contactDetails;
    private String courseId;

    public StudentDTO(String studentId, String studentName, String contactDetails, String courseId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.contactDetails = contactDetails;
        this.courseId = courseId;
    }

    // Getters & Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getContactDetails() { return contactDetails; }
    public void setContactDetails(String contactDetails) { this.contactDetails = contactDetails; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
}
