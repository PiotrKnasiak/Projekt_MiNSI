import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static final double PC = 0.2;
    public static final double PM = 0.1;
    public static final int EVALS_MULT = 2;
    public static final double CROSS_MULT_1 = 0.3, CROSS_MULT_2 = 0.6;
    public static final int POP_SIZE = 20;

    public static List<City> Cities = new ArrayList<City>();
    public static List<Integer> bestLocal = new ArrayList<>(POP_SIZE * EVALS_MULT);
    public static List<Integer> current = new ArrayList<>(POP_SIZE * EVALS_MULT);
    public static int bestIndex = 0;
    public static Random rand = new Random();

    public static Population algorytm(List<City> ListOfCities) {
        Population currPop = new Population(POP_SIZE, ListOfCities, PC, PM);
        int crossPoint1 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_1),
                crossPoint2 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_2);

        int currEvals = 0;
        int debestaG = -1;

        while (currEvals < POP_SIZE * EVALS_MULT) {
            int debestaL = -1;
            currEvals += POP_SIZE;

            for (Individual i : currPop.individuals) {
                if (debestaL == -1 || debestaL > i.sumDistance())
                    debestaL = i.sumDistance();
                bestLocal.add(debestaL);
            }

            current.add(debestaG);

            Population childPop = new Population(currPop.select(), POP_SIZE, ListOfCities, PC, PM, crossPoint1,
                    crossPoint2);

            currPop = childPop;

        }

        currPop.sort();
        return currPop;
    }

    public static void main(String[] args) {
        String fileName = "a280.tsp";

        Cities = Repository.loadTSPFile(fileName);

        Population finalPop = algorytm(Cities);

        Repository.SaveResults(finalPop.ListOfCities.size(), current, false);
        Repository.SaveResults(finalPop.ListOfCities.size(), bestLocal, true);

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
