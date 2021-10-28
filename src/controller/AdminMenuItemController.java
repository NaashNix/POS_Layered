package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminMenuItemController {

    public TableView<Item> tblItemDetails;
    public TextField txtItemCode;
    public TextField txtQtyOnHand;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public JFXButton btnAddItem;
    public TextField txtDesc;
    public TableColumn<Item,String> colCode;
    public TableColumn<Item,String> colDesc;
    public TableColumn<Item,Integer> colQtyOnHand;
    public TableColumn<Item,String> colPackSize;
    public TableColumn<Item,Double> colUnitPrice;
    public AnchorPane addItemContext;
    public JFXButton btnEdit;
    public JFXButton btnDelete;
    public int selectedRow = -1;
    public static String selectedItemID;
    private final ObservableList<Item> itemTMS = FXCollections.observableArrayList();
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);


    // Table, row selected number.

    @FXML
    public void tableRowSelectedOrNot(MouseEvent mouseEvent) {
        if (selectedRow == -1) {
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        } else {
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    // Add item to the database.
    @FXML
    public void addItemToDB(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                txtItemCode.getText(),
                txtDesc.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        if (itemBO.saveItem(item)){
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION,"Done!").show();
            loadItemDetails();
            clearAddItemForm(actionEvent);
            btnAddItem.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Error in saving item details.").show();
        }
    }

    // Load data to the table.
    public void loadItemDetails() throws SQLException, ClassNotFoundException {
        tblItemDetails.getItems().clear();
        ArrayList<Item> itemList = itemBO.getAllItems();
        itemTMS.clear();
        itemTMS.addAll(itemList);
        tblItemDetails.setItems(itemTMS);
    }

    // Clear add item form.
    public void clearAddItemForm(ActionEvent actionEvent) {
        txtDesc.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
    }


    // Back to the login window.
    public void BackToLoginWindow(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginForm.fxml");
        Parent load = null;
        if (resource != null) {
            load = FXMLLoader.load(resource);
        }
        Stage window = (Stage) addItemContext.getScene().getWindow();
        window.setScene(new Scene(Objects.requireNonNull(load)));
        window.show();
        window.centerOnScreen();
    }

    // Delete Item.
    public void deleteItem(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (selectedRow == -1){
            new Alert(Alert.AlertType.WARNING,"ERROR --> selectedRow(-1)").show();
        }else{
            if (itemBO.deleteItem(itemTMS.get(selectedRow).getItemID())){
                itemTMS.remove(selectedRow);
                tblItemDetails.refresh();
            }
        }
    }

    // Key event for the enable add item button.
    @FXML
    public void enableAddItem(KeyEvent keyEvent) {
        btnAddItem.setDisable((txtDesc.getText().isEmpty()) || (txtPackSize.getText().isEmpty())
                || (txtQtyOnHand.getText().isEmpty()) || (txtUnitPrice.getText().isEmpty()));
    }

    // Edit item details button on mouse clicked.
    @FXML
    public void editItemDetails(MouseEvent mouseEvent) throws IOException {
        System.out.println(selectedRow);
        EventHandler<MouseEvent> handler = MouseEvent::consume;
        selectedItemID = itemTMS.get(selectedRow).getItemID();
        Stage stage;
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/EditItemDetailsForm.fxml")));
        Scene scene = new Scene(load);
        stage = new Stage();
        stage.setScene(scene);
        btnEdit.setDisable(true);
        btnDelete.addEventFilter(MouseEvent.ANY,handler);
        btnEdit.addEventFilter(MouseEvent.ANY,handler);
        btnDelete.setDisable(true);
        addItemContext.setDisable(true);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                btnEdit.removeEventFilter(MouseEvent.ANY,handler);
                btnDelete.removeEventFilter(MouseEvent.ANY,handler);
                btnDelete.setDisable(false);
                btnEdit.setDisable(false);
                addItemContext.setDisable(false);
                initialize();
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                btnEdit.removeEventFilter(MouseEvent.ANY,handler);
                btnDelete.removeEventFilter(MouseEvent.ANY,handler);
                btnDelete.setDisable(false);
                btnEdit.setDisable(false);
                addItemContext.setDisable(false);
                initialize();

            }
        });
        stage.show();

    }
    public void initialize() {
        tblItemDetails.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedRow = newValue.intValue();
                });

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            txtItemCode.setText(itemBO.getLastItemID());
            loadItemDetails();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}



