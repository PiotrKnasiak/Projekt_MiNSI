import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<Integer> parent1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> parent2 = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1);
        // Losowanie punktów krzyżowania
        Random rand = new Random();
        int size = parent1.size();
        int crossoverPoint1 = rand.nextInt(size);
        int crossoverPoint2 = rand.nextInt(size);

        // Upewniamy się, że crossoverPoint1 < crossoverPoint2
        if (crossoverPoint1 > crossoverPoint2) {
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        List<Integer> offspring = pmxCrossover(parent1, parent2, crossoverPoint1, crossoverPoint2);
        System.out.println("Parent1: " + parent1);
        System.out.println("Parent2: " + parent2);
        System.out.println("Crossover points: " + crossoverPoint1 + ", " + crossoverPoint2);
        System.out.println("Offspring: " + offspring);
    }

    public static List<Integer> pmxCrossover(List<Integer> parent1, List<Integer> parent2, int crossoverPoint1, int crossoverPoint2) {
        int size = parent1.size();
        List<Integer> offspring = new ArrayList<>(Arrays.asList(new Integer[size]));
        Map<Integer, Integer> mapping1 = new HashMap<>();
        Map<Integer, Integer> mapping2 = new HashMap<>();

        // Step 1: Copy the segment from parent1 to the offspring
        for (int i = crossoverPoint1; i <= crossoverPoint2; i++) {
            offspring.set(i, parent1.get(i));
            mapping1.put(parent1.get(i), parent2.get(i));
            mapping2.put(parent2.get(i), parent1.get(i));
        }

        // Step 2: Resolve conflicts and fill the rest from parent2
        for (int i = 0; i < size; i++) {
            if (i >= crossoverPoint1 && i <= crossoverPoint2) {
                continue;
            }

            int gene = parent2.get(i);
            while (mapping1.containsKey(gene)) {
                gene = mapping1.get(gene);
            }
            offspring.set(i, gene);
        }

        // Step 3: Fill the missing elements from parent1
        for (int i = 0; i < size; i++) {
            if (offspring.get(i) == null) {
                offspring.set(i, parent1.get(i));
            }
        }

        return offspring;
    }
}
