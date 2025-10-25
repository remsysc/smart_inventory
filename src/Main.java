import entities.DoublyLinkedList;
import entities.DoublyLinkedList;
import entities.Product;
import entities.Product;
import java.util.Scanner;
import java.util.Scanner;
import service.inventory.InventoryService;
import service.inventory.InventoryService;
import service.inventory.InventoryServiceImpl;
import service.inventory.InventoryServiceImpl;
import service.report.ReportService;
import service.report.ReportService;
import service.report.ReportServiceImpl;
import service.report.ReportServiceImpl;
import service.sale.SaleService;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;
import service.sale.SaleServiceImpl;

/**
 * Main entry point for the Smart Inventory Management System application.
 *
 * This class provides a console-based user interface for managing inventory,
 * recording sales transactions, and generating business reports. It implements
 * a menu-driven navigation system that allows users to interact with the system
 * through text commands.
 *
 * This class serves as the presentation layer,
 * coordinating user interactions with the following service layers:
 *
 *   InventoryService - Product and inventory management
 *   SaleService - Sales transaction recording and history
 *   ReportService - Analytics and business intelligence
 *
 *
 * Features:
 *
 *   Product management (add, view, update, delete, search)
 *   Sales recording and transaction history
 *   Business reports (revenue, top products, stock alerts)
 *   Bidirectional product listing (forward/backward)
 *   Input validation with error handling
 *   Demo data population for testing

 */
public class Main {

    /**
     * Shared Scanner instance for reading user input from the console.
     *
     * Declared as static final to ensure a single Scanner instance is used
     * throughout the application lifecycle, preventing resource leaks and
     * ensuring consistent input handling.
     */
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        smartInventoryApp();
    }

    /**
     * Initializes and runs the main application loop.
     *
     * This method performs the following operations
     *
     *   Instantiates all required service layer components
     *   Establishes dependency relationships between services
     *   Populates the system with demonstration data
     *   Enters the main menu loop for user interaction
     *   Handles graceful shutdown when user exits

     * Services are manually instantiated
     * and wired together.
     */
    public static void smartInventoryApp() {
        // Initialize service layer components
        InventoryService inventoryService = new InventoryServiceImpl();
        SaleService saleService = new SaleServiceImpl(inventoryService);
        ReportService reportService = new ReportServiceImpl(
            inventoryService,
            saleService
        );

        // Populate with demo data for immediate usability
        fillDummyData(inventoryService, saleService);

        // Main application loop
        while (true) {
            String choice = readString(
                """

                ╔══════════════════════════════════════╗
                ║  Smart Inventory Management System   ║
                ╚══════════════════════════════════════╝
                1. Products
                2. Sales
                3. Reports
                4. Exit
                """
            );

            switch (choice) {
                case "1" -> productMenu(inventoryService);
                case "2" -> saleMenu(saleService);
                case "3" -> reportMenu(reportService);
                case "4" -> {
                    System.out.println("\n✓ Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.err.println(
                    "✗ Invalid choice. Please select 1-4."
                );
            }
        }
    }

    /**
     * Displays and handles the Reports submenu.
     *
     * This menu provides access to various business intelligence and analytics
     * features including inventory statistics, sales performance analysis, and
     * stock status monitoring.
     *
     * Available Reports:
     *
     *   Total product count in inventory
     *   Best-selling product identification
     *   Out-of-stock product list
     *   Total sales revenue calculation
     *
     *
     * @param reportService the report service instance for generating analytics
     */
    public static void reportMenu(ReportService reportService) {
        while (true) {
            String choice = readString(
                """

                ═══ Reports Menu ═══
                1. Total Number of Products
                2. Highest Product Sales
                3. Out of Stock Products
                4. Total Sales Revenue
                5. Back to Main Menu
                """
            );

            switch (choice) {
                case "1" -> {
                    int totalProducts =
                        reportService.getTotalNumberOfProducts();
                    System.out.println(
                        "\nTotal Number of Products: " + totalProducts
                    );
                }
                case "2" -> {
                    System.out.println();
                    reportService.displayProductWithHighestSales();
                }
                case "3" -> {
                    DoublyLinkedList outOfStockProducts =
                        reportService.getOutOfStockProducts();
                    if (outOfStockProducts.isEmpty()) {
                        System.out.println("\nNo out of stock products.");
                    } else {
                        System.out.println("\nOut of Stock Products:");
                        for (int i = 0; i < outOfStockProducts.size(); i++) {
                            Product product = (Product) outOfStockProducts.get(
                                i
                            );
                            System.out.println("  " + product);
                        }
                    }
                }
                case "4" -> {
                    double totalRevenue = reportService.getTotalSalesRevenue();
                    System.out.printf(
                        "\nTotal Sales Revenue: $%.2f\n",
                        totalRevenue
                    );
                }
                case "5" -> {
                    return;
                }
                default -> System.err.println(
                    "✗ Invalid choice. Please select 1-5."
                );
            }
        }
    }

    /**
     * Displays and handles the Sales submenu.
     *
     * This menu provides functionality for recording new sales transactions
     * and viewing sales history. Sales are automatically validated against
     * inventory availability before being recorded.
     *
     * Features:
     *
     *   Record new sales with automatic inventory updates
     *   View complete sales transaction history
     *
     * @param saleService the sales service instance for transaction management
     */
    public static void saleMenu(SaleService saleService) {
        while (true) {
            String choice = readString(
                """

                ═══ Sales Menu ═══
                1. Record Sale
                2. View Sales
                3. Back to Main Menu
                """
            );

            switch (choice) {
                case "1" -> {
                    String productId = readString("Enter Product ID: ");
                    int quantity = readInt("Enter quantity to sell: ");
                    try {
                        saleService.recordSale(productId, quantity);
                        System.out.println("✓ Sale recorded successfully.");
                    } catch (Exception e) {
                        System.err.println("✗ Error: " + e.getMessage());
                    }
                }
                case "2" -> {
                    System.out.println();
                    saleService.displayAllSales();
                }
                case "3" -> {
                    return;
                }
                default -> System.err.println(
                    "✗ Invalid choice. Please select 1-3."
                );
            }
        }
    }

    /**
     * Displays and handles the Product Management submenu.
     *
     * This menu provides comprehensive CRUD (Create, Read, Update, Delete)
     * operations for managing the product inventory. It also includes search
     * functionality and bidirectional listing capabilities.
     *
     * Features:
     *
     *   Add new products to inventory
     *   View products in ascending or descending order
     *   Update existing product details
     *   Remove products from inventory
     *   Search for specific products by ID
     *
     * @param inventoryService the inventory service instance for product management
     */
    public static void productMenu(InventoryService inventoryService) {
        while (true) {
            String choice = readString(
                """

                ═══ Product Menu ═══
                1. Add Product
                2. View Products
                3. Update Product Details
                4. Delete Product
                5. Search Product by ID
                6. Back to Main Menu
                """
            );

            switch (choice) {
                case "1" -> {
                    String id = readString("Enter Product ID: ");
                    String name = readString("Enter Product Name: ");
                    double price = readDouble("Enter Product Price: ");
                    int quantity = readInt("Enter quantity: ");
                    try {
                        inventoryService.addProduct(
                            new Product(id, name, price, quantity)
                        );
                        System.out.println("✓ Product added successfully.");
                    } catch (Exception e) {
                        System.err.println("✗ Error: " + e.getMessage());
                    }
                }
                case "2" -> {
                    String input = readString(
                        """
                        Select Display Order:
                        1. Ascending (Forward)
                        2. Descending (Backward)
                        """
                    );
                    if (input.equals("1")) {
                        System.out.println();
                        inventoryService.displayProductsForward();
                    } else if (input.equals("2")) {
                        System.out.println();
                        inventoryService.displayProductsBackward();
                    } else {
                        System.err.println("✗ Invalid choice.");
                    }
                }
                case "3" -> {
                    String id = readString("Enter Product ID to update: ");
                    String name = readString("Enter new Product Name: ");
                    double price = readDouble("Enter new Product Price: ");
                    int quantity = readInt("Enter new quantity: ");
                    try {
                        inventoryService.updateProduct(
                            id,
                            name,
                            price,
                            quantity
                        );
                        System.out.println("✓ Product updated successfully.");
                    } catch (Exception e) {
                        System.err.println("✗ Error: " + e.getMessage());
                    }
                }
                case "4" -> {
                    String id = readString("Enter Product ID to delete: ");
                    try {
                        inventoryService.deleteProduct(id);
                        System.out.println("✓ Product deleted successfully.");
                    } catch (Exception e) {
                        System.err.println("✗ Error: " + e.getMessage());
                    }
                }
                case "5" -> {
                    String id = readString("Enter Product ID to search: ");
                    Product product = inventoryService.findProductById(id);
                    if (product != null) {
                        System.out.println("\n" + product);
                    } else {
                        System.err.println("✗ Product not found.");
                    }
                }
                case "6" -> {
                    return;
                }
                default -> System.err.println(
                    "✗ Invalid choice. Please select 1-6."
                );
            }
        }
    }

    /**
     * Populates the system with demonstration data for testing and evaluation.

     * After populating data, this method displays the initial inventory
     * and sales history to provide immediate visual feedback.
     *
     * @param inventoryService the inventory service to populate with products
     * @param saleService the sales service to populate with transactions
     */
    public static void fillDummyData(
        InventoryService inventoryService,
        SaleService saleService
    ) {
        try {
            System.out.println("\nLoading demonstration data...\n");

            // Add sample products with realistic data
            inventoryService.addProduct(
                new Product("P001", "Laptop", 999.99, 10)
            );
            inventoryService.addProduct(
                new Product("P002", "Mouse", 29.99, 50)
            );
            inventoryService.addProduct(
                new Product("P003", "Keyboard", 79.99, 30)
            );
            inventoryService.addProduct(
                new Product("P004", "Monitor", 299.99, 0)
            );
            inventoryService.addProduct(
                new Product("P005", "Webcam", 89.99, 15)
            );

            // Record sample sales transactions
            saleService.recordSale("P001", 3); // Sell 3 laptops
            saleService.recordSale("P002", 10); // Sell 10 mice
            saleService.recordSale("P001", 2); // Sell 2 more laptops
            saleService.recordSale("P003", 5); // Sell 5 keyboards
            saleService.recordSale("P005", 15); // Sell all webcams (creates out-of-stock)

            // Display initial state
            System.out.println("\n═══ Current Inventory ═══");
            inventoryService.displayProductsForward();

            System.out.println("\n═══ Sales History ═══");
            saleService.displayAllSales();

            System.out.println("\n✓ Demonstration data loaded successfully.");
        } catch (Exception e) {
            System.err.println("✗ Error loading demo data: " + e.getMessage());
        }
    }

    /**
     * Displays a prompt and reads a line of text input from the user.
     *
     * This is a utility method that centralizes input reading logic,
     * providing consistent prompt formatting throughout the application.</p>
     *
     * @param prompt the message to display to the user
     * @return the user's input as a trimmed string
     */
    public static String readString(String prompt) {
        System.out.println(prompt);
        System.out.print(">> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays a prompt and reads an integer input from the user with validation.
     *
     * This method repeatedly prompts the user until a valid integer is entered.
     * Invalid inputs (non-numeric strings) display an error message and re-prompt
     * the user, preventing application crashes from malformed input.
     *
     * @param prompt the message to display to the user
     * @return a valid integer entered by the user
     */
    public static int readInt(String prompt) {
        while (true) {
            try {
                String input = readString(prompt);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println(
                    "✗ Invalid input. Please enter a valid integer."
                );
            }
        }
    }

    /**
     * Displays a prompt and reads a double input from the user with validation.
     *
     * This method repeatedly prompts the user until a valid decimal number
     * is entered. Invalid inputs (non-numeric strings) display an error message
     * and re-prompt the user, ensuring type safety for price and numeric inputs.
     *
     * @param prompt the message to display to the user
     * @return a valid double value entered by the user
     */
    public static double readDouble(String prompt) {
        while (true) {
            try {
                String input = readString(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.err.println(
                    "✗ Invalid input. Please enter a valid number."
                );
            }
        }
    }
}
