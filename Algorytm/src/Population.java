import java.util.*;

/**
 * Cała populacja składająca się z osobników typu {@code Individual}
 */
public class Population {
    List<City> ListOfCities;
    List<Individual> individuals;
    double pc = 0.2, pm = 0.01;
    Random rand = new Random();

    /**
     * Konstruktor generujący pierwszą populację
     * 
     * @param numberOfInd  ilość osobników w polulacji
     * @param ListOfCities lista miast
     * @param pc           sznasa dla pojedyńczego osobnika na krzyżowania, w
     *                     granicy (0; 1), definiuje liczbę rodziców
     * @param pm           szansa na mutację
     */
    public Population(int numberOfInd, List<City> ListOfCities, double pc, double pm) {
        this.ListOfCities = ListOfCities;
        this.pc = pc;
        this.pm = pm;

        for (int i = 0; i < numberOfInd; i++) {
            individuals.set(i, new Individual(ListOfCities));
        }
    }

    /**
     * Konstruktor generujący nową populację z krzyżowania
     * 
     * @param parents      lista rodziców
     * @param numberOfInd  ilość osobników w polulacji
     * @param ListOfCities lista miast
     * @param pc           sznasa dla pojedyńczego osobnika na krzyżowania, w
     *                     granicy (0; 1), definiuje liczbę rodziców
     * @param pm           szansa na mutację
     */
    public Population(List<Individual> parents, int numberOfInd, List<City> ListOfCities, double pc, double pm,
            int crossPoint1, int crossPoint2) {
        this.ListOfCities = ListOfCities;
        this.pc = pc;
        this.pm = pm;

        for (int i = 0; i < parents.size(); i++) {
            individuals.set(i, parents.get(i));
        }

        // osobnicy z pary rodziców = {(rozmiarPop - ileRodziców) * 2 / ileRodzów} <-
        // zaokrąglone do góry

        int pInd1 = 0, pInd2 = 1;
        for (int i = parents.size(); i < numberOfInd;) {
            Individual parent1 = individuals.get(pInd1), parent2 = individuals.get(pInd2);
            int numberOfCh = (int) Math.ceil((numberOfInd - parents.size()) * 2 / parents.size());
            int j = 0;

            while (j < numberOfCh && i + 2 < numberOfInd) {
                individuals.set(i, new Individual(parent1, parent2, crossPoint1, crossPoint2));
                i++;
                individuals.set(i, new Individual(parent2, parent1, crossPoint1, crossPoint2));
                i++;
            }

            pInd1++;
            pInd2++;
        }

        for (int i = 0; i < numberOfInd; i++) {
            if (rand.nextDouble() < pm)
                individuals.get(i).mutate();
        }
    }

    public int size() {
        return individuals.size();
    }

    public void sort() {
        Collections.sort(individuals);
    }

    /**
     * Sortuje populację i dobiera najlepsze osobniki
     * 
     * @param howMany {@code int}, ile wybrać osobników do krzyżowania
     * @return wybrany {@code Individual}
     */
    public List<Individual> select() {
        List<Individual> selectedInds = new ArrayList<>();

        int howMany = (int) Math.floor(individuals.size() * pc); // ile rodziców

        howMany += howMany % 2; // zapewnia przystą liczbę rodziców

        sort();

        for (int i = 0; i < howMany; i++) {
            selectedInds.set(i, individuals.get(i));
        }

        return selectedInds;
    }
}