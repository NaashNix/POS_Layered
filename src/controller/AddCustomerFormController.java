package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCustomerCity;
    public Label lblCustomerID;
    public TextField txtPostalCode;
    public ComboBox<String> cmbCrustTitle;
    public ComboBox<String> cmbCustProvince;
    public JFXButton btnAddCustomer;
    public JFXButton btnClear;
    public AnchorPane AddCustomerContext;
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    @FXML
    public void addCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (validation()) {

            boolean customerSaved = customerBO.saveCustomer(new Customer(
                    lblCustomerID.getText(),
                    cmbCrustTitle.getValue(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    txtCustomerCity.getText(),
                    cmbCustProvince.getValue(),
                    txtPostalCode.getText()
            ));

            if (customerSaved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Done!");
                Optional<ButtonType> result = alert.showAndWait();
                ButtonType button = result.orElse(ButtonType.CANCEL);
                if (button == ButtonType.OK) {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.hide();
                } else {
                    clearForm(actionEvent);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed!").show();
            }
            lblCustomerID.setText(customerBO.getLastCustomerID());
        }
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        // Clear the addCustomer Form.
        cmbCustProvince.getSelectionModel().clearSelection();
        cmbCrustTitle.getSelectionModel().clearSelection();
        txtCustomerCity.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtPostalCode.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            lblCustomerID.setText(customerBO.getLastCustomerID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbCrustTitle.getItems().addAll("Top","Mid","New");
        cmbCustProvince.getItems().addAll(
                "Western Province",
                "Eastern Province",
                "Nothern Province",
                "South Province",
                "Uva Province",
                "Central Province",
                "Sabaragamu Province",
                "North Central Province",
                "North Westarn Province"
        );
    }

    public boolean validation(){
        // Validation part of the form
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String city = txtCustomerCity.getText();
        String postalCode = txtPostalCode.getText();

        if (cmbCrustTitle.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select Title").show();
            cmbCrustTitle.requestFocus();
            return false;
        }

        if (cmbCustProvince.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select Province").show();
            cmbCustProvince.requestFocus();
            return false;
        }

        if (!name.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return false;
        }

        if (!address.matches(".{3,}")){
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustomerAddress.requestFocus();
            return false;
        }

        if (!postalCode.matches("^[0-9]*$")){
            new Alert(Alert.AlertType.ERROR, "Invalid postal code.").show();
            txtPostalCode.requestFocus();
            return false;
        }

        if (!city.matches("[A-z]+")){
            new Alert(Alert.AlertType.ERROR, "Invalid city name.").show();
            txtCustomerCity.requestFocus();
            return false;
        }
        return true;
    }


}
