package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String getOrderID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Orders ORDER BY orderID DESC LIMIT 1");
        int lastIDint;
        if (resultSet.next()){
             lastIDint = (Integer.parseInt(resultSet.getString(1).substring(2)));
            return String.format("O-%03d",++lastIDint);

        }else{
            return "O-001";
        }
    }

    @Override
    public boolean orderExists(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderID FROM `Orders` WHERE orderID=?", orderID);
        return rst.next();
    }


    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Orders (orderID, orderDate, custID) VALUES (?,?,?)",
                order.getOrderID(),
                order.getOrderDate(),
                order.getCustomerID());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Orders WHERE orderID=?", s);
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<Order> getAllOrdersRelatedToOneCustomer(String customerID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Orders WHERE custID=?", customerID);
        ArrayList<Order> relatedOrders = new ArrayList<>();
        while (resultSet.next()){
            relatedOrders.add(new Order(resultSet.getString("orderID"),resultSet.getDate("orderDate"),
                    resultSet.getString("custID")));
        }
        return relatedOrders;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
}
