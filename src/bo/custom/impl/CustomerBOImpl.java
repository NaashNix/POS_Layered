package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import entity.Customer;

import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return customerDAO.add(customer);
    }

    @Override
    public String getLastCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.getLastCustomer();
    }

    @Override
    public Customer searchCustomer(String custID) throws SQLException, ClassNotFoundException {
        return customerDAO.search(custID);
    }
}
