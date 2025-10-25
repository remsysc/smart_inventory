package service.report;

import entities.DoublyLinkedList;
import entities.Product;
import entities.Sale;
import service.inventory.InventoryService;
import service.sale.SaleService;

/**
 * Implementation of the ReportService interface that provides
 * business intelligence and analytical reporting capabilities.
 *
 * This service aggregates data from inventory and sales services to generate
 * comprehensive reports including:
 *
 *   Total product counts and inventory statistics
 *   Sales revenue calculations and financial summaries
 *   Top-performing product identification by sales volume
 *   Out-of-stock product tracking and alerts
 *
 *
 * The service acts as a facade over multiple data sources, providing a unified
 * interface for reporting and analytics. It performs data aggregation, correlation,
 * and calculation without modifying the underlying data.
 *
 */
public class ReportServiceImpl implements ReportService {

    /**
     * Reference to the inventory service for accessing product data.
     * Used to retrieve product information, check stock levels, and count inventory.
     */
    private final InventoryService inventoryService;

    /**
     * Reference to the sales service for accessing transaction data.
     * Used to retrieve sales history, calculate revenue, and analyze sales patterns.
     */
    private final SaleService saleService;

    /**
     * Constructs a new ReportServiceImpl with the specified dependencies.
     *
     * This constructor establishes the necessary connections to inventory
     * and sales services, enabling the report service to aggregate data from
     * multiple sources.
     *
     * @param inventoryService the inventory service for product data access
     *
     * @param saleService the sales service for transaction data access
     *
     * @throws NullPointerException if either service parameter is null
     */
    public ReportServiceImpl(
        InventoryService inventoryService,
        SaleService saleService
    ) {
        if (inventoryService == null) {
            throw new NullPointerException("InventoryService cannot be null");
        }
        if (saleService == null) {
            throw new NullPointerException("SaleService cannot be null");
        }
        this.inventoryService = inventoryService;
        this.saleService = saleService;
    }

    /**
     * Returns the total number of products in the inventory.
     *
     * This count includes all products.
     * @return the total count of products in inventory, or 0 if inventory is empty
     */
    @Override
    public int getTotalNumberOfProducts() {
        return inventoryService.getAllProducts().size();
    }

    /**
     * Calculates and returns the total sales revenue from all transactions.
     *
     * <p>This method iterates through all recorded sales and sums the total
     * amounts.
     * @return the sum of all sale amounts, or 0.0 if no sales have been recorded
     */
    @Override
    public double getTotalSalesRevenue() {
        DoublyLinkedList sales = saleService.getAllSales();
        double totalRevenue = 0;

        for (int i = 0; i < sales.size(); i++) {
            Sale sale = (Sale) sales.get(i);
            totalRevenue += sale.getTotalAmount();
        }

        return totalRevenue;
    }

    /**
     * Identifies and displays the product with the highest total sales revenue.
     *
     * This method performs the following analysis:
     *
     *   Iterates through all products in inventory
     *   For each product, calculates total sales revenue from all transactions
     *   Tracks the quantity sold for each product
     *   Identifies the product with maximum sales revenue
     *   Displays comprehensive information about the top product
     *
     *
     * The displayed information includes product ID, name, current unit price,
     * total sales revenue, and total quantity sold across all transactions.
     */
    @Override
    public void displayProductWithHighestSales() {
        DoublyLinkedList products = inventoryService.getAllProducts();
        DoublyLinkedList sales = saleService.getAllSales();

        // Validate data availability
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        if (sales.isEmpty()) {
            System.out.println("No sales available.");
            return;
        }

        // Track the top-performing product
        String topProductId = null;
        String topProductName = null;
        double highestSales = 0;
        int totalQuantitySold = 0;

        // Iterate through each product and calculate its total sales
        for (int i = 0; i < products.size(); i++) {
            Product product = (Product) products.get(i);
            double productTotalSales = 0;
            int productQuantitySold = 0;

            // Sum sales for this specific product
            for (int j = 0; j < sales.size(); j++) {
                Sale sale = (Sale) sales.get(j);
                if (sale.getProductId().equals(product.getId())) {
                    productTotalSales += sale.getTotalAmount();
                    productQuantitySold += sale.getQuantity();
                }
            }

            // Update if this product has higher sales than current top
            if (productTotalSales > highestSales) {
                highestSales = productTotalSales;
                topProductId = product.getId();
                topProductName = product.getName();
                totalQuantitySold = productQuantitySold;
            }
        }

        // Display the results
        if (topProductId != null) {
            System.out.println("\n=== Product with Highest Sales ===");
            System.out.println("ID: " + topProductId);
            System.out.println("Name: " + topProductName);
            System.out.println(
                "Current Unit Price: $" +
                    String.format(
                        "%.2f",
                        inventoryService
                            .findProductById(topProductId)
                            .getPrice()
                    )
            );
            System.out.println(
                "Total Sales Revenue: $" + String.format("%.2f", highestSales)
            );
            System.out.println("Total Quantity Sold: " + totalQuantitySold);
        } else {
            System.out.println("Unable to determine top product.");
        }
    }

    /**
     * Returns a list of all products that are currently out of stock.
     *
     * This method filters the complete product inventory and identifies
     * products with zero quantity.

     * @return a DoublyLinkedList containing all out-of-stock products,
     *         or an empty list if all products are in stock or inventory is empty
     */
    @Override
    public DoublyLinkedList getOutOfStockProducts() {
        DoublyLinkedList products = inventoryService.getAllProducts();
        DoublyLinkedList outOfStock = new DoublyLinkedList();

        for (int i = 0; i < products.size(); i++) {
            Product product = (Product) products.get(i);
            if (product.isOutOfStock()) {
                outOfStock.add(product);
            }
        }

        return outOfStock;
    }
}
