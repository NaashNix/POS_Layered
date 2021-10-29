package bo.custom.impl;

import bo.custom.ManageOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DbConnection;
import dto.CartItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import dto.UpdateOrderDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ManageOrderBOImpl implements ManageOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Pattern mainMoneyPattern = Pattern.compile("^[1-9][0-9]*$");
    private final Pattern singleDecimal = Pattern.compile("^[1-9][0-9]*([.][0-9]{1})?$");

    @Override
    public ArrayList<String> getAllCustomerIDs() throws SQLException, ClassNotFoundException {
        List<String> allIDs = customerDAO.getAllIDs();
        return new ArrayList<>(allIDs);
    }

    @Override
    public String getCustomerName(String customerID) throws SQLException, ClassNotFoundException {
        Customer result = customerDAO.search(customerID);
        return result.getCustName();
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders(String customerID) throws SQLException, ClassNotFoundException {
        ArrayList<Order> result = orderDAO.getAllOrdersRelatedToOneCustomer(customerID);
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        for (Order tempOrder: result
             ) {
            allOrders.add(new OrderDTO(tempOrder.getOrderID(),tempOrder.getOrderDate(),tempOrder.getCustomerID()));
        }

        return allOrders;
    }

    @Override
    public ArrayList<CartItemDTO> getAllCartInTheOrder(String orderID) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrderDetailsFromOrderID = orderDetailsDAO.getAllOrderDetailsFromOrderID(orderID);
        ArrayList<CartItemDTO> result = new ArrayList<>();

        for (OrderDetails tempOrderDetail: allOrderDetailsFromOrderID
             ) {
            String itemDescription = itemDAO.getItemDescription(tempOrderDetail.getItemID());
            result.add(new CartItemDTO(tempOrderDetail.getItemID(),
                    itemDescription, tempOrderDetail.getUnitPrice(),
                    tempOrderDetail.getOrderQty(), tempOrderDetail.getDiscount()));
        }
        return result;
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
    public boolean deleteOrder(String orderID) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        if (orderDAO.orderExists(orderID)){
            if (orderDAO.delete(orderID)){
                if (orderDetailsDAO.delete(orderID)){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }else{
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }else{
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
    }

    @Override
    public boolean deleteItemInTheOrder(String orderID, String itemID) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.deleteItemInTheOrder(orderID,itemID);
    }

    @Override
    public String getItemName(String itemID) throws SQLException, ClassNotFoundException {
        return itemDAO.getItemName(itemID);
    }

    @Override
    public int getItemQuantityOnHand(String itemID) throws SQLException, ClassNotFoundException {
        return itemDAO.getItemQuantityOnHand(itemID);
    }

    @Override
    public boolean updateOrderedItem(UpdateOrderDTO dto) throws SQLException,ClassNotFoundException{
        // Get the connection.
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        if (itemDAO.updateItemQuantity(dto.getItemID(), dto.getQtyOnHand())){
            OrderDetails orderDetails = new OrderDetails(dto.getOrderID(), dto.getItemID(), dto.getRequestedAmount()
            ,dto.getDiscount());
            if (orderDetailsDAO.updateOrderDetail(orderDetails)){
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }
        }

        connection.rollback();
        connection.setAutoCommit(true);
        return false;

    }

}

