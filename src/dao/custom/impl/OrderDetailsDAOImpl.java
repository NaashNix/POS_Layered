package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import java.sql.ResultSet;
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
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAllOrderDetailsFromOrderID(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM orderDetail WHERE orderID=?", orderID);

        ArrayList<OrderDetails> result = new ArrayList<>();

        while (resultSet.next()){
            result.add(new OrderDetails(resultSet.getString("orderID"),
                    resultSet.getString("itemID"),resultSet.getInt("orderQty"),
                    resultSet.getDouble("discount"),resultSet.getDouble("unitPrice")));
        }

        return result;
    }

    @Override
    public boolean deleteItemInTheOrder(String orderID, String itemID) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM orderDetail WHERE orderID=? AND itemID=?",orderID,itemID);
    }

    @Override
    public boolean updateOrderDetail(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE orderDetail SET orderQty=?,discount=? WHERE orderID=? AND itemID=?",
                orderDetails.getOrderQty(), orderDetails.getDiscount(),orderDetails.getOrderId(),orderDetails.getItemID());
    }
}
