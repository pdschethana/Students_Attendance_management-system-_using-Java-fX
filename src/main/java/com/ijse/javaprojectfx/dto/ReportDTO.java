package com.ijse.javaprojectfx.dto;

/**
 * Data Transfer Object for Report data
 * Used to transfer report information between layers
 */
public class ReportDTO {
    private String studentName;
    private String subject;
    private String date;
    private String status;

    // Default constructor
    public ReportDTO() {
    }

    // Parameterized constructor
    public ReportDTO(String studentName, String subject, String date, String status) {
        this.studentName = studentName;
        this.subject = subject;
        this.date = date;
        this.status = status;
    }

    // Getter and Setter methods
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "ReportDTO{" +
                "studentName='" + studentName + '\'' +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    // equals and hashCode methods for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ReportDTO reportDTO = (ReportDTO) obj;

        if (studentName != null ? !studentName.equals(reportDTO.studentName) : reportDTO.studentName != null)
            return false;
        if (subject != null ? !subject.equals(reportDTO.subject) : reportDTO.subject != null)
            return false;
        if (date != null ? !date.equals(reportDTO.date) : reportDTO.date != null)
            return false;
        return status != null ? status.equals(reportDTO.status) : reportDTO.status == null;
    }

    @Override
    public int hashCode() {
        int result = studentName != null ? studentName.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}