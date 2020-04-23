package done.firstTaskSet.taskC;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    //Kernel functions
    public static double uniform(double u){ return Math.abs(u) < 1.0 ? 0.5 : 0.0; }

    public static double triangular(double u){
        return Math.abs(u) < 1.0 ? 1.0 - Math.abs(u) : 0.0;
    }

    public static double epanechnikov(double u){
        return Math.abs(u) < 1.0 ? 0.75 * (1.0 - Math.pow(u, 2)) : 0.0;
    }

    public static double quartic(double u){ return Math.abs(u) < 1.0 ? 15.0 / 16.0 * Math.pow((1.0 - Math.pow(u, 2)), 2) : 0.0; }

    public static double triweight(double u){
        return Math.abs(u) < 1.0 ? 35.0 / 32.0  * Math.pow((1.0 - Math.pow(u, 2)), 3) : 0.0;
    }

    public static double tricube(double u){ return Math.abs(u) < 1.0 ? 70.0 / 81.0 * Math.pow((1.0 - Math.pow(Math.abs(u), 3)), 3) : 0.0; }

    public static double cosine(double u){
        return Math.abs(u) < 1.0 ? Math.PI / 4.0 * Math.cos(Math.PI / 2.0 * u) : 0.0;
    }

    public static double gaussian(double u){
        return 1.0 / Math.sqrt(2.0 * Math.PI) * Math.pow(Math.E, -0.5 * u * u);
    }

    public static double logistic(double u){
        return 1.0 / (Math.pow(Math.E, u) + 2.0 + Math.pow(Math.E, -u));
    }

    public static double sigmoid(double u){
        return 2.0 / Math.PI / (Math.pow(Math.E, u) + Math.pow(Math.E, -u));
    }

    public enum DistanceFunction{
        MANHATTAN,
        EUCLIDEAN,
        CHEBYSHEV
    }

    public static double manhattan(double[] a, double[] b){
        double result = 0.0;

        for (int i = 1; i < a.length - 1; ++i)
            result += Math.abs(a[i] - b[i]);

        return result;
    }

    public static double euclidean(double[] a, double[] b){
        double result = 0.0;

        for (int i = 1; i < a.length - 1; ++i)
            result += Math.pow(a[i] - b[i], 2);

        return Math.sqrt(result);
    }

    public static double chebyshev(double[] a, double[] b){
        double result = 0.0;

        for (int i = 1; i < a.length - 1; ++i)
            if (Math.abs(a[i] - b[i]) > result)
                result = Math.abs(a[i] - b[i]);

        return result;
    }

    public enum KernelFunction{
        UNIFORM,
        TRIANGULAR,
        EPANECHNIKOV,
        QUARTIC,
        TRIWEIGHT,
        TRICUBE,
        GAUSSIAN,
        COSINE,
        LOGISTIC,
        SIGMOID
    }

    public static double getKernelFunctionResult(KernelFunction kernelFunction, double distance){
        switch (kernelFunction) {
            case UNIFORM:
                return uniform(distance);
            case TRIANGULAR:
                return triangular(distance);
            case EPANECHNIKOV:
                return epanechnikov(distance);
            case QUARTIC:
                return quartic(distance);
            case TRIWEIGHT:
                return triweight(distance);
            case TRICUBE:
                return tricube(distance);
            case GAUSSIAN:
                return gaussian(distance);
            case COSINE:
                return cosine(distance);
            case LOGISTIC:
                return logistic(distance);
            case SIGMOID:
                return sigmoid(distance);
            default:
                return 0.0;
        }
    }

    public static double getDistance(DistanceFunction distanceFunction, double[] request, double[] ithParametersMatrix){

        switch (distanceFunction) {
            case EUCLIDEAN:
                return euclidean(request, ithParametersMatrix);
            case MANHATTAN:
                return manhattan(request, ithParametersMatrix);
            case CHEBYSHEV:
                return chebyshev(request, ithParametersMatrix);
            default:
                return 0.0;
        }
    }

    public static double getPredictedResultforNaiveMethod(double[][] parametersMatrix,
                                                          double[] request,
                                                          KernelFunction kernelFunction,
                                                          DistanceFunction distanceFunction,
                                                          String windowType,
                                                          double h,
                                                          int K) {

        int N = parametersMatrix.length;
        int M = parametersMatrix[0].length - 1;

        if (windowType.equals("variable")){
            ArrayList<Double> distances = new ArrayList<>();

            for (int i = 0; i < parametersMatrix.length; ++i)
                distances.add(getDistance(distanceFunction, request, parametersMatrix[i]));

            Collections.sort(distances);

            h = distances.get(K);

            if (h == distances.get(K - 1))
                h += 0.0000001;
        }

        double numerator = 0.0;
        double denominator = 0.0;

        if (h != 0) {
            for (int i = 0; i < parametersMatrix.length; ++i) {

                double distance = getDistance(distanceFunction, request, parametersMatrix[i]);
                double kernelFunctionResult = getKernelFunctionResult(kernelFunction, distance / (double) h);

                numerator += kernelFunctionResult * parametersMatrix[i][M];
                denominator += kernelFunctionResult;
            }
        }
        else {
            for (int i = 0; i < parametersMatrix.length; ++i) {
                double distance = getDistance(distanceFunction, request, parametersMatrix[i]);

                if (distance == 0.0) {
                    numerator += 1.0 * parametersMatrix[i][M];
                    denominator += 1.0;
                }
            }
        }

        if ((denominator != 0)) {
            return(numerator / denominator);
        } else {

            for (int i = 0; i < N; ++i)
                numerator += 1.0 * parametersMatrix[i][M];;

           return(numerator / N);
        }
    }

    public static double getPredictedResultforOneHotMethod(double[][] parametersMatrix,
                                                          double[] request,
                                                          KernelFunction kernelFunction,
                                                          DistanceFunction distanceFunction,
                                                          String windowType,
                                                          double h,
                                                          int K){

        ArrayList<Double> values = new ArrayList<>(11);

        double[][]data = new double[parametersMatrix.length][28];
        double[] newRequest = new double[28];

        for (int i = 0; i < parametersMatrix.length; ++i) {
            for (int j = 0; j < 28; ++j)
                data[i][j] = parametersMatrix[i][j];
        }

        for (int i = 0; i < 28; ++i){
            newRequest[i] = request[i];
        }

        for (int i = 28; i < 38; ++i) {
            for (int j = 0; j < parametersMatrix.length; ++j)
                data[j][27] = parametersMatrix[j][i];

            values.add(getPredictedResultforNaiveMethod(data, newRequest, kernelFunction, distanceFunction, windowType, h, K));
        }

        double maxValue = 0;
        int max = 0;

        for (int i = 0; i < 11; ++i){
            if (values.get(i) > maxValue){
                maxValue = values.get(i);
                max = i + 1;
            }
        }

        return max;
    }
}
