package done.firstTaskSet.Lab1;

import done.firstTaskSet.taskC.Solution;
import done.firstTaskSet.taskB.FScoreCounter;
import done.firstTaskSet.taskC.Solution.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab1Part1 {

    private static final String COMMA_DELIMITER = ",";

    private static ArrayList<Double> getRecordFromLine(String line) {
        ArrayList<Double> values = new ArrayList<Double>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(Double.parseDouble(rowScanner.next()));
            }
        }
        return values;
    }

    public static void main(String[] args) throws FileNotFoundException {

        String[] description = new String[28];
        ArrayList<ArrayList<Double>> data = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("C:\\Users\\1358365\\IdeaProjects\\ML\\resources\\input.csv"));) {
            description = scanner.nextLine().split(COMMA_DELIMITER);

            while (scanner.hasNextLine())
                data.add(getRecordFromLine(scanner.nextLine()));

        } catch (java.io.FileNotFoundException exception) {
            System.out.println("File not found");
        }

        DistanceFunction bestDistanceFunction =  DistanceFunction.EUCLIDEAN;
        KernelFunction bestKernelFunction = KernelFunction.UNIFORM;
        int bestK = 1;
        double bestLOO = Double.POSITIVE_INFINITY;

        //for (DistanceFunction distanceFunction :  DistanceFunction.values()) {

          //  for (KernelFunction kernelFunction : KernelFunction.values()){

                int localBestK = 1;
                double localBestLOO = Double.POSITIVE_INFINITY;

                for (int K = 1; K < 30; K++) {
                    double LOO = 0.0;

                    int[][]confusionMatrix = new int[11][11];

                    for (int i = 0; i < 11; ++i){
                        for (int j = 0; j < 11; ++j)
                            confusionMatrix[i][j] = 0;
                    }

                    for (int i = 0; i < data.size(); ++i) {
                        ArrayList<Double> requestRow = (ArrayList<Double>) data.get(i).clone();

                        double[] request = requestRow.stream()
                                .mapToDouble(Double::doubleValue)
                                .toArray();

                        ArrayList<ArrayList<Double>> parametersMatrixRow = (ArrayList<ArrayList<Double>>) data.clone();
                        parametersMatrixRow.remove(i);

                        double[][] parametersMatrix = new double[parametersMatrixRow.size()][parametersMatrixRow.get(0).size()];

                        for (int j = 0; j < parametersMatrixRow.size(); ++j)
                            parametersMatrix[j] = parametersMatrixRow.get(j).stream()
                                    .mapToDouble(Double::doubleValue)
                                    .toArray();

                       int result = (int) Math.round(Solution.getPredictedResultforNaiveMethod(parametersMatrix,
                                request, bestKernelFunction, bestDistanceFunction,
                                "variable", 0.0, K));

                       int actualResult = (int) Math.round(requestRow.get(13));

                       confusionMatrix[result][actualResult] += 1;
                    }

                    FScoreCounter.countFScore(K, confusionMatrix, 11);

                }


        //    }
        //}

        System.out.println("Global best parameters with LOO = " + bestLOO + " are:" +
                "\nbest K: " + bestK +
                "\nbest distanceFunction: " + bestDistanceFunction +
                "\nbest kernelFunction: " + bestKernelFunction);
    }

    public static int getClassOfResult(ArrayList<Double> a){

        for (int i = 27; i < 38; ++i)
            if (a.get(i).equals(0.0) )
                return i;

        return 38;
    }

    public static void getResultForOneHot(String[] args) throws FileNotFoundException {

        String[] description = new String[38];
        ArrayList<ArrayList<Double>> data = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("C:\\Users\\1358365\\IdeaProjects\\ML\\resources\\OneHotNormalised.csv"));) {
            description = scanner.nextLine().split(COMMA_DELIMITER);

            while (scanner.hasNextLine())
                data.add(getRecordFromLine(scanner.nextLine()));

        } catch (java.io.FileNotFoundException exception) {
            System.out.println("File not found");
        };

        ArrayList<Double> requestRow = (ArrayList<Double>) data.get(0).clone();

        double[] request = requestRow.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        ArrayList<ArrayList<Double>> parametersMatrixRow = (ArrayList<ArrayList<Double>>) data.clone();
        parametersMatrixRow.remove(0);

        double[][] parametersMatrix = new double[parametersMatrixRow.size()][parametersMatrixRow.get(0).size()];

        for (int j = 0; j < parametersMatrixRow.size(); ++j)
            parametersMatrix[j] = parametersMatrixRow.get(j).stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();


        DistanceFunction bestDistanceFunction =  DistanceFunction.MANHATTAN;
        KernelFunction bestKernelFunction = KernelFunction.UNIFORM;
        int bestK = 1;
        double bestLOO = Double.POSITIVE_INFINITY;

        for (DistanceFunction distanceFunction :  DistanceFunction.values()) {

            for (KernelFunction kernelFunction : KernelFunction.values()){
                int localBestK = 1;
                double localBestLOO = Double.POSITIVE_INFINITY;

                for (int K = 1; K < 3; K++) {
                    double LOO = 0.0;

                    for (int i = 0; i < data.size(); ++i) {


                        if (Solution.getPredictedResultforOneHotMethod(parametersMatrix,
                                request, kernelFunction, distanceFunction,
                                "variable", 0.0, K) != getClassOfResult(requestRow)) {
                            LOO += 1;
                        }
                    }

                    if(LOO < localBestLOO){
                        localBestLOO = LOO;
                        localBestK = K;
                    }

                    if (LOO < bestLOO){
                        bestLOO = LOO;
                        bestK = K;
                        bestDistanceFunction = distanceFunction;
                        bestKernelFunction = kernelFunction;
                    }
                }

                System.out.println("Current best LOO result for distance function " + distanceFunction +  " and kernel function " + kernelFunction + ": " + localBestLOO + " with parameters: " + localBestK);
            }
        }

        System.out.println("Global best parameters with LOO = " + bestLOO + " are:" +
                "\nbest K: " + bestK +
                "\nbest distanceFunction: " + bestDistanceFunction +
                "\nbest kernelFunction: " + bestKernelFunction);
    }


}
