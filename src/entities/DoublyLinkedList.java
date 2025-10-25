package entities;

/**
 * A doubly linked list implementation that maintains references to both
 * the next and previous nodes, allowing bidirectional traversal.
 * This implementation stores elements as Object type.
 */
public class DoublyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Node class representing an element in the doubly linked list.
     * Each node contains data and references to both the next and previous nodes.
     */
    private static class Node {

        //The data stored in this node
        Object data;

        //Reference to the next node in the list
        Node next;

        //Reference to the previous node in the list
        Node prev;

        /**
         * Constructs a new node with the specified data.
         * Next and previous references are initialized to null.
         *
         * @param data the data to store in this node
         */
        Node(Object data) {
            this.prev = this.next = null;
            this.data = data;
        }
    }

    /**
     * Constructs an empty doubly linked list.
     * Initializes head and tail to null and size to 0.
     */
    public DoublyLinkedList() {
        this.head = this.tail = null;
        this.size = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     * This operation runs in constant time O(1).
     *
     * @param data the element to be appended to this list
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

    /**
     * Returns the element at the specified position in this list.
     * This operation runs in linear time O(n) where n is the index.
     *
     * @param index the index of the element to return (zero-based)
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + size
            );
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * This operation runs in linear time O(n) where n is the index.
     *
     * @param index the index of the element to be removed (zero-based)
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + size
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

    /**
     * Returns the number of elements in this list.
     * This operation runs in constant time O(1).
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * @return true if this list contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }
}
