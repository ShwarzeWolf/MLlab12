import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class RangSpearmanCorrelation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int[][] data = new int[N][4];

        for (int i = 0; i < N; ++i) {
            data[i][0] = scanner.nextInt();
            data[i][1] = scanner.nextInt();
        }

        Arrays.sort(data, Comparator.comparingInt(arr -> arr[0]));

        for (int i = 0; i < N; ++i) {
            data[i][2] = i;
        }

        Arrays.sort(data, Comparator.comparingInt(arr -> arr[1]));
        for (int i = 0; i < N; ++i) {
            data[i][3] = i;
        }

        double coefficient = 6.0 / (N * (N * N - 1.0));

        BigDecimal D = new BigDecimal("0.0");
        for (int i = 0; i < N; ++i) {
            BigDecimal currentValueBD = new BigDecimal(Math.pow(data[i][2] - data[i][3], 2));

            D = D.add(currentValueBD);
        }
        BigDecimal bdCoefficient = new BigDecimal(coefficient);
        D = D.multiply(bdCoefficient);

        double result = D.doubleValue();
        System.out.println(1.0 - result);
    }}

