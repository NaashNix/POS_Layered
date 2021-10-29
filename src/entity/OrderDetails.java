package entity;

public class OrderDetails {
    private String orderId;
    private String itemID;
    private int orderQty;
    private double discount;
    private double unitPrice;

    public OrderDetails(String orderId, String itemID, int orderQty, double discount, double unitPrice) {
        this.orderId = orderId;
        this.itemID = itemID;
        this.orderQty = orderQty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    public OrderDetails(String orderID, String itemID, int requestedAmount, double discount) {
        this.orderId = orderID;
        this.itemID = itemID;
        this.orderQty = requestedAmount;
        this.discount = discount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
