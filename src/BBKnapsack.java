/**
 * Created by jacobmurillo on 11/30/16.
 *
 * Since we are using a state-space tree for this algorithm,
 * we would be using our own data structure to solve the
 * Branch-and-Bound Knapsack.
 *
 */

import java.util.*;
import java.io.*;

public class BBKnapsack{
    static int[] Weights;
    static int[] Values;
    static int[][] F;

    private static final int W = 5;
    public static void main(String args[]) throws FileNotFoundException{
        Scanner in = new Scanner(new File(args[0]));
        //in.nextLine();
        int n = 0;
        while(in.hasNextInt()){
            in.nextInt();
            n++;
        }
        n = n/2;
        in.close();
        in = new Scanner(new File(args[0]));
        Weights = new int[n+1];
        Values = new int[n+1];
        F = new int[n+1][W+1];
        for(int i = 1; i < n+1; i++){
            Weights[i] = in.nextInt();
            Values[i] = in.nextInt();
            System.out.println("Weight: " + Weights[i] + "\tValues: " + Values[i]);
        }
        in.close();
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < W+1; j++){
                if(i == 0 || j == 0){
                    F[i][j] = 0;
                } else{
                    F[i][j] = -1;
                }
            }
        }
        int value = knapsack(n, W);
        System.out.println(value);
        for(int i = 0; i < n+1; i++) {
            for (int j = 0; j < W +1; j++) {
                System.out.print(F[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static int knapsack(int i, int j){
        return 0;
    }

    // class dedicated for the BBKnapsack
    public class BranchAndBoundKnapsack{

        private class Node implements Comparable<Node>{
            public int h;
            List<Item> taken;
            public double bound, value, weight;

            public Node(){
                taken = new ArrayList<Item>();
            }

            public int compareTo(Node secondNode){
                return (int)(secondNode.bound - this.bound);
            }

        }
    }

    public class Item{

        public int label;
        public double value;
        public double weight;

        public double getRatio(){
            return value / weight;
        }

        public Comparator<Item> byLabel(){
            return new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.label - o2.label;
                }
            };
        }

        public Comparator<Item> byRatio(){
            return new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return Double.compare(o2.getRatio(), o1.getRatio());
                }
            };
        }
    }

}

