package controller;

import bo.BoFactory;
import bo.custom.ManageOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dto.CartItemDTO;
import dto.ItemTableDTO;
import dto.ManageOrderDTO;
import dto.OrderDTO;
import entity.Item;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManageOrdersFormController {

    public VBox vBoxOrderButtons;
    public JFXComboBox<String> cmbCustomerID;
    public Label txtCustomerName;
    public Label txtOrderID;
    public JFXButton previousButton = null;
    public AnchorPane manageOrdersContext;
    public TableView<CartItemDTO> tblItemDetails;
    public TableColumn<CartItemDTO,String> colItemID;
    public TableColumn<CartItemDTO,String> colDesc;
    public TableColumn<CartItemDTO,Integer> colQty;
    public TableColumn<CartItemDTO,Double> colUnitPrice;
    public TableColumn<CartItemDTO,Double> colTotal;
    public TableColumn<CartItemDTO,Double> colDiscount;
    public static int selectedRow = -1;
    public Label txtGrandTotal;
    public Label txtDiscountedAmount;
    private final DecimalFormat df=new DecimalFormat("#.##");
    public JFXButton btnRemove;
    public JFXButton btnEdit;
    public Rectangle SQBackToLogin;
    public ImageView imgBackToLogin;
    public JFXButton btnDeleteOrder;
    public static String selectedItemID;
    public static String orderID;
    public static ItemTableDTO itemDetailsMNG;
    public JFXButton btnDone;
    public OrderDTO previousOrder;
    private final ManageOrderBO manageOrderBO = (ManageOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGE_ORDERS);
    private final ObservableList<CartItemDTO> cartItems = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {

        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadCustomerID();
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        clearBoard();
                        txtCustomerName.setText(manageOrderBO.getCustomerName(newValue));
                        getAllOrdersRelatedToSelectedCustomer(newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        tblItemDetails.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedRow = newValue.intValue();
                    if (selectedRow != -1){
                        btnEdit.setDisable(false);
                        btnRemove.setDisable(false);
                    }else{
                        btnRemove.setDisable(true);
                        btnEdit.setDisable(true);
                    }
                }
        );

    }



    private void clearBoard() {
        txtOrderID.setText(null);
        txtGrandTotal.setText(null);
        txtOrderID.setText(null);
        txtDiscountedAmount.setText(null);
        cartItems.clear();
        tblItemDetails.refresh();

    }

    private JFXButton createOrderNumberButton(OrderDTO order){
        JFXButton button = new JFXButton(order.getOrderID());
        button.setStyle("-fx-background-color : #bdc3c7;");
        button.setPrefSize(102.0,24.0);
        button.setOnAction(event -> {
            try {
                if (previousButton == null){
                }else{
                    previousButton.setStyle("-fx-background-color : #bdc3c7;");
                }

                button.setStyle("-fx-background-color : #2980b9;");
                btnDeleteOrder.setDisable(false);
                setDataToBoard(order);
                previousOrder = new OrderDTO(
                        txtOrderID.getText(),
                        order.getOrderDate(),
                        order.getCustomerID(),
                        order.getItems()
                );
                previousButton = button;
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        return button;
    }

    private void setDataToBoard(OrderDTO order) throws SQLException, ClassNotFoundException {

        txtOrderID.setText(order.getOrderID());
        setDataToTable(order.getOrderID());
        calculateGrandTotal();

    }

    private void calculateGrandTotal() {
        double total = 0.00;
        double discount = 0.00;
        for (CartItemDTO tempItem : cartItems
             ) {
            total+=tempItem.getTotal();
            discount+=tempItem.getTotal()/100*tempItem.getDiscount();
        }
        try {
            txtDiscountedAmount.setText(manageOrderBO.moneyPatternValidator(discount));
            txtGrandTotal.setText(manageOrderBO.moneyPatternValidator(total));
        }catch (NullPointerException | SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    // When selecting a orderID(button), this method will fire and set the related order details to the table.
    private void setDataToTable(String orderID) throws SQLException, ClassNotFoundException {
        ArrayList<CartItemDTO> orderItemDetails = manageOrderBO.getAllCartInTheOrder(orderID);

        cartItems.clear();

        for (CartItemDTO tempOrderItem : orderItemDetails
             ) {
            cartItems.add(new CartItemDTO(
                    tempOrderItem.getItemCode(),
                    tempOrderItem.getItemDesc(),
                    tempOrderItem.getUnitPrice(),
                    tempOrderItem.getReqAmount(),

                    Double.parseDouble(df.format(
                            (tempOrderItem.getReqAmount()*tempOrderItem.getUnitPrice())-(tempOrderItem.getReqAmount()*
                            tempOrderItem.getUnitPrice())/100*tempOrderItem.getDiscount()
                            )),
                    tempOrderItem.getDiscount()

            ));
        }

        tblItemDetails.setItems(cartItems);
    }

    private void getAllOrdersRelatedToSelectedCustomer(String customerID) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orderList = manageOrderBO.getAllOrders(customerID);
        vBoxOrderButtons.getChildren().clear();
        for (OrderDTO order : orderList
             ) {
            vBoxOrderButtons.getChildren().add(createOrderNumberButton(order));
        }
    }

    //  Load all the customer IDs.
    private void loadCustomerID() throws SQLException, ClassNotFoundException {
        List<String> customerIDs = manageOrderBO.getAllCustomerIDs();
        cmbCustomerID.getItems().addAll(customerIDs);
    }


    @FXML   // Action when clicked the back button.
    public void backButtonMouseClicked(MouseEvent mouseEvent) throws IOException {
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Order will be restore to previous!");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.OK);
        if (button == ButtonType.OK) {

            if (previousButton!=null) {
                if (manageOrderBO.deleteOrder(previousOrder.getOrderID())) {
                    new OrderController().placeOrder(preiousOrder);
                }
            }*/

            manageOrdersContext.getChildren().clear();
            manageOrdersContext.getStylesheets().clear();
            Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/PlaceOrderForm.fxml")));
            manageOrdersContext.getChildren().add(load);
    }

    @FXML   //Delete item in the order.
    public void removeItemFromOrder(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (selectedRow == -1){
            new Alert(Alert.AlertType.WARNING,"ERROR --> selectedRow(-1)").show();
        }else{
            if (manageOrderBO.deleteItemInTheOrder(txtOrderID.getText(),cartItems.get(selectedRow).getItemCode())){
                cartItems.remove(selectedRow);
                tblItemDetails.refresh();
                if (cartItems.isEmpty()){
                    new Alert(Alert.AlertType.WARNING,"Empty Order!").show();
                    btnDone.setDisable(cartItems.isEmpty());
                }
                calculateGrandTotal();
            }else{
                System.out.println("ERROR-->orderRemove.deleteFailed(false)");
            }

        }
    }



    /*public OrderDTO getLoadedOrder(String orderIDAfterEdited) throws SQLException, ClassNotFoundException {
        return new OrderController().searchOrder(orderIDAfterEdited);
        return manageOrderBO.searchOrder(orderIDAfterEdited);
    }*/

    public void BackButtonMouseEN(MouseEvent mouseEvent) {
        SQBackToLogin.setFill(Color.rgb(99, 110, 114,1));
    }

    public void BackButtonMouseEX(MouseEvent mouseEvent) {
        SQBackToLogin.setFill(Color.rgb(127, 140, 141,1));
    }

    public void deleteEntireOrder(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete the order?");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.OK);
        if (button == ButtonType.OK) {
            if (manageOrderBO.deleteOrder(txtOrderID.getText())) {
                Alert alt = new Alert(Alert.AlertType.INFORMATION, "Deleted!");
                Optional<ButtonType> rst = alt.showAndWait();
                clearBoard();
            } else {
                System.out.println("Failed!");
            }
        }
    }

    public void exit(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        clearBoard();
        initialize();
    }

    public void enableDoneButton(KeyEvent keyEvent) {

    }

    public void CustomerEditDone(ActionEvent actionEvent) {

    }

    public void cancel(ActionEvent actionEvent) {

    }

    public void orderEditConfirm(MouseEvent mouseEvent) {

    }
}
