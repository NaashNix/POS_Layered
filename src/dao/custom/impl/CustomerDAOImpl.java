package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import db.DbConnection;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)",
                customer.getCustID(),
                customer.getCustTitle(),
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode());
    }

    @Override
    public boolean delete(String s) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Customer customer) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Customer WHERE custID=?", s);
        if (resultSet.next()){
            return new Customer(
                    resultSet.getString("custID"),
                    resultSet.getString("custTitle"),
                    resultSet.getString("custName"),
                    resultSet.getString("custAddress"),
                    resultSet.getString("city"),
                    resultSet.getString("province"),
                    resultSet.getString("postalCode")
            );
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return null;
    }

    @Override
    public String getLastCustomer() throws SQLException, ClassNotFoundException {
        int lastIDint;
        ResultSet resultSet = CrudUtil.executeQuery("SELECT*FROM Customer ORDER BY CustID DESC LIMIT 1");

        if (resultSet.next()){
            lastIDint = (Integer.parseInt(resultSet.getString(1).substring(2)));
            return String.format("C-%03d",++lastIDint);
        }else{
            return "C-001";
        }
    }

    @Override
    public List<String> getAllIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT custID FROM Customer");
        List<String> allCustomerIDs = new ArrayList<>();
        while (resultSet.next()){
            allCustomerIDs.add(resultSet.getString(1));
        }
        return allCustomerIDs;
    }
}
