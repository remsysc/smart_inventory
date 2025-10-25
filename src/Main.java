import entities.DoublyLinkedList;
import entities.Product;
import java.util.Scanner;
import service.inventory.InventoryService;
import service.inventory.InventoryServiceImpl;
import service.report.ReportService;
import service.report.ReportServiceImpl;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    static void main(String[] args) {
        smartInventoryApp();
    }

    public static void smartInventoryApp() {
        InventoryService inventoryService = new InventoryServiceImpl();
        SaleService saleService = new SaleServiceImpl(inventoryService);
        ReportService reportService = new ReportServiceImpl(
            inventoryService,
            saleService
        );

        fillDummyData(inventoryService, saleService);

        while (true) {
            String choice = readString(
                """

                Smart Inventory Management System
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
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                }
            }
        }
    }

    public static void reportMenu(ReportService reportService) {
        while (true) {
            String choice = readString(
                """

                Reports Menu
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
                        "Total Number of Products: " + totalProducts
                    );
                }
                case "2" -> reportService.displayProductWithHighestSales();
                case "3" -> {
                    DoublyLinkedList outOfStockProducts =
                        reportService.getOutOfStockProducts();
                    if (outOfStockProducts.isEmpty()) {
                        System.out.println("No out of stock products.");
                    } else {
                        System.out.println("Out of Stock Products:");
                        for (int i = 0; i < outOfStockProducts.size(); i++) {
                            Product product = (Product) outOfStockProducts.get(
                                i
                            );
                            System.out.println(product);
                        }
                    }
                }
                case "4" -> {
                    double totalRevenue = reportService.getTotalSalesRevenue();
                    System.out.printf(
                        "Total Sales Revenue: %.2f\n",
                        totalRevenue
                    );
                }
                case "5" -> {
                    return;
                }
            }
        }
    }

    public static void saleMenu(SaleService saleService) {
        while (true) {
            String choice = readString(
                """

                Sales Menu
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
                        System.out.println("Sale recorded successfully.");
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case "2" -> saleService.displayAllSales();
                case "3" -> {
                    return;
                }
            }
        }
    }

    public static void productMenu(InventoryService inventoryService) {
        while (true) {
            String choice = readString(
                """

                Product Menu
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
                        System.out.println("Product added successfully.");
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case "2" -> {
                    String input = readString(
                        """
                        1. Ascending List
                        2. Descending List
                        """
                    );
                    if (input.equals("1")) {
                        inventoryService.displayProductsForward();
                    } else if (input.equals("2")) {
                        inventoryService.displayProductsBackward();
                    } else {
                        System.out.println("Invalid choice.");
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
                        System.out.println("Product updated successfully.");
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case "4" -> {
                    String id = readString("Enter Product ID to delete: ");
                    try {
                        inventoryService.deleteProduct(id);
                        System.out.println("Product deleted successfully.");
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case "5" -> {
                    String id = readString("Enter Product ID to search: ");
                    Product product = inventoryService.findProductById(id);
                    if (product != null) {
                        System.out.println(product);
                    } else {
                        System.out.println("Product not found.");
                    }
                }
                case "6" -> {
                    return;
                }
            }
        }
    }

    public static void fillDummyData(
        InventoryService inventoryService,
        SaleService saleService
    ) {
        try {
            // Add products
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

            // Record some saleService
            saleService.recordSale("P001", 3); // Sell 3 laptops
            saleService.recordSale("P002", 10); // Sell 10 mice
            saleService.recordSale("P001", 2); // Sell 2 more laptops
            saleService.recordSale("P003", 5); // Sell 5 keyboards
            saleService.recordSale("P005", 15); // Sell all webcams (out of stock)

            // Display current inventoryService
            inventoryService.displayProductsForward();

            // Display saleService
            saleService.displayAllSales();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static String readString(String prompt) {
        System.out.println(prompt);
        System.out.print(">> ");
        return scanner.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                String input = readString(prompt);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println(
                    "Invalid input. Please enter a valid integer."
                );
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                String input = readString(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.err.println(
                    "Invalid input. Please enter a valid number."
                );
            }
        }
    }
}
