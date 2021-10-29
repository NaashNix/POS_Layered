package dto;

import java.sql.Date;
import java.util.ArrayList;

public class OrderDTO {
    private String orderID;
    private Date orderDate;
    private String customerID;
    private ArrayList<OrderDetailsDTO> items = new ArrayList<>();

    public OrderDTO(String orderID, Date orderDate, String customerID, ArrayList<OrderDetailsDTO> items) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.items = items;
    }

    public OrderDTO(String orderID, java.util.Date orderDate, String customerID) {
        this.orderID = orderID;
        this.orderDate = (Date) orderDate;
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public ArrayList<OrderDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetailsDTO> items) {
        this.items = items;
    }
}
