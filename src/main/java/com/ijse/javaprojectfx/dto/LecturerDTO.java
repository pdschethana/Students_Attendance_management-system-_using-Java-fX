package com.ijse.javaprojectfx.dto;

public class LecturerDTO {
    private String lecturerId;
    private String lecturerName;
    private String contactDetails;
    private String subject;

    public LecturerDTO(String lecturerId, String lecturerName, String contactDetails, String subject) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.contactDetails = contactDetails;
        this.subject = subject;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
