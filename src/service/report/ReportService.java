package service.report;

import entities.DoublyLinkedList;

public interface ReportService {
    int getTotalNumberOfProducts();
    double getTotalSalesRevenue();
    void displayProductWithHighestSales();
    DoublyLinkedList getOutOfStockProducts();
}
