module com.si.proiect {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.si.proiect to javafx.fxml;
    exports com.si.proiect;
}