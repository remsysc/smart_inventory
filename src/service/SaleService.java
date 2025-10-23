package service;

import entities.DoublyLinkedList;
import entities.Product;
import entities.Sale;
import java.time.LocalDateTime;

public class SaleService {

    private final DoublyLinkedList sales;
    private final InventoryService inventoryService;
    private int saleCounter;

    public SaleService(InventoryService inventoryService) {
        this.sales = new DoublyLinkedList();
        this.inventoryService = inventoryService;
        this.saleCounter = 1;
    }

    public void recordSale(String productId, int quantity) {
        Product product = inventoryService.findProductById(productId);

        if (product == null) {
            throw new IllegalArgumentException(
                "Product not found: " + productId
            );
        }

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException(
                String.format(
                    "Insufficient stock. Available: %d, Requested: %d",
                    product.getQuantity(),
                    quantity
                )
            );
        }
        //create a sale record
        String date = LocalDateTime.now().toString();
        Sale sale = new Sale(
            productId,
            product.getName(),
            quantity,
            product.getPrice(),
            date
        );
        product.setQuantity(product.getQuantity() - quantity); //updates inventory
        sales.add(sale); //record sale

        System.out.println("✓ Sale recorded: " + sale);
    }

    public void displayAllSales() {
        if (sales.isEmpty()) {
            System.out.println("No sales recorded.");
            return;
        }

        System.out.println("\n=== SALES TRANSACTIONS ===");
        for (int i = 0; i < sales.size(); i++) {
            Sale s = (Sale) sales.get(i); // ← EXPLICIT CAST
            System.out.println(s);
        }
    }

    // for ReportService access
    DoublyLinkedList getAllSales() {
        return sales;
    }
}
