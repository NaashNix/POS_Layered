package bo.custom.impl;


import bo.custom.PlaceOrderBO;
import dao.CrudUtil;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DbConnection;
import dto.CartItemDTO;
import dto.CustomerPlaceOrderDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Pattern mainMoneyPattern = Pattern.compile("^[1-9][0-9]*$");
    private final Pattern singleDecimal = Pattern.compile("^[1-9][0-9]*([.][0-9]{1})?$");

    @Override
    public String getOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderID();
        //return "O-005";
    }

    @Override
    public List<String> getAllItemIDs() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIDs();
    }

    @Override
    public List<String> getAllCustomerIDs() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIDs();
    }

    @Override
    public Item searchItem(String itemID) throws SQLException, ClassNotFoundException {
        return itemDAO.search(itemID);
    }

    @Override
    public CustomerPlaceOrderDTO searchCustomer(String custID) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(custID);
        if (customer!=null){
            return new CustomerPlaceOrderDTO(
                    customer.getCustID(),
                    customer.getCustName(),
                    customer.getCustTitle(),
                    customer.getCustAddress(),
                    customer.getPostalCode()
            );
        }else {
            return null;
        }

    }

    @Override
    public double calItemTotal(double itemTotal, double discount) {
        return  Double.parseDouble(df.format(itemTotal-(itemTotal/100)*discount));
    }

    @Override
    public String moneyPatternValidator(double value) {
        //double accountBalance = new SavingsAccountController().getAccountBalance(txtSearchWithdraw.getText());

        String refactoredValue = null;
        if (mainMoneyPattern.matcher(String.valueOf(value)).matches()){
            refactoredValue = value+".00";
        }else if (singleDecimal.matcher(String.valueOf(value)).matches()){
            refactoredValue = value+"0";
        }else{
            refactoredValue = String.valueOf(value);
        }
        return refactoredValue;
    }

    @Override
    public boolean placeOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        // get the connection.
        Connection connection = null;

        connection = DbConnection.getInstance().getConnection();

        if (orderDAO.orderExists(dto.getOrderID())) {
            return false;   // If orderID already exists, return false.
        }

        connection.setAutoCommit(false);    // offing auto commit.

        boolean orderAdded = orderDAO.add(new Order(dto.getOrderID(),dto.getOrderDate(),dto.getCustomerID()));

        if (!orderAdded) {
            // If the order doesn't save.
            connection.rollback();
            connection.setAutoCommit(true);
            return false;

        }

        // Save order details.
        for (OrderDetailsDTO tempItem : dto.getItems()) {
            OrderDetails orderDetails = new OrderDetails(
                    dto.getOrderID(),
                    tempItem.getItemID(),
                    tempItem.getOrderQty(),
                    tempItem.getDiscount(),
                    tempItem.getUnitPrice());

            boolean orderDetailsAdded = orderDetailsDAO.add(orderDetails);

            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            Item item = itemDAO.search(orderDetails.getItemID());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetails.getOrderQty());
            boolean update = itemDAO.update(item);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        //if every thing ok
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }


}
