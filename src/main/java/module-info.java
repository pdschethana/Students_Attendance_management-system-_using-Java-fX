module com.ijse.javaprojectfx {
    requires javafx.controls;
    requires javafx.fxml;
requires java.sql;

    opens com.ijse.javaprojectfx.controller to javafx.fxml;
    exports com.ijse.javaprojectfx;
}