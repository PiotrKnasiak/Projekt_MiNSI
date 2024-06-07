import java.util.*;

/**
 * Cała populacja składająca się z osobników typu {@code Individual}
 */
public class Population {
    List<City> ListOfCities;
    List<Individual> individuals;

    /**
     * Konstruktor generujący nową populację
     * 
     * @param numberOfInd  ilość osobników w polulacji
     * @param ListOfCities lista miast
     */
    public Population(int numberOfInd, List<City> ListOfCities) {
        this.ListOfCities = ListOfCities;

        for (int i = 0; i < numberOfInd; i++) {
            individuals.set(i, new Individual(ListOfCities));
        }
    }

    public int size() {
        return individuals.size();
    }

    /**
     * Sortuje populację i dobiera najlepsze osobniki
     * 
     * @param howMany {@code int}, ile wybrać osobników do krzyżowania
     * @return wybrany {@code Individual}
     */
    public Individual[] select(int howMany) {
        Individual[] selectedInds = new Individual[howMany];

        return selectedInds;
    }
}