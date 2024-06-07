import java.util.*;

public class Individual {
    public int LP;
    public List<Integer> ListOfCities;
    public List<Integer> ListOfDistances;//doesnt work till i dont get data from file
    public int SumOfDistances()
    {
        return ListOfDistances.stream().mapToInt(Integer::intValue).sum();
    }
    public void Crossover(Individual Parent1, Individual Parent2, int CrossOverPoint1, int CrossOverPoint2)
    {
        int size = Parent1.ListOfCities.size();
        List<Integer> offspring = new ArrayList<>(Arrays.asList(new Integer[size]));
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();

        // Step 1: Copy the segment from parent1 to the offspring
        for (int i = CrossOverPoint1; i <= CrossOverPoint2; i++) {
            offspring.set(i, Parent1.ListOfCities.get(i));
            mapping1.put(Parent1.ListOfCities.get(i), Parent2.ListOfCities.get(i));
            mapping2.put(Parent2.ListOfCities.get(i), Parent1.ListOfCities.get(i));
        }

        // Step 2: Resolve conflicts and fill the rest from parent2
        for (int i = 0; i < size; i++) {
            if (i >= CrossOverPoint1 && i <= CrossOverPoint2) {
                continue;
            }

            int gene = Parent2.ListOfCities.get(i);
            while (mapping1.containsKey(gene)) {
                gene = mapping1.get(gene);
            }
            offspring.set(i, gene);
        }

        // Step 3: Fill the missing elements from parent1
        for (int i = 0; i < size; i++) {
            if (offspring.get(i) == null) {
                offspring.set(i, Parent1.ListOfCities.get(i));
            }
        }

        ListOfCities = offspring;

    }
}
