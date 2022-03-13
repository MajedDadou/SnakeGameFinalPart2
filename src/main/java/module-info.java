module com.example.snakegamefinalpart2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakegamefinalpart2 to javafx.fxml;
    exports com.example.snakegamefinalpart2;
}