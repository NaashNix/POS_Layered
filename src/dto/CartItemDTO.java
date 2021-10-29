package dto;

public class CartItemDTO {
    private String itemCode;
    private String itemDesc;
    private double unitPrice;
    private int reqAmount;
    private double total;
    private double discount;

    public CartItemDTO(){}

    public CartItemDTO(String itemCode, int reqAmount,double discount) {
        this.itemCode = itemCode;
        this.reqAmount = reqAmount;
        this.discount = discount;
    }

    public CartItemDTO(double unitPrice, int reqAmount, double discount){
        this.setUnitPrice(unitPrice);
        this.setReqAmount(reqAmount);
        this.setDiscount(discount);
    }

    public CartItemDTO(String itemCode, String itemDesc, double unitPrice, int reqAmount, double total, double discount) {
        this.setItemCode(itemCode);
        this.setItemDesc(itemDesc);
        this.setUnitPrice(unitPrice);
        this.setReqAmount(reqAmount);
        this.setTotal(total);
        this.setDiscount(discount);
    }

    public CartItemDTO(String itemID, String itemDescription, double unitPrice, int orderQty, double discount) {
        this.setItemCode(itemID);
        this.setItemDesc(itemDescription);
        this.setUnitPrice(unitPrice);
        this.setReqAmount(orderQty);
        this.setDiscount(discount);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public void setReqAmount(int reqAmount) {
        this.reqAmount = reqAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
