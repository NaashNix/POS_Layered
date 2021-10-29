package bo.custom;


import bo.SuperBO;
import dto.CartItemDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {

    ArrayList<String> getAllCustomerIDs() throws SQLException,ClassNotFoundException;

    String getCustomerName(String customerID) throws SQLException,ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrders(String customerID) throws SQLException,ClassNotFoundException;

    ArrayList<CartItemDTO> getAllCartInTheOrder(String orderID) throws SQLException,ClassNotFoundException;

    String moneyPatternValidator(double value)throws SQLException,ClassNotFoundException;

    boolean deleteOrder(String orderID) throws SQLException,ClassNotFoundException;

    boolean deleteItemInTheOrder(String orderID,String itemID) throws SQLException,ClassNotFoundException;



}
