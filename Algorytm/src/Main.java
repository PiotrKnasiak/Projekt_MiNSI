import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final double PC = 0.2;
    public static final double PM = 0.01;
    public static final int MAX_EVALS = 500;
    public static final double CROSS_MULT_1 = 0.3, CROSS_MULT_2 = 0.6;
    public static final int POP_SIZE = 100;

    public static Random rand = new Random();

    public static List<City> loadCities() {
        List<City> ListOfCities = new ArrayList<City>();

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

        }

        return currPop;
    }

    public static void savePop(Population pop) {

    }

    public static void main(String[] args) {
        List<City> miasta = loadCities();

        Population pop = algorytm(miasta);

        savePop(pop);
    }

}
