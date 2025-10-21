package service;

import entities.DoublyLinkedList;
import entities.Product;

public class InventoryService {
    private final DoublyLinkedList products;

    public InventoryService(){
        this.products = new DoublyLinkedList();
    }

    public void addProduct(Product product){
        //check for product IDs
        for(int i = 0; i < products.size();i++){
            Product p = (Product)  products.get(i);
            if(p.getId().equals(product.getId())){
                throw new IllegalArgumentException("Product ID already exists: "  + product.getId());
            }
        }
        products.add(product);
        System.out.println("✓ Product added: " + product.getName());

    }

    public  Product findProductById(String id){
        for(int i = 0; i < products.size();i++){
            Product p = (Product) products.get(i);
            if(p.getId().equals(id))
                return p;
        }
        return  null;
    }

    public void updateProductQuantity(String id, int newQuantity){
        Product product = findProductById(id);
        if(product == null){
            throw new IllegalArgumentException("Product not found: " + id);
        }
        product.setQuantity(newQuantity);
        System.out.println("✓ Updated quantity for " + product.getName() + " to " + newQuantity);
    }

    public void deleteProduct(String id){
        for(int i =0;i < products.size();i++)
        {
            Product p = (Product) products.get(i);
            if(p.getId().equals(id)){
                products.remove(i);
                System.out.println("✓ Product deleted: " + p.getName()); //TODO: test
            }
        }
    }

    public  void displayAllProducts(){
        if(products.isEmpty()){
            System.out.println("No products in inventory");
            return;
        }
        System.out.println("\n=== INVENTORY ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = (Product) products.get(i);  // ← EXPLICIT CAST
            System.out.println(p);
        }

    }
    //for report service access
    DoublyLinkedList getAllProducts(){
        return  products;
    }
    

}
