package dto;

public class UpdateOrderDTO {
    private String orderID;
    private String ItemID;
    private int qtyOnHand;
    private int requestedAmount;
    private double discount;


    public UpdateOrderDTO(String orderID, String itemID, int qtyOnHand, int requestedAmount, double discount) {
        this.orderID = orderID;
        ItemID = itemID;
        this.qtyOnHand = qtyOnHand;
        this.requestedAmount = requestedAmount;
        this.discount = discount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
