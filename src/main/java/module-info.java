module com.sportsmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sportsmanager to javafx.fxml;
    opens com.sportsmanager.football to javafx.base;

    exports com.sportsmanager;
}