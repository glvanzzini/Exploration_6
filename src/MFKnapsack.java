/**
 * Created by Giampiero on 11/29/2016.
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
public class MFKnapsack {
    private static int[] Weights; //weights array
    private static int[] Values; //values array
    private static int[][] F; //knapsack array

    //array of items to bring index inside of the knapsack code
    private static int W;
    public static void main (String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));
       // in.nextLine();
        in.nextInt();//skip first line
        int n = 0;

        while(in.hasNextInt()){//loop counts number of items
            in.nextInt();
            n++;
        }
        n = n/2; //this is divided to give the number of items
        in.close();
        in = new Scanner(new File(args[0]));
        W = in.nextInt();
        Weights = new int[n+1];
        Values = new int[n+1];
        F = new int[n+1][W+1];
        //loop initializes arrays with the values and weights
        for(int i = 1; i < n+1; i++){
            Weights[i] = in.nextInt();
            Values[i] = in.nextInt();
            //System.out.println("Weight: " + Weights[i] + "\tValues: " + Values[i]);
        }
        in.close();
        //for loops initialize the F table
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < W+1; j++){
                if(i == 0 || j == 0){
                    F[i][j] = 0;
                } else{
                    F[i][j] = -1;
                }
            }
        }
        long startTime = System.nanoTime();
        int value = knapsack(n, W);
        long endTime = System.nanoTime() - startTime;
        //this code can be uncommented to print the solved knapsack table
       // System.out.println("Solved knapsack table:");
//        for(int i = 0; i < n+1; i++) {
//            for (int j = 0; j < W +1; j++) {
//                System.out.print(F[i][j] + "\t");
//            }
//            System.out.println();
//        }
        //prints the items in the optimal solution
        PrintOptimalSolution(F, Weights, n, W);
        System.out.println("The maximum cost that can be carried is: " + value + "; completed in " + endTime + " nanoseconds");

    }

    //preforms the memory function knapsack algorithm
    private static int knapsack(int i, int j) {
       int value;

        if(F[i][j] < 0){
           if(j < Weights[i]){
               value = knapsack(i - 1, j);
           } else{
               value = max(knapsack(i - 1, j), (Values[i] + knapsack(i-1, j - Weights[i])));

           }

           F[i][j] = value;
       }

        return F[i][j];
    }

    private static int max(int i, int j){
        if(i > j){
            return i;
        }else{
            return j;
        }
    }

    //prints optimal list of items
        public static void PrintOptimalSolution(int[][] MF, int[] w, int n, int c) {
        //MF is the matrix/table, w[] is the array with the weights, n is the num of items and c is the capacity
        ArrayList<Integer> opt = new ArrayList<Integer>();
        int j = c;
        int i = n;
        while (i > 0) {
            //System.out.println("J: " + j);
            if (j == 0)
                break;
            else if (MF[i][j] != MF[i-1][j]) {
                //System.out.println("I: " + i);
                opt.add(i);
                j = j-w[i];
            }

            i = i-1;

        }
        System.out.print("Items: ");
        for (int p = 0; p < opt.size();p++) {
            System.out.print(opt.get(p) + " ");
        }
        System.out.println();

    }


}
