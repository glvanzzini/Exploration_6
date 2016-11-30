/**
 * Created by Giampiero on 11/29/2016.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class MFKnapsack {
    static int[] Weights;
    static int[] Values;
    static int[][] F;
    private static final int W = 5;
    public static void main (String[] args) throws FileNotFoundException {
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

    private static int knapsack(int i, int j) {
       int value = 0;
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


}
