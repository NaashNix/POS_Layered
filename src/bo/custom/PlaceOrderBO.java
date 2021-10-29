package bo.custom;

import bo.SuperBO;
import dto.CartItemDTO;
import dto.CustomerPlaceOrderDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    String getOrderID() throws SQLException,ClassNotFoundException;

    List<String> getAllItemIDs() throws SQLException,ClassNotFoundException;

    List<String> getAllCustomerIDs() throws SQLException,ClassNotFoundException;

    Item searchItem(String itemID) throws SQLException,ClassNotFoundException;

    CustomerPlaceOrderDTO searchCustomer(String custID) throws SQLException,ClassNotFoundException;

    double calItemTotal(double itemTotal,double discount);

    String moneyPatternValidator(double value);

    boolean placeOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;



}
