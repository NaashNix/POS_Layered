package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import entity.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

public class EditItemDetailsFormController {
    @FXML
    public Image imageRestore;
    @FXML
    public ImageView restoreImageView;
    @FXML
    public Label lblItemID;
    @FXML
    public TextField txtItemDesc;
    @FXML
    public TextField txtQtyOnHand;
    @FXML
    public TextField txtUnitPrice;
    @FXML
    public JFXButton btnClear;
    @FXML
    public TextField txtPackSize;
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    // Initialize method.
    public void initialize() throws SQLException, ClassNotFoundException {
            setDataToFields();
    }

    // Set searched item data to fields.
    private void setDataToFields() throws SQLException, ClassNotFoundException {
        Item item = itemBO.searchItem(AdminMenuItemController.selectedItemID);
        lblItemID.setText(item.getItemID());
        txtItemDesc.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        txtPackSize.setText(item.getPackSize());
    }

    @FXML   // Restore mouse enter button icon color.
    public void restoreMouseEN(MouseEvent mouseEvent) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/view/assets/images/undo.png"));
        restoreImageView.setImage(image);

    }

    @FXML   // Restore icon mouse Exit
    public void restoreMouseEX(MouseEvent mouseEvent) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/view/assets/images/undoNormal.png"));
        restoreImageView.setImage(image);
    }



    @FXML   // Cancel button action.
    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }


    @FXML   // update Item details method.
    public void btnItemEditDone(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(itemBO.updateItem(new Item(
                lblItemID.getText(),
                txtItemDesc.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        ))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Done!");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);
            if (button == ButtonType.OK) {
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }

    }

    @FXML   // Restore item details to the previous.
    public void restorePrevious(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        setDataToFields();
    }
}
