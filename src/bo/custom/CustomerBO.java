package bo.custom;

import bo.SuperBO;
import entity.Customer;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(Customer customer) throws SQLException,ClassNotFoundException;

    String getLastCustomerID() throws SQLException,ClassNotFoundException;

    Customer searchCustomer(String custID) throws SQLException,ClassNotFoundException;
}
