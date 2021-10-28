package dto;

public class CustomerPlaceOrderDTO {
    private String customerID;
    private String customerName;
    private String customerTitle;
    private String customerAddress;
    private String customerPostalCode;

    public CustomerPlaceOrderDTO() {
    }

    public CustomerPlaceOrderDTO(String customerID, String customerName, String customerTitle, String customerAddress, String customerPostalCode) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerTitle = customerTitle;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }
}
