package done.firstTaskSet.taskB;

public class FScoreCounter {

    public static void countFScore(int iterationNumber, int[][] confusionMatrix, int K) {

        double[] f_scores = new double[K];
        double[] numberOfElements = new double[K];

        double macroPrecision = 0.0;
        int macroRecall = 0;
        int all = 0;

        for (int i = 0; i < K; ++i){
            int numberOfSamplesInKthGroupFact = 0;
            int numberOfSamplesInKthGroupPredicted = 0;

            for (int j = 0; j < K; ++j){
                numberOfSamplesInKthGroupFact += confusionMatrix[i][j];
                numberOfSamplesInKthGroupPredicted += confusionMatrix[j][i];
            }

            if (numberOfSamplesInKthGroupPredicted != 0)
                macroPrecision += (double)confusionMatrix[i][i] * numberOfSamplesInKthGroupFact / numberOfSamplesInKthGroupPredicted;
            macroRecall += confusionMatrix[i][i];
            all += numberOfSamplesInKthGroupFact;

            double microFScore = (confusionMatrix[i][i] != 0)
                    ? 2.0 * (double)confusionMatrix[i][i] / ( numberOfSamplesInKthGroupFact + numberOfSamplesInKthGroupPredicted)
                    : 0.0;

            f_scores[i] = microFScore;
            numberOfElements[i] = numberOfSamplesInKthGroupFact;
        }

        double sumOfScores  = 0.0;
        int numberOfElemets = 0;

        for (int i = 0; i < K; ++i){
            sumOfScores += f_scores[i] * numberOfElements[i];
            numberOfElemets += numberOfElements[i];
        }

        double microFScore = sumOfScores / (double)numberOfElemets;
        double macroFScore = 2.0 * macroPrecision * macroRecall / ( all * ((double)macroPrecision + macroRecall));

        System.out.println("" + iterationNumber + " " + macroFScore + " " + microFScore);
    }
}
