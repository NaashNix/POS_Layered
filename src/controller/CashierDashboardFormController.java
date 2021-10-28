package controller;


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

public class CashierDashboardFormController implements Initializable {
    
    public Label txtUserName;
    public Label dashboardTime;
    public LocalTime currentTime;
//    public String userName;
    public AnchorPane dashboardsContext;
    public Label dashboardWindowTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            currentTime = LocalTime.now();
            dashboardTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        try {
            loadCashierDashboard();
        } catch (IOException e) {
            String message = e.getMessage();
            e.printStackTrace();
        }

    }


    // Load Dashboard To The Play Ground.
    private void loadCashierDashboard() throws IOException {
        URL resource = getClass().getResource("../view/PlaceOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashboardsContext.getChildren().clear();
        dashboardsContext.getChildren().add(load);
    }
}
