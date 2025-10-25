package entities;

/**
 * Represents a product in an inventory management system.
 * This class encapsulates the core attributes of a product including
 * its unique identifier, name, price, and available quantity. It provides
 * methods for accessing and modifying these attributes, as well as checking
 * stock availability.


 */
public class Product {

    //The unique identifier for this product.
    private String id;

    //The display name of the product.

    private String name;

    /**
     * The price of the product in the system's default currency.
     * Must be non-negative.
     */
    private double price;

    /**
     * The available quantity of the product in stock.
     * Must be non-negative, where 0 indicates out of stock.
     */
    private int quantity;

    /**
     * Constructs a new Product with the specified attributes.

     * @param id the unique identifier for the product
     * @param name the display name of the product
     * @param price the price of the product
     * @param quantity the available quantity in stock
     */
    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Checks whether this product is out of stock.
     * @return true if the quantity is 0, false otherwise
     */
    public boolean isOutOfStock() {
        return quantity == 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of this product.
     *
     * The string representation includes all product attributes formatted
     * for readability. The price is formatted to two decimal places.</p>
     *
     * @return a formatted string containing the product's attributes
     */
    @Override
    public String toString() {
        return String.format(
            "Product[ID=%s, Name=%s, Price=%.2f, Quantity=%d]",
            id,
            name,
            price,
            quantity
        );
    }
}
