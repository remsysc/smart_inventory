package service.inventory;

import entities.DoublyLinkedList;
import entities.Product;

public class InventoryServiceImpl implements InventoryService {

    private final DoublyLinkedList products;

    public InventoryServiceImpl() {
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
        isProductValid(product);
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
            throw new NullPointerException("Product not found: " + id);
        }

        if (newPrice < 0 || newQuantity < 0) {
            throw new IllegalArgumentException(
                "New price or quantity cannot be less than 0."
            );
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
    public DoublyLinkedList getAllProducts() {
        return products;
    }

    private void isProductValid(Product product) {
        if (product == null) {
            throw new NullPointerException("Product cannot be null.");
        }

        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException(
                "Product quantity cannot be less than 0."
            );
        }

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException(
                "Product price cannot be less than 0."
            );
        }
    }
}
