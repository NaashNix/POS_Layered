package controller;

import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginFormController {

    public TextField txtLoginUserName;
    public PasswordField txtLoginPassword;
    public AnchorPane loginWindowContext;
    public static String setUserName;


    public void openRelatedDashboard(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        String id = txtLoginUserName.getText();
        setUserName = txtLoginUserName.getText();

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Users WHERE id=?");
        preparedStatement.setObject(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){

            switch (resultSet.getString("roll")) {
                case "admin": {
                    adminLoginChecker(resultSet,actionEvent);
                    break;
                }

                case "cashi": {
                    cashierLoginChecker(resultSet,actionEvent);
                    break;
                }

                case "mngr": {
                    managerLoginChecker();
                    break;
                }

                default: {
                    new Alert(Alert.AlertType.ERROR, "Invalid User roll");
                }
            }
            System.out.println("HEllo");
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
        }
    }

    private void managerLoginChecker() {
        System.out.println("manager");
    }

    private void cashierLoginChecker(ResultSet resultSet,ActionEvent actionEvent) throws IOException, SQLException {

        if (txtLoginPassword.getText().equals(resultSet.getString("password"))){
            Parent load = FXMLLoader.load(getClass().getResource("../view/CashierDashboardForm.fxml"));
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Co-operative Bank Management ");
            stage.show();
            Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Password").show();

        }
    }

    private void adminLoginChecker(ResultSet resultSet,ActionEvent actionEvent) throws SQLException, IOException {
        if(txtLoginPassword.getText().equals(resultSet.getString("password"))){
            Parent load = FXMLLoader.load(getClass().getResource("../view/SystemAdminDashboardFrom.fxml"));
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            String css = this.getClass().getResource("../view/assets/style/AdminDashboardStyle.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Co-operative Bank Management ");
            stage.show();
            Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Password").show();
        }
    }


}
