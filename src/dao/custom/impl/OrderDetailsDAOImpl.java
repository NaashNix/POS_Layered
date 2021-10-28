package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean add(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO orderDetail (orderID, itemID, orderQty, discount,unitPrice) VALUES (?,?,?,?,?)",
                orderDetails.getOrderId(),
                orderDetails.getItemID(),
                orderDetails.getOrderQty(),
                orderDetails.getDiscount(),
                orderDetails.getUnitPrice());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
