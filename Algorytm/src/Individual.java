import java.util.*;

public class Individual implements Comparable<Individual> {
    // lp nic tu nie znaczy
    public List<Integer> listOfCities = new ArrayList<>();
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
            this.listOfCities.add(cities[i]);
        }
        this.listOfCities.add(cities[0]);

        calculateDistances();
        // odległość pierwszego od ostatniego

    }

    public int sumOfDistances(List<Integer> TemplistOfDistances) {
        return TemplistOfDistances.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Krzyżuje dwa osobniki
     *
     * @param parent1     {@code Individual} Pierwszy rodzic
     * @param parent2     {@code Individual} Drugi rodzic
     * @param crossPoint1 {@code int} Pierwszy punk krzyżowania
     * @param crossPoint2 {@code int} Drugi punk krzyżowania
     */
    public void crossover(Individual parent1, Individual parent2, int crossPoint1, int crossPoint2) {
        int size = parent1.listOfCities.subList(0, parent1.listOfCities.size() - 1).size();
        List<Integer> offspring = new ArrayList<>(Arrays.asList(new Integer[size]));
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();

        // Step 1: Copy the segment from parent1 to the offspring
        for (int i = crossPoint1; i <= crossPoint2; i++) {
            offspring.set(i, parent1.listOfCities.get(i));
            mapping1.put(parent1.listOfCities.get(i), parent2.listOfCities.get(i));
            mapping2.put(parent2.listOfCities.get(i), parent1.listOfCities.get(i));

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
        offspring.add(offspring.get(0));

        listOfCities = offspring;

    }

    /**
     * Mutuje osobnika zamieniając miejscami 2 miasta
     * 
     * @param chanceToMutate szansa na mutację
     */
    public void mutate(int chanceToMutate)

    {
        if (rand.nextInt(100) > chanceToMutate) {
            return;
        }

        // miasta do zmiany

        int index1 = rand.nextInt(listOfCities.size());
        int index2 = rand.nextInt(listOfCities.size());

        // zamiana miejscami
        int temp = listOfCities.get(index1);
        listOfCities.set(index1, listOfCities.get(index2));
        listOfCities.set(index2, temp);

    }

    /**
     * Zwraca sumę przebytego dystansu
     * 
     * @return {@code int}
     */
    public int sumDistance() {
        return listOfCities.stream().mapToInt(d -> d).sum();
    }

    /**
     * Oblicza dystanse między miastami
     */
    public void calculateDistances() {
        listOfDistances = new ArrayList<>(listOfCities.size());
        for (int i = 0; i < listOfCities.size(); i++) {
            if (i != 0) {
                listOfDistances
                        .add(Main.Cities.get(listOfCities.get(i)).distance(Main.Cities.get(listOfCities.get(i - 1))));
                /*
                 * listOfDistances.add((int) calculateDistance(
                 * 
                 * Main.Cities.get(listOfCities.get(i)).x,
                 * Main.Cities.get(listOfCities.get(i)).y,
                 * Main.Cities.get(listOfCities.get(i - 1)).x,
                 * Main.Cities.get(listOfCities.get(i - 1)).y));
                 */
            } else {
                listOfDistances.add(0);
            }
        }
    }

    /**
     * zwraca listę odległości pokonywana przez komwojazera
     *
     * @return Lista odległości pokonywana przez komwojazera jako
     *         {@code List<Integer>}
     */
    public List<Integer> GetListOfSumDistances() {
        List<Integer> Temp = new ArrayList<>();
        for (int i = 0; i <= listOfCities.size(); i++) {
            if (i != 0) {
                Temp.add(sumOfDistances(listOfDistances.subList(0, i)));
            } else {
                Temp.add(0);
            }
        }
        return Temp.subList(1, Temp.size());
    }

    /**
     * Oblicza dystans między dwoma punktami
     *
     * @param x1 Pozycja x pierwszego punktu
     * @param y1 Pozycja y pierwszego punktu
     * @param x2 Pozycja x drugiego punktu
     * @param y2 Pozycja y drugiego punktu
     * @return Dystans jako {@code double}
     */
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public int compareTo(Individual other) {
        // Compare books based on their publication year
        return Integer.compare(this.sumDistance(), other.sumDistance());
    }
}
