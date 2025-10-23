package entities;

import java.util.UUID;

public class Sale {

    private final UUID id;
    private final String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String date;

    public Sale(
        String productId,
        String productName,
        int quantity,
        double unitPrice,
        String date
    ) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = unitPrice * quantity;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getProductId() {
        return productId;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public String toString() {
        return String.format(
            "Sale[Product=%s, Unit Price=%.2f, Quantity=%d, Total=%.2f]",
            productName,
            unitPrice,
            quantity,
            totalAmount
        );
    }
}
