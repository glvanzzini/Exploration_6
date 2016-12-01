
import java.util.*;
import java.io.*;

public class BBKnapsack{

    public static List<Item> items = new LinkedList<>();
    private static int W;

    public static void main(String args[]) throws FileNotFoundException{

        Scanner in = new Scanner(new File(args[0]));
        W = in.nextInt();
        while(in.hasNext()) {
            Item item = new Item();
            item.weight = in.nextDouble();
            item.value = in.nextDouble();
            items.add(item);
        }

        long startTime = System.nanoTime();

        branchAndBoundKnapsack knapsack = new branchAndBoundKnapsack(items, W);
        System.out.print(knapsack.solve());
        long endTime = System.nanoTime() - startTime;

        System.out.println("; completed in " + endTime + " nanoseconds");
    }

    // class dedicated for the BBKnapsack
    public static class branchAndBoundKnapsack {

        public List<Item> items;
        public int capacity;

        public branchAndBoundKnapsack(List<Item> items, int capacity) {
            this.items = items;
            this.capacity = capacity;
        }

        private class Node implements Comparable<Node> {
            public int h;
            List<Item> taken;
            public double bound;
            public double value;
            public double weight;

            public Node() {
                taken = new ArrayList<>();
            }

            public Node(Node parent) {
                h = parent.h + 1;
                taken = new ArrayList<>(parent.taken);
                bound = parent.bound;
                value = parent.value;
                weight = parent.weight;
            }

            public int compareTo(Node other) {
                return (int) (other.bound - bound);
            }

            public void computeBound() {
                int i = h;
                double w = weight;
                bound = value;
                Item item;
                do {
                    item = items.get(i);
                    if (w + item.weight > capacity) break;
                    w += item.weight;
                    bound += item.value;
                    i++;
                } while (i < items.size());
                bound += (capacity - w) * (item.value / item.weight);
            }
        }

        public String solve() {
            Collections.sort(items, Item.byRatio());
            Node best = new Node();
            Node root = new Node();
            root.computeBound();
            PriorityQueue<Node> q = new PriorityQueue<Node>();
            q.offer(root);

            while (!q.isEmpty()) {
                Node node = q.poll();

                if (node.bound > best.value && node.h < items.size() - 1) {

                    Node with = new Node(node);
                    Item item = items.get(node.h);
                    with.weight += item.weight;

                    if (with.weight <= capacity) {

                        with.taken.add(items.get(node.h));
                        with.value += item.value;
                        with.computeBound();

                        if (with.value > best.value) {
                            best = with;
                        }
                        if (with.bound > best.value) {
                            q.offer(with);
                        }
                    }

                    Node without = new Node(node);
                    without.computeBound();

                    if (without.bound > best.value) {
                        q.offer(without);
                    }
                }
            }

            return "The maximum cost that can be carried is: " + best.value;
        }
    }

    public static class Item {
        public int label;
        public double value;
        public double weight;

        public static Comparator<Item> byLabel() {
            return (i1, i2) -> i1.label - i2.label;
        }

        public static Comparator<Item> byRatio() {
            return (i1, i2) -> Double.compare(i2.getRatio(), i1.getRatio());
        }

        public double getRatio() {
            return value / weight;
        }
    }

}

