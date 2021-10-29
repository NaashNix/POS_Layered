package controller;

import bo.BoFactory;
import bo.custom.LoginBO;
import javafx.event.ActionEvent;
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
import java.sql.SQLException;

public class loginFormController {

    public TextField txtLoginUserName;
    public PasswordField txtLoginPassword;
    public AnchorPane loginWindowContext;
    public static String setUserName;
    private final LoginBO loginBO = (LoginBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.LOGIN);

    public void openRelatedDashboard(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        String id = txtLoginUserName.getText();
        setUserName = txtLoginUserName.getText();
        String theUserRoll = loginBO.getTheUserRoll(id);

            switch (theUserRoll) {
                case "admin": {
                    adminLoginChecker(id,actionEvent);
                    break;
                }

                case "cashi": {
                    cashierLoginChecker(id,actionEvent);
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
        }

    private void managerLoginChecker() {
        throw new UnsupportedOperationException("Not Supported yet");
    }

    private void cashierLoginChecker(String userID,ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (txtLoginPassword.getText().equals(loginBO.getPasswordByID(userID))){
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

    private void adminLoginChecker(String userID,ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if(txtLoginPassword.getText().equals(loginBO.getPasswordByID(userID))){
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
