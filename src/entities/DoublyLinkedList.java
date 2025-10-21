package entities;

public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
         Object data;
        Node next;
        Node prev;
        Node(Object data){
            this.prev = null;
            this.next = null;
            this.data = null;

        }


    }
    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds element to the end of the list
     */
    public void add(Object data){
        Node newNode = new Node(data);
        if(head == null){
            head = tail = newNode;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;

    }

    public Object get(int index){
        if(index <0 || index >= size){
            throw  new IllegalArgumentException("Index:"  + index + ", Size " + size);
        }
        Node current = head;
        for(int i = 0;i <index; i++){
            current= current.next;
        }

        return current.data;

    }

    public Object remove(int index){
        if(index <0 || index >= size){
            throw  new IllegalArgumentException("Index:"  + index + ", Size " + size);
        }

        Node current = head;
        for(int i = 0;i < index;i++){
            current = current.next;
        }
        Object data = current.data;
        if(current.prev != null)
        {
            current.prev.next = current.next;
        }else{
            head = current.next;
        }

        if(current.next != null){
            current.next.prev = current.prev;
        }else{
            tail = current.prev;
        }
        size--;
        return data;
    }
    public  int size(){
        return  size;
    }
    public  boolean isEmpty(){
        return  size == 0;
    }

    public void display(){
        if(isEmpty()){
            System.out.println("List is empty");
            return;
        }
        Node current = head;
        System.out.print("[");
        while(current != null){
            System.out.print(current.data);
            if(current.next != null){
                System.out.println(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }
}
