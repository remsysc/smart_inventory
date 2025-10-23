package entities;

public class DoublyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private static class Node {

        Object data;
        Node next;
        Node prev;

        Node(Object data) {
            this.prev = this.next = null; //explicit set to null, but not really needed
            this.data = data;
        }
    }

    public DoublyLinkedList() {
        this.head = this.tail = null; //explicit set to null, but not really needed
        this.size = 0;
    }

    /**
     * Adds element to the end of the list
     */
    public void add(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(
                "Index:" + index + ", Size " + size
            );
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(
                "Index:" + index + ", Size " + size
            );
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
