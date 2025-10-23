package service;

import entities.DoublyLinkedList;
import entities.Product;
import entities.Sale;

/**
 * Not a record: contains business logic
 */
public class ReportService {

    private final InventoryService inventoryService;
    private final SaleService saleService;

    public ReportService(
        InventoryService inventoryService,
        SaleService saleService
    ) {
        this.inventoryService = inventoryService;
        this.saleService = saleService;
    }

    public int getTotalNumberOfProducts() {
        return inventoryService.getAllProducts().size();
    }

    public double getTotalSalesRevenue() {
        DoublyLinkedList sales = saleService.getAllSales();
        double totalRevenue = 0;
        for (int i = 0; i < sales.size(); i++) {
            Sale sale = (Sale) sales.get(i);
            totalRevenue += sale.getTotalAmount();
        }
        return totalRevenue;
    }

    public void displayProductWithHighestSales() {
        DoublyLinkedList products = inventoryService.getAllProducts();
        DoublyLinkedList sales = saleService.getAllSales();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        String topProductId = null;
        String topProductName = null;
        double highestSales = 0;
        int totalQuantitySold = 0;

        for (int i = 0; i < products.size(); i++) {
            Product product = (Product) products.get(i);
            double productTotalSales = 0;
            int productQuantitySold = 0;

            for (int j = 0; j < sales.size(); j++) {
                Sale sale = (Sale) sales.get(j);
                if (sale.getProductId().equals(product.getId())) {
                    productTotalSales += sale.getTotalAmount();
                    productQuantitySold += sale.getQuantity();
                }
            }

            if (productTotalSales > highestSales) {
                highestSales = productTotalSales;
                topProductId = product.getId();
                topProductName = product.getName();
                totalQuantitySold = productQuantitySold;
            }
        }
        if (topProductId == null) {
            return;
        }

        System.out.println("Product with Highest Sales:");
        System.out.println("ID: " + topProductId);
        System.out.println("Name: " + topProductName);
        System.out.println("Total Sales: " + highestSales);
        System.out.println("Total Quantity Sold: " + totalQuantitySold);
    }

    public DoublyLinkedList getOutOfStockProducts() {
        DoublyLinkedList products = inventoryService.getAllProducts();
        DoublyLinkedList outOfStock = new DoublyLinkedList();

        for (int i = 0; i < products.size(); i++) {
            Product product = (Product) products.get(i);
            if (product.isOutOfStock()) outOfStock.add(product);
        }
        return outOfStock;
    }
}
