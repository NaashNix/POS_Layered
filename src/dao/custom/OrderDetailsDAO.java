package dao.custom;

import dao.CrudDAO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails,String> {
    ArrayList<OrderDetails> getAllOrderDetailsFromOrderID(String orderID) throws SQLException,ClassNotFoundException;

    boolean deleteItemInTheOrder(String orderID,String itemID) throws SQLException,ClassNotFoundException;

    boolean updateOrderDetail(OrderDetails orderDetails) throws SQLException,ClassNotFoundException;
}
