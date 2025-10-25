package service.sale;

import java.time.LocalDateTime;

/**
 * Implementation of the SaleService interface that manages sales transactions.
 *
 * This service handles the complete sales workflow including:
 *  Validating product availability and stock levels
 *  Recording sale transactions with timestamp
 *  Automatically updating inventory quantities
 *  Maintaining a transaction history
 *
 *
 * This implementation uses a DoublyLinkedList to store sales history,
 * providing efficient append operations for new transactions. It coordinates with
 * the InventoryService to ensure data consistency between sales and inventory.
 *

 */
public class SaleServiceImpl implements SaleService {

    /**
     * Internal storage for all recorded sales transactions.
     * Sales are stored in chronological order (oldest to newest).
     */
    private final DoublyLinkedList sales;

    /**
     * Reference to the inventory service for product lookup and stock updates.
     * This dependency enables coordination between sales and inventory management.
     */
    private final InventoryService inventoryService;

    /**
     * Constructs a new SaleServiceImpl with the specified inventory service.
     *
     * The constructor initializes an empty sales history and establishes
     * the dependency on the inventory service for product operations.
     *
     * @param inventoryService the inventory service to use for product lookups
     *                        and stock updates
     * @throws NullPointerException if inventoryService is null
     */
    public SaleServiceImpl(InventoryService inventoryService) {
        if (inventoryService == null) {
            throw new NullPointerException("InventoryService cannot be null");
        }
        this.sales = new DoublyLinkedList();
        this.inventoryService = inventoryService;
    }

    /**
     * Records a sale transaction for the specified product and quantity.
     *
     * This method performs the following operations atomically:
     *
     * Validates that the product exists in inventory
     * Verifies sufficient stock is available
     * Creates a sale record with current timestamp
     * Updates the product inventory quantity
     * Stores the sale transaction in history
     * Outputs a confirmation message
     *
     * @param productId the unique identifier of the product being sold
     * @param quantity the number of units being sold
     * @throws IllegalArgumentException if the product is not found,
     *         if quantity is negative or zero, or if there is insufficient stock
     * @throws NullPointerException if productId is null
     */
    @Override
    public void recordSale(String productId, int quantity) {
        // Retrieve product from inventory
        Product product = inventoryService.findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException(
                "Product not found: " + productId
            );
        }

        // Validate sufficient stock
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException(
                String.format(
                    "Insufficient stock. Available: %d, Requested: %d",
                    product.getQuantity(),
                    quantity
                )
            );
        }

        // Create sale record with current timestamp
        String date = LocalDateTime.now().toString();
        Sale sale = new Sale(
            productId,
            product.getName(),
            quantity,
            product.getPrice(),
            date
        );

        // Update inventory quantity
        product.setQuantity(product.getQuantity() - quantity);

        // Record the sale transaction
        sales.add(sale);

        // Provide user feedback
        System.out.println("✓ Sale recorded: " + sale);
    }

    /**
     * Displays all recorded sales transactions to the console.
     *
     * This method iterates through the sales history and prints each
     * transaction using its toString() representation. If no sales
     * have been recorded, a message indicating this is displayed instead
     */
    @Override
    public void displayAllSales() {
        if (sales.isEmpty()) {
            System.out.println("No sales recorded.");
            return;
        }

        System.out.println("\n=== SALES TRANSACTIONS ===");
        for (int i = 0; i < sales.size(); i++) {
            // Explicit cast required due to DoublyLinkedList storing Object type
            Sale s = (Sale) sales.get(i);
            System.out.println(s);
        }
    }

    /**
     * Returns the complete list of all sales transactions.
     *
     * This method provides a access to the copy of  sales storage,
     * primarily intended for use by reporting services or other components
     * that need to analyze sales data.

     * @return the copy of DoublyLinkedList containing all Sale objects,
     *         or an empty list if no sales have been recorded
     */
    public DoublyLinkedList getAllSales() {
        // Return unmodifiable view
        DoublyLinkedList copy = new DoublyLinkedList();
        for (int i = 0; i < sales.size(); i++) {
            copy.add(sales.get(i));
        }
        return copy;
    }
}
