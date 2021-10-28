package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SystemAdminDashboardFormController implements Initializable {

    public AnchorPane AdminDashPlayGround;
    public Label lblDashboardTime;
    public Label lblUserName;
    public LocalTime currentTime;
    public JFXComboBox<String> cmbViewSelecter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemDashboard();
        cmbViewSelecter.setValue("ADD ITEM FORM");
        // lblUserName.setText(loginFormController.setUserName);
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            currentTime = LocalTime.now();
            lblDashboardTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        cmbViewSelecter.getItems().addAll("ADD ITEM FORM","INCOME REPORTS");
        cmbViewSelecter.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals("ADD ITEM FORM")){
                        loadItemDashboard();
                    }else if (newValue.equals("INCOME REPORTS")){
                        System.out.println("Income");
                    }
                });

    }

    private void loadItemDashboard() {
        URL resource = getClass().getResource("../view/AdminMenuItems.fxml");
        Parent load = null;
        try {
            if (resource != null) {
                load = FXMLLoader.load(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminDashPlayGround.getChildren().clear();
        AdminDashPlayGround.getChildren().add(load);
    }
}
