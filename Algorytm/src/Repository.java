import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class Repository {
    private static final String projectPath = Paths.get("").toAbsolutePath().toString();
    private static final String folderPath = "data"; // Wybrany folder w projekcie
    private static final String fileName = "a280.tsp"; // Nazwa pliku

    public static List<City> loadTSPFile() {
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
    public static void SavePopulation (Population pop)
    {
        //TODO: Implement
    }
}
