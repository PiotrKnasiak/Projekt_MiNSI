import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static Random rand = new Random();

    public static List<City> loadCities() {
        List<City> ListOfCities = new ArrayList<City>();

        return ListOfCities;
    }

    public static Population algorytm(List<City> ListOfCities) {
        return new Population(ListOfCities.size(), ListOfCities);
    }

    public static void savePop(Population pop) {

    }

    public static void main(String[] args) {
        List<City> miasta = loadCities();
        Population pop = algorytm(miasta);
        savePop(pop);
    }

}
