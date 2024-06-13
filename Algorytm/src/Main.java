import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static final double PC = 0.2;
    public static final double PM = 0.01;
    public static final int MAX_EVALS = 500;
    public static final double CROSS_MULT_1 = 0.3, CROSS_MULT_2 = 0.6;
    public static final int POP_SIZE = 280;
    public static List<City> Cities = new ArrayList<City>();

    public static Random rand = new Random();

    public static List<City> loadCities() {
        List<City> ListOfCities = new ArrayList<City>();
        ListOfCities.add(new City(0, 0, "Warszawa"));
        ListOfCities.add(new City(1, 1, "Kraków"));
        ListOfCities.add(new City(2, 2, "Gdańsk"));
        ListOfCities.add(new City(3, 3, "Wrocław"));
        ListOfCities.add(new City(4, 4, "Poznań"));

        return ListOfCities;
    }

    public static Population algorytm(List<City> ListOfCities) {
        Population currPop = new Population(POP_SIZE, ListOfCities, PC, PM);
        int crossPoint1 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_1),
                crossPoint2 = (int) Math.floor(ListOfCities.size() * CROSS_MULT_2);

        int currEvals = 0;

        while (currEvals < MAX_EVALS) {
            currEvals += POP_SIZE;

            Population childPop = new Population(currPop.select(), POP_SIZE, ListOfCities, PC, PM, crossPoint1,
                    crossPoint2);

            currPop = childPop;

        }

        return currPop;
    }



    public static void main(String[] args) {
        String fileName = "a280.tsp";
        List<City> miasta = Repository.loadTSPFile(fileName);
        Cities = Repository.loadTSPFile(fileName);

        Population pop = algorytm(miasta);



        Individual New = new Individual(miasta);
        Individual Parent1 = new Individual(miasta);
        Individual Parent2 = new Individual(miasta);
        New.crossover(Parent1, Parent2, 1, 5);

        System.out.println("Parents:");
        System.out.println(Parent1.listOfCities);
        System.out.println(Parent2.listOfCities);
        System.out.println("Child:");
        System.out.println(New.listOfCities);

        // Testowanie wczytywania pliku

        Cities = Repository.loadTSPFile(fileName);

        // for (City city : Cities) {
        // System.out.println(city);
        // }

        // Testowanie obliczania dystansu
        New.calculateDistances();
        System.out.println("");
        System.out.println("ListOfDistances: " + New.listOfDistances);
        New.calculateDistances();

        // Testowanie sumy dystansów
        System.out.println("SumOfDistances: " + New.sumOfDistances(New.listOfDistances));

        // Testowanie ListySumyDystansów
        System.out.println("Increasing Distance: " + New.GetListOfSumDistances());

        //Testowanie zapisu populacji
        Population poptemp= new Population(POP_SIZE, miasta, PC, PM);

        New.calculateDistances();
        poptemp.individuals.add(New);
        Repository.SavePopulation(poptemp, "Individual");

    }

}
