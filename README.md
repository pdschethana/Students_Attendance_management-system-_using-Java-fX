 Project Overview: Student Attendance Management System (JavaFX)

This is a JavaFX-based desktop application** for managing student attendance in an academic institution. The system supports roles like **admin**, **lecturers** offering features such as:

* Student registration and management
* Lecturer and course management
* Attendance tracking for scheduled classes
* Reports generation for attendance records
* Admin and lecturer dashboards
* Secure login system



 Setup Instructions

 ✅ Prerequisites

1. Java JDK 21 or compatible
2. Apache Maven (for dependency management and build)
3. MySQL (for database backend)
4. JavaFX SDK 21
5. IDE: IntelliJ IDEA or Eclipse (JavaFX friendly)

  Steps to Run

1. **Clone or Extract the Project**

   Extract the ZIP file and open the project folder in your Java IDE.

2. **Configure JavaFX**

   * Download [JavaFX SDK 21](https://gluonhq.com/products/javafx/)
   * Set the path to JavaFX SDK in your IDE under project structure (VM options):

     
     --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
     





 Technologies Used

| Component             | Technology                               |
| --------------------- | ---------------------------------------- |
| UI Framework          | JavaFX (Controls, FXML v21)              |
| Dependency Management | Maven                                    |
| Language              | Java 21                                  |
| Database              | MySQL                                    |
| ORM / DB Access       | JDBC 
|      |                                  |
|  Tools            |     IntelliJ IDEA                    |
| Architecture Pattern  | Layered  |


######Here are the **valid login credentials** extracted from your database:

 Admin Login
  ID                Password 
 ADMIN001           admin123 

Lecturer Logins
 ID                               Password 

 LEC001                         lec123   
 LEC002                         lec456   
 LEC003                         lec789   
 LEC004                         lec101   
 LEC005                         lec202   


 The datadase is 

 -- Create the database
CREATE DATABASE IF NOT EXISTS edutrack_system;
USE edutrack_system;

-- 1. Users table (Admin & Lecturer login)
CREATE TABLE users (
    user_id VARCHAR(10) PRIMARY KEY,           -- A001 = Admin ID, L001 = Lecturer ID
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Lecturer') NOT NULL
);

-- 2. Courses table
CREATE TABLE courses (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

-- 3. Students table
CREATE TABLE students (
    student_id VARCHAR(10) PRIMARY KEY,
    student_name VARCHAR(100),
    contact_details VARCHAR(100),
    course_id VARCHAR(10),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- 4. Lecturers table
CREATE TABLE lecturers (
    lecturer_id VARCHAR(10) PRIMARY KEY,  -- Should match user_id in users table for lecturers
    name VARCHAR(100),
    contact VARCHAR(100),
    subject VARCHAR(100)
);

-- 5. Class schedule table
CREATE TABLE class_schedule (
    schedule_id VARCHAR(10) PRIMARY KEY,
    course_id VARCHAR(10),
    subject VARCHAR(100),
    date DATE,
    lecturer_id VARCHAR(10),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(lecturer_id)
);

-- 6. Attendance table
CREATE TABLE attendance (
    attendance_id VARCHAR(10) PRIMARY KEY,
    student_id VARCHAR(10),
    schedule_id VARCHAR(10),
    date DATE,
    status ENUM('Present', 'Absent'),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (schedule_id) REFERENCES class_schedule(schedule_id)
);

-- Sample data
-- Admin and lecturer users
INSERT INTO users (user_id, username, password, role) VALUES
('A001', 'admin', 'adminpass', 'Admin'),
('L001', 'lec1', 'lecpass1', 'Lecturer'),
('L002', 'lec2', 'lecpass2', 'Lecturer');

-- Courses
INSERT INTO courses (course_id, course_name) VALUES
('C001', 'Software Engineering'),
('C002', 'Data Science');

-- Students
INSERT INTO students (student_id, student_name, contact_details, course_id) VALUES
('S001', 'Supuni Chethana', '0771234567', 'C001'),
('S002', 'Nimal Perera', '0779876543', 'C002');

-- Lecturers
INSERT INTO lecturers (lecturer_id, name, contact, subject) VALUES
('L001', 'Kamal Silva', '0712345678', 'OOP'),
('L002', 'Nadeeka Fernando', '0773456789', 'Data Structures');

-- Class schedule
INSERT INTO class_schedule (schedule_id, course_id, subject, date, lecturer_id) VALUES
('SCH001', 'C001', 'OOP', '2025-08-01', 'L001'),
('SCH002', 'C002', 'Data Structures', '2025-08-02', 'L002');

-- Attendance
INSERT INTO attendance (attendance_id, student_id, schedule_id, date, status) VALUES
('A001', 'S001', 'SCH001', '2025-08-01', 'Present'),
('A002', 'S002', 'SCH002', '2025-08-02', 'Absent');




