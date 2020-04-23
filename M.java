import java.util.Scanner;

public class M {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int K1 = scanner.nextInt();
        int K2 = scanner.nextInt();

        int N = scanner.nextInt();

        int[][] data = new int[N][2];

        for (int i = 0; i < N; ++i) {
            data[i][0] = scanner.nextInt() - 1;
            data[i][1] = scanner.nextInt() - 1;

        }

        int[][] observedData = new int[K1 + 1][K2 + 1];

        for (int i = 0; i < N; ++i) {
            int x1 = data[i][0];
            int x2 = data[i][1];

            observedData[x1][x2] += 1;
            observedData[x1][K2] += 1;
            observedData[K1][x2] += 1;
        }

        double xSquare = 0.0;

        for (int i = 0; i < K1; ++i) {
            if (observedData[i][K2] == 0)
                continue;

            for (int j = 0; j < K2; ++j) {
                double expectedValue = observedData[K1][j] * observedData[i][K2] / (double) N;

                if (expectedValue != 0.0)
                    xSquare += Math.pow(observedData[i][j] - expectedValue, 2) / expectedValue;
            }
        }

        System.out.println(xSquare);
    }
}
