package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a completed sales transaction in the system.
 *
 * A Sale entity captures a snapshot of a transaction including product details,
 * pricing information, quantity sold, and transaction timestamp. Each sale is assigned
 * a unique identifier (UUID) upon creation to ensure traceability and uniqueness.
 *
 * The sale stores denormalized data (product name, unit price) to preserve the
 * exact state at the time of sale, even if the product details change later in the
 * inventory. This follows the principle of transaction immutability for audit purposes

 */
public class Sale {

    /**
     * Unique identifier for this sale transaction.
     * Automatically generated using UUID to ensure global uniqueness.
     */
    private final UUID id;

    /**
     * The identifier of the product that was sold.
     * References the product in the inventory system.
     */
    private final String productId;

    /**
     * The name of the product at the time of sale.
     * This is a denormalized snapshot that preserves historical accuracy
     * even if the product name changes in inventory.
     */
    private String productName;

    /**
     * The number of units sold in this transaction.
     * Must be a positive integer. Changes to this value should trigger
     * recalculation of the total amount.
     */
    private int quantity;

    /**
     * The price per unit at the time of sale.
     * This captures the historical price and may differ from the current
     * product price in inventory. Should be non-negative.
     */
    private double unitPrice;

    /**
     * The total amount for this sale (unitPrice × quantity).
     * This is a calculated field that should be kept in sync with
     * unitPrice and quantity.
     */
    private double totalAmount;

    //The date and time when this sale was recorded.
    private LocalDateTime date;

    /**
     * Constructs a new Sale transaction with the specified details.
     *
     * A unique UUID is automatically generated for this sale. The total amount
     * is calculated as the product of unit price and quantity. All parameters
     * represent the state at the time of the transaction.

     * @param productId the unique identifier of the product being sold
     * @param productName the name of the product at the time of sale
     * @param quantity the number of units sold
     * @param unitPrice the price per unit at the time of sale
     * @param date the timestamp of the sale as a string
     */
    public Sale(
        String productId,
        String productName,
        int quantity,
        double unitPrice,
        LocalDateTime date
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
        // Automatically recalculate total when quantity changes
        this.totalAmount = this.unitPrice * quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        // Automatically recalculate total when unit price changes
        this.totalAmount = unitPrice * this.quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns a string representation of this sale transaction.
     *
     * The string includes the product name, unit price (formatted to 2 decimal
     * places), quantity, and total amount (formatted to 2 decimal places).
     * The sale ID and date are not included in this representation.</p>
     *
     * @return a formatted string describing this sale
     */
    @Override
    public String toString() {
        return String.format(
            "Sale[ID=%s, Product=%s, Unit Price=%.2f, Quantity=%d, Total=%.2f, Date=%s]",
            id,
            productName,
            unitPrice,
            quantity,
            totalAmount,
            date
        );
    }
}
