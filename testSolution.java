package done.firstTaskSet.taskA;

import java.util.Arrays;
import java.util.Scanner;

public class testSolution {
    //naive realisation
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfObjects = scanner.nextInt();
        int numberOfClasses = scanner.nextInt();
        int numberOfParts = scanner.nextInt();

        int[] classes = new int[numberOfObjects];
        int[] positions = new int[numberOfObjects];

        for (int i = 0; i < numberOfObjects; ++i){
            classes[i] = scanner.nextInt();
            positions[i] = i + 1;
        }

        System.out.println(Arrays.toString(classes));
        System.out.println(Arrays.toString(positions));

        for (int i = 0; i < numberOfObjects; ++i){
            for (int j = i; j < numberOfObjects; ++j){
                if (classes[i] > classes[j]){
                    int temp = classes[i];
                    classes[i] = classes[j];
                    classes[j] = temp;

                    int tempPos = positions[i];
                    positions[i] = positions[j];
                    positions[j] = tempPos;
                }
            }
        }

        System.out.println(Arrays.toString(classes));
        System.out.println(Arrays.toString(positions));

        int i = 0;
        for (; i < numberOfObjects % numberOfParts; ++i){
            System.out.print(numberOfObjects / numberOfParts + 1 + " ");

            for (int k = i; k < numberOfObjects; k += numberOfParts){
                System.out.print(positions[k] + " ");
            }

            System.out.println();
        }

        for (; i < numberOfParts; ++i ){
            System.out.print(numberOfObjects / numberOfParts + " ");

            for (int k = i; k < numberOfObjects; k += numberOfParts){
                System.out.print(positions[k] + " ");
            }

            System.out.println();
        }
        //*/


    /*
        ArrayList<ArrayList<Integer>> classes = new ArrayList<>(numberOfClasses);

        for (int i = 0; i < numberOfClasses; ++i){
            ArrayList<Integer> currentArray = new ArrayList<>();
            classes.add(currentArray);
        }

        for (int i = 1; i <= numberOfObjects; ++i){
            int classOfObject = scanner.nextInt();
            classes.get(classOfObject - 1).add(i);
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<>(numberOfParts);

        for (int i = 0; i < numberOfParts; ++i){
            ArrayList<Integer> currentArray = new ArrayList<>();
            result.add(currentArray);
        }

        //logic
        //main idea - to add to each class N / K elements
        // m nod K elements should be added to other categories 1 to each

        int currentClass;
        for (int i = 0; i < classes.size(); ++i){
            int numberOfElementsInPart = classes.get(i).size() / numberOfParts;
            for (int j = 0; j < numberOfParts; ++j){
                for (int k = 0; k < numberOfElementsInPart; ++k){
                    //вставляем элементы с позиции j * numberOfParts + k
                }
            }
        }
        /*
        for(int i = 0; i < classes.size(); ++i){
            System.out.println(classes.get(i).toString());
        }
        //*/

        scanner.close();
    }
}
