package dto;

public class OrderDetailsDTO {
    private String itemID;
    private int orderQty;
    private double discount;
    private double unitPrice;

    public OrderDetailsDTO(String itemID, int orderQty, double discount, double unitPrice) {
        this.itemID = itemID;
        this.orderQty = orderQty;
        this.discount = discount;
        this.unitPrice = unitPrice;
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
