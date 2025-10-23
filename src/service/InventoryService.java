package service;

import entities.DoublyLinkedList;
import entities.Product;

public class InventoryService {

    private final DoublyLinkedList products;

    public InventoryService() {
        this.products = new DoublyLinkedList();
    }

    public void addProduct(Product product) {
        //check for product IDs
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i);
            if (p.getId().equals(product.getId())) {
                throw new IllegalArgumentException(
                    "Product ID already exists: " + product.getId()
                );
            }
        }
        products.add(product);
        System.out.println("✓ Product added: " + product.getName());
    }

    public Product findProductById(String id) {
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i);
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public void updateProduct(
        String id,
        String newName,
        double newPrice,
        int newQuantity
    ) {
        Product product = findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        String oldName = product.getName();
        double oldPrice = product.getPrice();
        int oldQuantity = product.getQuantity();

        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantity(newQuantity);

        System.out.println(
            "✓ Updated product " +
                oldName +
                ": Name(" +
                oldName +
                " -> " +
                newName +
                "), Price(" +
                oldPrice +
                " -> " +
                newPrice +
                "), Quantity(" +
                oldQuantity +
                " -> " +
                newQuantity +
                ")"
        );
    }

    public void deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i);
            if (p.getId().equals(id)) {
                products.remove(i);
                System.out.println("✓ Product deleted: " + p.getName());
            }
        }
    }

    public void displayProductsForward() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory");
            return;
        }
        System.out.println("\n=== INVENTORY ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i); // ← EXPLICIT CAST
            System.out.println(p);
        }
    }

    public void displayProductsBackward() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory");
            return;
        }
        System.out.println("\n=== INVENTORY (REVERSED) ===");
        for (int i = products.size() - 1; i >= 0; i--) {
            Product p = (Product) products.get(i); // ← EXPLICIT CAST
            System.out.println(p);
        }
    }

    //for report service access
    DoublyLinkedList getAllProducts() {
        return products;
    }
}
