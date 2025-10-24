package service.sale;

import entities.DoublyLinkedList;

public interface SaleService {
    void recordSale(String productId, int quantity);
    void displayAllSales();
    DoublyLinkedList getAllSales();
}
