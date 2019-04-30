import org.junit.jupiter.api.Test;

public class LRU {

    Node head;
    int size;

    @Test
    public void test() {
        int i = 10;

        if (++i > 10) {
            System.out.println(">10");
        }

        System.out.println("i = " + i);
    }

    public String get(int key) {
        if (head == null) {
            return null;
        }

        Node prev = head;
        do {
            if (prev.key == key) {
                return prev.value;
            }
        } while ((prev = prev.next) != null);

        return null;
    }

    public void set(int key, String value) {
        Node n = new Node();
        n.key = key;
        n.value = value;

        if (size > 10) {
            if (get(key) == null) {
                n.next = head;
                head = n;


            }
        }
    }





    class Node {
        Node next;
        int key;
        String value;
    }
}
