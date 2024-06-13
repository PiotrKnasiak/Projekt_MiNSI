import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Repository {
    private static String projectPath = Paths.get("").toAbsolutePath().toString();
    private static final String folderPath = "data"; // Wybrany folder w projekcie
    private static final String fileName = "a280.tsp"; // Nazwa pliku

    /**
     * Wczytuje plik TSP
     *
     * @param fileName Nazwa pliku
     * @return Lista miast
     */
    public static List<City> loadTSPFile(String fileName) {
        if (projectPath.endsWith("\\src"))
            projectPath = Paths.get("").toAbsolutePath().getParent().toString();

        String filename = Paths.get(projectPath, folderPath, fileName).toString();
        List<City> cities = new ArrayList<>();
        boolean readingNodes = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("NODE_COORD_SECTION")) {
                    readingNodes = true;
                    continue;
                }
                if (line.startsWith("EOF")) {
                    break;
                }

                if (readingNodes) {
                    String[] parts = line.trim().split("\\s+");
                    int id = Integer.parseInt(parts[0]);
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    cities.add(new City(x, y, Integer.toString(id)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Zapisuje populację do pliku
     *
     * @param pop      populacja
     * @param fileName nazwa pliku
     */

    public static void SavePopulation(Population pop, String fileName) {
        if (projectPath.endsWith("\\src"))
            projectPath = Paths.get("").toAbsolutePath().getParent().toString();

        String outputFile = Paths.get(projectPath, folderPath, fileName + ".txt").toString();
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(outputFile));

            for (Individual i : pop.individuals) {
                String temp = "";

                // 1. liczba miast
                temp += i.listOfCities.size() + ";";

                // 2. miasta
                for (int c : i.listOfCities) {
                    temp += c + ";";
                }

                // 3. suma odległości
                temp += i.sumDistance() + ";";

                // 4. Odległości między miastami
                if (!i.listOfDistances.isEmpty()) {

                    for (int d : i.listOfDistances) {
                        temp += d + ";";
                    }
                }

                // 5. Odległości między miastami przebyta droga
                for (int d : i.GetListOfSumDistances()) {
                    temp += d + ";";
                }

                // 6. zapisz wszystko w jednym wierszu i przejdz do nastepnego
                writer.write(temp);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Zapisuje wyniki oddzielając je średnikiem ( ; ). Wszystkie wyniki są w jednej
     * linii
     * 
     * @param numOfCities Ilość miast zapisywanego wyniku
     * @param ListOfBest  Lista {@code List<Integer>} najlepszych wyników
     * @param isLocal     Czy podane wyniki są lokalne, czy nie (globalne)
     */
    public static void SaveResults(int numOfCities, List<Integer> ListOfBest, boolean isLocal) {
        if (projectPath.endsWith("\\src"))
            projectPath = Paths.get("").toAbsolutePath().getParent().toString();

        String addition = isLocal ? "Local" : "Global";

        String outputFile = Paths.get(projectPath, folderPath, "Wyniki" + ListOfBest.size() + addition + ".txt")
                .toString();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (Integer i : ListOfBest) {
                writer.write(i + ";");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
