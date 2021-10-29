package dto;

public class ManageOrderDTO {
    private String itemID;
    private String description;
    private int orderQty;
    private double unitPrice;
    private double discount;
    private double total;

    public ManageOrderDTO(String itemID, String description, int orderQty, double unitPrice, double discount, double total) {
        this.itemID = itemID;
        this.description = description;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
