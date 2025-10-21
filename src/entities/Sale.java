package entities;

public class Sale {
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private String date;

    public Sale(String productName, int quantity, double price, double totalAmount, String date) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        this.date = date;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String toString(){
        return String.format("Sale[product=%s, qty=%d, total=%.2f]",productName,quantity,totalAmount);
    }

}
