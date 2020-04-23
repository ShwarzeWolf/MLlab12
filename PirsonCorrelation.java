package done;

import java.util.Scanner;

public class PirsonCorrelation {

    public static double findAverage(int[] massive){
        long sum = 0;

        for (int i = 0; i < massive.length; ++i){
            sum += massive[i];
        }

        return (double) sum / (double) massive.length;
    }

    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);

        int N = scanner.nextInt();

        int[] x = new int[N];
        int[] y = new int[N];

        for (int i = 0; i < N; ++i){
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }

        double xAverage = findAverage(x);
        double yAverage = findAverage(y);

        double numerator = 0.0;
        double xSqr = 0.0;
        double ySqr = 0.0;

        for (int i = 0; i < N; ++i){
            numerator += (x[i] - xAverage) * (y[i] - yAverage);
            xSqr += Math.pow(x[i] - xAverage, 2);
            ySqr += Math.pow(y[i] - yAverage, 2);
        }

        double denominator = Math.sqrt(xSqr * ySqr);

        if (denominator != 0.0)
            System.out.println(numerator / denominator);
        else
            System.out.println(0.0);
    }
}
