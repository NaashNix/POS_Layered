package controller;


import bo.BoFactory;
import bo.custom.ItemBO;
import bo.custom.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dto.CartItemDTO;
import dto.CustomerPlaceOrderDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlaceOrderFormController{
    public Rectangle addCustomerSQ;
    public ImageView imgAddCustomer;
    public static Stage stage;
    private final EventHandler<MouseEvent> handler = MouseEvent::consume;
    public Label txtCustomerName;
    public Label txtCustomerTitle;
    public Label txtCustomerAddress;
    public Label txtCustomerPostalCode;
    public JFXComboBox<String> cmbItemSelector;
    public JFXComboBox<String> cmbCustomerIDs;
    public Label txtQtyOnHand;
    public Label txtUnitPrice;
    public JFXButton btnClearForm;
    public JFXButton btnAddToCart;
    public TextField txtReqAmount;
    public TextField txtDiscountAmount;
    public TextField txtItemDesc;
    public Label txtCaptionQtyOnHand;
    public TableView<CartItemDTO> tblCart;
    public TableColumn<CartItemDTO,String> colItemCode;
    public TableColumn<CartItemDTO,Integer> colQty;
    public TableColumn<CartItemDTO,Double> colPrice;
    public Label txtGrandTotal;
    private final DecimalFormat df=new DecimalFormat("#.##");
    public JFXButton btnPay;
    public Label txtItemTotal;
    public Label lblDiscountedAmount;
    public Label lblSubTotal;
    public Label lblOrderID;
    public int tableSelectedRow = -1;
    public Label txtCustomerNameLabel;
    public Label txtOrderIDLabel;
    public JFXButton btnRemoveRow;
    public AnchorPane placeOrderContext;
    public Rectangle SQBackToLogin;
    public ImageView imgBackToLogin;
    public Rectangle SQManageOrders;
    private final PlaceOrderBO placeOrderBO = (PlaceOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PLACE_ORDER);
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    private final ObservableList<CartItemDTO> cartQueue = FXCollections.observableArrayList();    // Cart item temporary stored here.

    // Initialize Method.
    public void initialize() throws SQLException, ClassNotFoundException {
        setLblOrderID(); // Set order ID to the interface.

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode")); // * Table Values.
        colQty.setCellValueFactory(new PropertyValueFactory<>("reqAmount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        // When selecting table row, buttons will enable and assign table row number to the variable.
        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tableSelectedRow = newValue.intValue();
            if (tableSelectedRow == -1){
                btnRemoveRow.setDisable(true);
            }else{
                btnRemoveRow.setDisable(false);
            }
        });


        loadCustomerIDs(); // Load all Customer IDs.
        loadItemIDs(); // Load all Item IDs.

        cmbItemSelector.getSelectionModel().selectedItemProperty() // Listener for the Item selector combo Box.
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemData(newValue); // Calling Item Data Setter.
                    } catch (SQLException | ClassNotFoundException throwable) {
                        throwable.printStackTrace();
                    }
                });


        cmbCustomerIDs.getSelectionModel().selectedItemProperty()   // Listener for the customer IDs.
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData(newValue);  // Calling customer data setter.
                    } catch (SQLException | ClassNotFoundException throwable) {
                        throwable.printStackTrace();
                    }
                });

    }


    @FXML   // Open Add Customer Form
    public void openAddCustomerForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/AddCustomerForm.fxml")));
        Scene scene = new Scene(load);
        stage = new Stage();
        stage.setScene(scene);
        addCustomerSQ.addEventFilter(MouseEvent.ANY,handler);
        imgAddCustomer.addEventFilter(MouseEvent.ANY,handler);
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                addCustomerSQ.removeEventFilter(MouseEvent.ANY,handler);
                imgAddCustomer.removeEventFilter(MouseEvent.ANY,handler);
                try {
                    initialize();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                addCustomerSQ.removeEventFilter(MouseEvent.ANY,handler);
                imgAddCustomer.removeEventFilter(MouseEvent.ANY,handler);
                try {
                    initialize();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        stage.show();

    }
    // Setting orderID to the label.

    public void setLblOrderID() throws SQLException, ClassNotFoundException {
        lblOrderID.setText(placeOrderBO.getOrderID());
        //lblOrderID.setText("O-001");

    }

    private void setItemData(String itemID) throws SQLException, ClassNotFoundException {
        Item item = itemBO.searchItem(itemID);
        if (item == null){
            System.out.println("NULL --> Failed!");
            
        }else{

            for (CartItemDTO tempCartItem : cartQueue
            ) {
                if (tempCartItem.getItemCode().equals(itemID)){
                    item.setQtyOnHand(item.getQtyOnHand() - tempCartItem.getReqAmount());
                }
            }

            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            txtItemDesc.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        }


    }

    private void loadItemIDs() throws SQLException, ClassNotFoundException {
        if (placeOrderBO != null){
            List<String> itemIDs = placeOrderBO.getAllItemIDs();
            cmbItemSelector.getItems().addAll(itemIDs);
        }
    }

    private void setCustomerData(String customerID) throws SQLException, ClassNotFoundException {
        CustomerPlaceOrderDTO customer = placeOrderBO.searchCustomer(customerID);
        if (customer == null){
            System.out.println("CustomerDatasetNULL");
        }else{
            txtOrderIDLabel.setText(lblOrderID.getText()); // Set Order ID for the label.
            txtCustomerNameLabel.setText(customer.getCustomerName());
            txtCustomerName.setText(customer.getCustomerName());
            txtCustomerTitle.setText(customer.getCustomerTitle());
            txtCustomerAddress.setText(customer.getCustomerAddress());
            txtCustomerPostalCode.setText(customer.getCustomerPostalCode());
        }
    }

    // Load all customer IDs
    List<String> customerIDs = null;
    public void loadCustomerIDs() throws SQLException, ClassNotFoundException {
        cmbCustomerIDs.getItems().clear();
        /*if (!customerIDs.isEmpty()) {
            customerIDs.clear();
        }*/
        if (placeOrderBO!=null) {
            customerIDs = placeOrderBO.getAllCustomerIDs();
            cmbCustomerIDs.getItems().addAll(customerIDs);
        }
    }

    @FXML // Clear the item form.
    public void clearItemFormOnAction(ActionEvent actionEvent) {
        txtReqAmount.clear();
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
        txtDiscountAmount.clear();
        cmbItemSelector.getSelectionModel().clearSelection();
        txtItemDesc.setText(null);
        txtItemTotal.setText("0.00");
        btnAddToCart.setDisable(true);
    }

    @FXML   // Algorithm to enable the "Add To Cart" button.
    public void enableAddToCart(KeyEvent inputMethodEvent) throws NumberFormatException{
        if (!txtReqAmount.getText().matches("^[0-9]*$")){
            txtReqAmount.setStyle("-fx-border-color:red");
            btnAddToCart.setDisable(true);
            return;
        }else{
            txtReqAmount.setStyle("-fx-border-color:green");
            btnAddToCart.setDisable(true);
        }

        if (cmbItemSelector.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Select Item.").show();
            txtItemTotal.setText("0.00");
        }else {
            txtReqAmount.setDisable(false);
            if (txtReqAmount.getText().isEmpty()) {
                btnAddToCart.setDisable(true);
                txtItemTotal.setText("0.00");
            } else {
                if (Integer.parseInt(txtReqAmount.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
                    txtCaptionQtyOnHand.setTextFill(Color.rgb(194, 54, 22));
                    txtQtyOnHand.setTextFill(Color.rgb(194, 54, 22));
                    btnAddToCart.setDisable(true);
                } else {
                    double tot = Double.parseDouble(txtUnitPrice.getText())
                            * Integer.parseInt(txtReqAmount.getText());
                    if (txtDiscountAmount.getText().isEmpty()){
                        txtItemTotal.setText(placeOrderBO.moneyPatternValidator(tot));
                    }else{
                        txtItemTotal.setText(placeOrderBO.moneyPatternValidator(tot - (tot / 100 *
                                Double.parseDouble(txtDiscountAmount.getText()))));
                    }
                    btnAddToCart.setDisable(false);
                    txtCaptionQtyOnHand.setTextFill(Color.rgb(97, 96, 96));
                    txtQtyOnHand.setTextFill(Color.rgb(0, 0, 0));
                }
            }
        }
    }

    @FXML //BackButton Mouse enter
    public void BackButtonMouseEN(MouseEvent mouseEvent) {
        SQBackToLogin.setFill(Color.rgb(99, 110, 114,1));
    }

    @FXML //Back button mouse exit.
    public void BackButtonMouseEX(MouseEvent mouseEvent) {
        SQBackToLogin.setFill(Color.rgb(127, 140, 141,1));
    }

    @FXML //Manage Order mouse enter.
    public void manageOrdersMouseEN(MouseEvent mouseEvent) {
        SQManageOrders.setFill(Color.rgb(99, 110, 114,1));
    }

    @FXML // manage order mouse exit.
    public void manageOrdersMouseEX(MouseEvent mouseEvent) {
        SQManageOrders.setFill(Color.rgb(127, 140, 141,1));
    }

    @FXML // Clear all existing items in the cart.
    public void clearCart(ActionEvent actionEvent) {
        clearItemFormOnAction(actionEvent);
        MouseEvent mouseEvent = null;
        clearCustomer();
        txtOrderIDLabel.setText("Select Customer");
        tblCart.refresh();
    }

    // Clear all customer data in the place order form.
    public void clearCustomer(){
        cmbCustomerIDs.getSelectionModel().clearSelection();
        txtCustomerPostalCode.setText(null);
        txtCustomerAddress.setText(null);
        txtCustomerTitle.setText(null);
        txtCustomerName.setText(null);
        txtCustomerNameLabel.setText("AYUBOWAN!");
    }

    @FXML // Open Manage Orders Form.
    public void openManageOrders(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/ManageOrdersForm.fxml")));
        placeOrderContext.getChildren().clear();
        placeOrderContext.getStylesheets().clear();
        placeOrderContext.getChildren().add(load);
    }

    @FXML   // Log out button on mouse clicked.
    public void logoutMouseClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) placeOrderContext.getScene().getWindow();
        window.setScene(new Scene(load));
        window.show();
        window.centerOnScreen();
    }

    @FXML   // Add Customer icon Mouse enter.
    public void addCustomerMouseEN(MouseEvent mouseEvent) {
        addCustomerSQ.setFill(Color.rgb(99, 110, 114,1));
    }

    @FXML   // Add customer icon mouse exit.
    public void addCustomerMouseEX(MouseEvent mouseEvent) {
        addCustomerSQ.setFill(Color.rgb(127, 140, 141,1));
    }

    @FXML   // Add To Cart button on action.
    public void btn_addToCart_onAction(ActionEvent actionEvent) {

        String itemCode = cmbItemSelector.getValue();
        String itemDesc = txtItemDesc.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int reqAmount = Integer.parseInt(txtReqAmount.getText());
        double discount = txtDiscountAmount.getText().isEmpty()? 0.00 : Double.parseDouble(df.format(Double.parseDouble(txtDiscountAmount.getText())));
        double total = placeOrderBO.calItemTotal((unitPrice*reqAmount),discount);

        // Making ordered item dto for future verification.
        CartItemDTO orderedItem = new CartItemDTO(
                itemCode,
                itemDesc,
                unitPrice,
                reqAmount,
                total,
                discount
        );

        int cartQueueRowNumber = isExist(orderedItem);

        if (cartQueueRowNumber == -1){
            cartQueue.add(orderedItem);
            System.out.println("PlaceOrderFormController.btn_addToCart_onAction");
        }else {
            CartItemDTO existingItem = cartQueue.get(cartQueueRowNumber);

            // Update the details of existing item with the new item.
            orderedItem.setReqAmount(existingItem.getReqAmount() + reqAmount);
            orderedItem.setTotal(existingItem.getTotal() + total);

            cartQueue.remove(cartQueueRowNumber);
            cartQueue.add(cartQueueRowNumber, orderedItem);
        }


        clearItemFormOnAction(actionEvent);
        tblCart.refresh();
        tblCart.setItems(cartQueue);
        calculateGrandTotal();  // Set current total of the all items to the label.

        // Disable or enable "PAY" button.
        btnPay.setDisable(tblCart.getItems().isEmpty());

        // Subtract ordered item quantity from the it's quantity.


    }


    @FXML   // Enable txtRequiredAmount when item is selected.
    public void cmbItemSelectorOnAction(ActionEvent actionEvent) {
        if (!cmbItemSelector.getSelectionModel().isEmpty()){
            txtReqAmount.setEditable(true);
            txtDiscountAmount.setEditable(true);
        }
    }

    //  Check whether the selected item is already in the list.
    private int isExist(CartItemDTO itemToCheck){
        for (int i= 0; i < cartQueue.size(); i++){
            if (itemToCheck.getItemCode().equals(cartQueue.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }

    private void calculateGrandTotal(){
        double total = 0.00;
        double  totalDiscountedAmount = 0.0;
        for (CartItemDTO tempItemInTheCart : cartQueue){
            total+=tempItemInTheCart.getTotal();
            totalDiscountedAmount = totalDiscountedAmount+
                    (tempItemInTheCart.getUnitPrice()*tempItemInTheCart.getReqAmount())
                            /100*tempItemInTheCart.getDiscount();
        }

        lblDiscountedAmount.setText("- "+placeOrderBO.moneyPatternValidator(totalDiscountedAmount));
        txtGrandTotal.setText(placeOrderBO.moneyPatternValidator(total));
        lblSubTotal.setText(placeOrderBO.moneyPatternValidator(total+totalDiscountedAmount));
    }

    @FXML   // "PAY" button action. This will place the order.
    public void btn_payOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetailsDTO> cartItemList = new ArrayList<>();

        for (CartItemDTO tempCartItem : cartQueue
        ) {

            cartItemList.add(new OrderDetailsDTO(
                    tempCartItem.getItemCode(),
                    tempCartItem.getReqAmount(),
                    tempCartItem.getDiscount(),
                    tempCartItem.getUnitPrice()
            ));
        }


        OrderDTO order = new OrderDTO(
                lblOrderID.getText(),
                java.sql.Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                cmbCustomerIDs.getValue(),
                cartItemList
        );

        if (placeOrderBO.placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Done").show();
            setLblOrderID();
            clearCart(actionEvent);
            btnPay.setDisable(true);
            cartQueue.clear();
            tblCart.refresh();
            txtGrandTotal.setText("0.00");
            lblSubTotal.setText("0.00");
            lblDiscountedAmount.setText("0.00");
        }else{
            new Alert(Alert.AlertType.WARNING,"Failed!").show();
            System.out.println("Failed --> "+lblOrderID.getText());
        }

    }

    @FXML   // Remove selected item from the order.
    public void removeSelectedItemFromOrder(ActionEvent actionEvent) {
        cartQueue.remove(tableSelectedRow);
        calculateGrandTotal();
        tblCart.refresh();
        cmbItemSelector.getSelectionModel().clearSelection();
        //MouseEvent mouseEvent = null;
        //(mouseEvent);
    }

    @FXML   // action for the customer id combo box.
    public void customerIDSelectorOnAction(ActionEvent actionEvent) {
        if (cmbCustomerIDs.getSelectionModel().isEmpty()){
            cmbItemSelector.setDisable(true);
            cmbItemSelector.getSelectionModel().clearSelection();

        }else{
            cmbItemSelector.setDisable(false);
        }
    }
}
