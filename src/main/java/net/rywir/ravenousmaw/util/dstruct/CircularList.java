package net.rywir.ravenousmaw.util.dstruct;

public final class CircularList {

    private Node head;
    private Node tail;

    private CircularList() {}

    public static CircularList of(int... values) {
        CircularList list = new CircularList();

        if (values == null || values.length == 0) {
            return list;
        }

        Node head = new Node(values[0]);
        Node current = head;

        for (int i = 1; i < values.length; i++) {
            Node node = new Node(values[i]);
            node.prev = current;
            current.next = node;
            current = node;
        }

        current.next = head;
        head.prev = current;

        list.head = head;
        list.tail = current;

        return list;
    }

    public Node head() {
        return head;
    }

    public Node tail() {
        return tail;
    }

    public Node byValue(int i) {
        if (this.head == null) {
            return null;
        }

        Node current = this.head;

        do {
            if (current.val == i) {
                return current;
            }

            current = current.next;

        } while (current != this.head);

        return null;
    }

    public static final class Node {
        int val;

        Node next;
        Node prev;

        Node(int val) {
            this.val = val;
        }

        public int val() {
            return val;
        }

        public Node next() {
            return next;
        }

        public Node prev() {
            return prev;
        }
    }
}
