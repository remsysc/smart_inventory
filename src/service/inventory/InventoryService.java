package service.inventory;

import entities.DoublyLinkedList;
import entities.Product;

public interface InventoryService {
    void addProduct(Product product);
    Product findProductById(String id);
    void deleteProduct(String id);
    void displayProductsForward();
    void displayProductsBackward();
    void updateProduct(
        String id,
        String newName,
        double newPrice,
        int newQuantity
    );

    DoublyLinkedList getAllProducts();
}
