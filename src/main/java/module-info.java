module com.sportsmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sportsmanager to javafx.fxml;
    opens com.sportsmanager.framework to javafx.base;
    opens com.sportsmanager.football to javafx.base;
    opens com.sportsmanager.volleyball to javafx.base;

    exports com.sportsmanager;
}