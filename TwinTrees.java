import java.util.Arrays;
import java.util.Formatter;

public class TwinTrees {

    private class Node {

        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private int n;
    private Node green;  // Usual order
    private Node red;  // Reverse order

    // Another representation
    private int[] lefts;
    private int[] rights;
    private boolean[] leftColors;
    private boolean[] rightColors;

    public TwinTrees(int[] p) {
        
        checkInput(p);
        this.n = p.length;
        int[] q = new int[n];
        for(int i = 0; i < n; i++) {
            q[i] = p[n-i-1];
        }

        this.green = new Node(p[0]);
        this.red   = new Node(q[0]);

        for(int i = 1; i < n; i++) {
            addRecursive(this.green, p[i]);
            addRecursive(this.red, q[i]);
        }

        lefts = new int[n];
        rights = new int[n];
        leftColors = new boolean[n];
        rightColors = new boolean[n];
        traverse(this.green, false);
        traverse(this.red, true);
    }

    public String toString() {
        String RESET = "\033[0m";
        String RED = "\033[0;31m";
        String GREEN = "\033[0;32m";

        Formatter fmt = new Formatter();
        fmt.format("%s %s %s\n", "L", "V", "R");
        for(int i = 0; i < n; i++) {
            String left = (this.leftColors[i] ? RED : GREEN) + (this.lefts[i] == 0 ? " " : "" + this.lefts[i]) + RESET;
            String value = "" + (i + 1);
            String right = (this.rightColors[i] ? RED : GREEN) + (this.rights[i] == 0 ? " " : "" + this.rights[i]) + RESET;
            fmt.format("%s %s %s\n", left, value, right);
        }
        String str = fmt.toString();
        fmt.close();
        return str;
    }

    private Node addRecursive(Node current, int value) {

        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }
    
        return current;
    }

    private void traverse(Node current, boolean color) {
        if(current.left != null) {
            this.lefts[current.value - 1] = current.left.value;
            this.leftColors[current.value - 1] = color;
            traverse(current.left, color);
        }
        if(current.right != null) {
            this.rights[current.value - 1] = current.right.value;
            this.rightColors[current.value - 1] = color;
            traverse(current.right, color);
        }
    }

    private void checkInput(int[] p) {
        int n = p.length;
        int[] copy = Arrays.copyOf(p,n);
        Arrays.sort(copy);
        for(int i = 0; i < n; i++) {
            assert copy[i] == (i+1) : "The input is not a valid permutation of " + n;
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) return;
        System.out.println("Running on: " + String.join(", ", args));
        int[] p = new int[args.length];
        for(int i = 0; i < args.length; i++) {
            p[i] = Integer.parseInt(args[i]);
        }
        TwinTrees twins = new TwinTrees(p);
        System.out.println(twins.toString());
    }
}