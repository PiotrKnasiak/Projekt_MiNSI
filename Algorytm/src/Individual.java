import java.util.*;

/**
 * @deprecated
 */
public class Individual implements Comparable<Individual> {
    // lp nic tu nie znaczy
    public List<Integer> listOfCities;
    public List<Integer> listOfDistances;// doesnt work till i dont get data from file
    private Random rand = new Random();

    /**
     * Konstruktor generujący nowego osobnika z krzyżowania
     * 
     * @param parent1     {@code Individual} Pierwszy rodzic
     * @param parent2     {@code Individual} Drugi rodzic
     * @param crossPoint1 {@code int} Pierwszy punk krzyżowania
     * @param crossPoint2 {@code int} Drugi punk krzyżowania
     */
    public Individual(Individual parent1, Individual parent2, int crossPoint1, int crossPoint2) {

        // nigdzie indziej nie użyje się crossover, niż przy towrzeniu nowego osobnika
        // więc najlepiej przenieść kod funckji tutaj
        crossover(parent1, parent2, crossPoint1, crossPoint2);
    }

    /**
     * Konstruktor generujący nowego osobnika, pierwsza populacja
     * 
     * @param listOfCities Lista {@code City} z których wybiera miasta
     */
    public Individual(List<City> listOfCities) {
        // losuje tyle losowych liczb, ile jest miast of (od 0 do ilczby miast-1)
        // tl;dr -> losuje indexy miast jakie przypisać
        int[] cities = rand.ints(0, listOfCities.size()).distinct().limit(listOfCities.size()).toArray();

        for (int i = 0; i < cities.length; i++) {
            this.listOfCities.set(i, cities[i]);
        }
        this.listOfCities.set(cities.length, cities[0]);

        for (int i = 0; i < cities.length - 1; i++) {
            City c1 = listOfCities.get(cities[i]), c2 = listOfCities.get(cities[i + 1]);
            this.listOfDistances.set(i, c1.distance(c2));
        }
        // odległość pierwszego od ostatniego
        this.listOfDistances.set(cities.length - 1,
                listOfCities.get(cities[0]).distance(listOfCities.get(cities[cities.length - 1])));
    }

    public int sumOfDistances() {
        return listOfDistances.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * @deprecated Do kompletnej przeróbki
     * @param parent1     {@code Individual} Pierwszy rodzic
     * @param parent2     {@code Individual} Drugi rodzic
     * @param crossPoint1 {@code int} Pierwszy punk krzyżowania
     * @param crossPoint2 {@code int} Drugi punk krzyżowania
     */
    public void crossover(Individual parent1, Individual parent2, int crossPoint1, int crossPoint2) {
        int size = parent1.listOfCities.size();
        List<Integer> offspring = new ArrayList<>(Arrays.asList(new Integer[size]));
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();

        // Step 1: Copy the segment from parent1 to the offspring
        for (int i = crossPoint1; i <= crossPoint2; i++) {
            offspring.set(i, parent1.listOfCities.get(i));
            mapping1.put(parent1.listOfCities.get(i), parent2.listOfCities.get(i)); // komicznie źle; używa numeru
                                                                                    // miasta jako klucza;
                                                                                    // zwróci "dobrego" odobnika, ale to
                                                                                    // nie jest krzyżowanie
            mapping2.put(parent2.listOfCities.get(i), parent1.listOfCities.get(i)); // też komicznie źle;
        }

        // Step 2: Resolve conflicts and fill the rest from parent2
        for (int i = 0; i < size; i++) {
            if (i >= crossPoint1 && i <= crossPoint2) {
                continue;
            }

            int gene = parent2.listOfCities.get(i);
            while (mapping1.containsKey(gene)) {
                gene = mapping1.get(gene);
            }
            offspring.set(i, gene);
        }

        // Step 3: Fill the missing elements from parent1
        for (int i = 0; i < size; i++) {
            if (offspring.get(i) == null) {
                offspring.set(i, parent1.listOfCities.get(i));
            }
        }

        listOfCities = offspring;

    }

    /**
     * Mutuje osobnika zamieniając miejscami 2 miasta
     */
    public void mutate() {
        // miasta do zmiany
        int[] swap = rand.ints(0, listOfCities.size()).distinct().limit(2).toArray();

        // ...
    }

    /**
     * Zwraca sumę przebytego dystansu
     * 
     * @return {@code int}
     */
    public int sumDistance() {
        return listOfCities.stream().mapToInt(d -> d).sum();
    }

    @Override
    public int compareTo(Individual other) {
        // Compare books based on their publication year
        return Integer.compare(this.sumOfDistances(), other.sumOfDistances());
    }
}
