import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static final double PC = 0.2;
    public static final double PM = 0.1;
    public static final int EVALS_MULT = 10000;
    public static final double CROSS_MULT_1 = 0.3, CROSS_MULT_2 = 0.6;

    public static int[] popList = { 10, 50, 200, 400, 600, 1500 };
    public static int POP_SIZE = 10; // 10, 50, 200, 400, 600, 1500
    public static List<City> Cities = new ArrayList<City>();
    public static List<Integer> bestGlobal = new ArrayList<>(EVALS_MULT);
    public static List<Integer> avgCurrent = new ArrayList<>(EVALS_MULT);
    public static int max101 = 0, max280 = 0;
    public static Random rand = new Random();

    public static Population algorytm(List<City> ListOfCities) {
        Population currPop = new Population(POP_SIZE, ListOfCities, PC, PM);
        int crossPoint1 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_1),
                crossPoint2 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_2);

        int currEvals = 0;
        int debestaG = -1;

        while (currEvals < POP_SIZE * EVALS_MULT) {
            int currSum = 0;
            currEvals += POP_SIZE;

            for (Individual i : currPop.individuals) {
                int sumDist = i.sumDistance();
                if (debestaG == -1 || debestaG > sumDist)
                    debestaG = sumDist;
                currSum += sumDist;
            }

            bestGlobal.add(debestaG);
            avgCurrent.add((int) (currSum / POP_SIZE));

            Population childPop = new Population(currPop.select(), POP_SIZE, ListOfCities, PC, PM, crossPoint1,
                    crossPoint2);

            currPop = childPop;

            if ((currEvals / POP_SIZE) % 500 == 0) {
                System.out.println("Progress: " + currEvals / POP_SIZE / 100 + "%");
            }
        }

        currPop.sort();
        return currPop;
    }

    public static void main(String[] args) {
        // piotr: a280, eil101
        String fileName1 = "a280.tsp";
        String fileName2 = "eil101.tsp";

        // kacper: kroA100, lin318
        // String fileName1 = "kroA100.tsp";
        // String fileName2 = "lin318.tsp";

        for (int i = popList.length - 1; i < popList.length; i++) {
            POP_SIZE = popList[i];

            // Plik 1

            /*
             * Cities = Repository.loadTSPFile(fileName1);
             * 
             * Population finalPop = algorytm(Cities);
             * 
             * Repository.SaveResults(finalPop.ListOfCities.size(), avgCurrent, false);
             * Repository.SaveResults(finalPop.ListOfCities.size(), bestGlobal, true);
             * 
             * bestGlobal = new ArrayList<>(EVALS_MULT);
             * avgCurrent = new ArrayList<>(EVALS_MULT);
             */
            // Plik 2

            Cities = Repository.loadTSPFile(fileName2);

            Population finalPop2 = algorytm(Cities);

            Repository.SaveResults(finalPop2.ListOfCities.size(), avgCurrent, false);
            Repository.SaveResults(finalPop2.ListOfCities.size(), bestGlobal, true);

            bestGlobal = new ArrayList<>(EVALS_MULT);
            avgCurrent = new ArrayList<>(EVALS_MULT);
        }

        /*
         * Individual New = new Individual(miasta);
         * Individual Parent1 = new Individual(miasta);
         * Individual Parent2 = new Individual(miasta);
         * New.crossover(Parent1, Parent2, 1, 5);
         * 
         * System.out.println("Parents:");
         * System.out.println(Parent1.listOfCities);
         * System.out.println(Parent2.listOfCities);
         * System.out.println("Child:");
         * System.out.println(New.listOfCities);
         * 
         * // Testowanie wczytywania pliku
         * 
         * Cities = Repository.loadTSPFile(fileName);
         * 
         * // for (City city : Cities) {
         * // System.out.println(city);
         * // }
         * 
         * // Testowanie obliczania dystansu
         * New.calculateDistances();
         * System.out.println("");
         * System.out.println("ListOfDistances: " + New.listOfDistances);
         * New.calculateDistances();
         * 
         * // Testowanie sumy dystansów
         * System.out.println("SumOfDistances: " +
         * New.sumOfDistances(New.listOfDistances));
         * 
         * // Testowanie ListySumyDystansów
         * System.out.println("Increasing Distance: " + New.GetListOfSumDistances());
         * 
         * // Testowanie zapisu populacji
         * Population poptemp = new Population(POP_SIZE, miasta, PC, PM);
         * 
         * New.calculateDistances();
         * poptemp.individuals.add(New);
         * Repository.SavePopulation(poptemp, "Individual");
         * 
         * Repository.SaveResults(5, Arrays.asList(1, 2, 34, 4, 5), false);
         */

        System.out.println();
    }

}
