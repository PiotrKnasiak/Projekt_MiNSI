import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final double A = 10;
    private static final double PC = 0.6;
    private static final double PM = 0.02;
    private static final int DIMENSION_2 = 2;
    private static final int DIMENSION_10 = 10;
    private static final double MIN_VAL = -5.21;
    private static final double MAX_VAL = 5.21;
    private static final int EVALS_2 = 1000;
    private static final int EVALS_10 = 10000;
    private static final boolean debug = false;

    private static Random rand = new Random();

    /**
     * Pojekdyńczy osobnik z populacji złożony z genów
     */
    static class Chromosome {
        double[] genes;
        double fitness;

        /**
         * Konstruktor generujący nowy {@code Chromosome}
         * 
         * @param dimension ilość genów w chromosomie (osobniku)
         */
        public Chromosome(int dimension) {
            genes = new double[dimension];
            for (int i = 0; i < dimension; i++) {
                genes[i] = MIN_VAL + (MAX_VAL - MIN_VAL) * rand.nextDouble();
            }
            evaluate();
        }

        /**
         * Oblicza wart. funkcji Rastrigina dla tego chromosomu
         * 
         * @return zwraca tablicę z dwoma {@code Chromosome} będącymi
         *         potomkami danych rodziców
         */
        public void evaluate() {
            fitness = rastrigin(genes);
        }

        /**
         * Oblicza wart. funkcji Rastrigina dla tego chromosomu
         * 
         * @return wartość fukcji Rastrigina dla danych genów
         */
        private double rastrigin(double[] x) {
            double sum = A * x.length;
            for (double xi : x) {
                sum += (xi * xi) - A * Math.cos(2 * Math.PI * xi);
            }
            return sum;
        }
    }

    /**
     * Cała populacja składająca się z osobników typu {@code Chromosome}
     */
    static class Population {
        Chromosome[] chroms;

        /**
         * Konstruktor generujący nową populację
         * 
         * @param size      ilość osobników w polulacji
         * @param dimension ilość genów w chromosomie (osobniku)
         */
        public Population(int size, int dimension) {
            chroms = new Chromosome[size];
            for (int i = 0; i < size; i++) {
                chroms[i] = new Chromosome(dimension);
            }
        }

        public int size() {
            return chroms.length;
        }

        /**
         * Wybiera na, zasadzie ruletki, odpowiedniego osobnika
         * 
         * @return wybrany {@code Chromosome}
         */
        public Chromosome select() {
            double totalFitness = Arrays.stream(chroms).mapToDouble(c -> 1 / c.fitness).sum();
            double roulette = rand.nextDouble() * totalFitness;
            double partialSum = 0;

            for (Chromosome chrom : chroms) {
                partialSum += 1 / chrom.fitness;
                if (partialSum >= roulette) {
                    return chrom;
                }
            }
            return chroms[chroms.length - 1];
        }

        public void evaluate() {
            for (Chromosome chrom : chroms) {
                chrom.evaluate();
            }
        }

        /**
         * Zwraca najlepiej przystosowany chromosom
         * 
         * @return wybrany {@code Chromosome}
         */
        public Chromosome getBestChromosome() {
            return Arrays.stream(chroms).min((c1, c2) -> Double.compare(c1.fitness, c2.fitness)).orElse(null);
        }

        /**
         * W pierwsze puste miescje w populacji wstawia dany chromosom
         * 
         * @param chrom {@code Chromosome} do wstawienia
         */
        public void addChromosome(Chromosome chrom) {
            for (int i = 0; i < chroms.length; i++) {
                if (chroms[i] == null) {
                    chroms[i] = chrom;
                    break;
                }
            }
        }

        /**
         * Zwraca tablicę {@code Chromosome}
         * 
         * @return wsystkie {@code Chromosome}
         */
        public Chromosome[] getChromosomes() {
            return chroms;
        }
    }

    /**
     * Funkcja krzyżowania chromosomów
     * 
     * @param parent1 pierwszy rodzic
     * @param parent2 drugi rodzic
     * @return zwraca tablicę z dwoma {@code Chromosome} będącymi
     *         potomkami danych rodziców
     */
    private static Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        int dimension = parent1.genes.length;
        long crossPts = 2;
        Chromosome[] children = new Chromosome[2];
        children[0] = new Chromosome(dimension);
        children[1] = new Chromosome(dimension);

        if (dimension == DIMENSION_10)
            crossPts = 8;

        int[] points = rand.ints(0, dimension).distinct().limit(crossPts).toArray();
        Arrays.sort(points);

        if (debug) {
            System.out.println("Pkt. krzyżowania : ");
            for (int pt : points)
                System.out.print(pt + ", ");
            System.out.println("\n");
        }

        boolean swap = false;
        int pointInd = 0;

        for (int i = 0; i < dimension; i++) {
            if (pointInd < crossPts && i == points[pointInd]) {
                swap = !swap;
                pointInd++;
            }
            if (swap) {
                children[0].genes[i] = parent2.genes[i];
                children[1].genes[i] = parent1.genes[i];
            } else {
                children[0].genes[i] = parent1.genes[i];
                children[1].genes[i] = parent2.genes[i];
            }
        }

        return children;
    }

    /**
     * Mutuje losowy chromosom
     * 
     * @param chrom chromosom któego gen zostanie zmieniony
     */
    private static void mutate(Chromosome chrom) {
        int geneInd = rand.nextInt(chrom.genes.length);
        chrom.genes[geneInd] = MIN_VAL + (MAX_VAL - MIN_VAL) * rand.nextDouble();
    }

    /**
     * Funkcja krzyżowania chromosomów
     * 
     * @param popSize   rozmiar populacji (ilośc chromosomów)
     * @param dimension ilość genów w chromosomie
     * @param maxEvals  ilość ewaluacji populacji
     * @return wygenerowana {@code Population}
     */
    public static double[][] AlgorytmGenetyczny(int popSize, int dimension, int maxEvals) {

        Population pop = new Population(popSize, dimension);
        int evals = 0;
        int indexRes = 0;
        double bestCurrFit = pop.getBestChromosome().fitness;

        while (evals < maxEvals) {
            evals += popSize;
        }

        // liczba ewolucji, aktualny fitness, najlepszy dotąd fitness (best global to
        // ostatni best fitness)
        double[][] results = new double[3][evals];

        evals = 0;
        indexRes = 0;

        while (evals < maxEvals) {
            pop.evaluate();
            evals += popSize;

            Population newPop = new Population(popSize, dimension);

            while (newPop.size() < popSize) {
                Chromosome parent1 = pop.select();
                Chromosome parent2 = pop.select();

                if (rand.nextDouble() <= PC) {
                    Chromosome[] children = crossover(parent1, parent2);
                    newPop.addChromosome(children[0]);
                    newPop.addChromosome(children[1]);
                } else {
                    newPop.addChromosome(parent1);
                    newPop.addChromosome(parent2);
                }
            }

            for (Chromosome chrom : newPop.getChromosomes()) {
                if (rand.nextDouble() <= PM) {
                    mutate(chrom);
                }
            }

            pop = newPop;

            for (Chromosome chr : pop.getChromosomes()) {
                results[0][indexRes] = chr.fitness;
                results[1][indexRes] = pop.getBestChromosome().fitness;
                if (results[1][indexRes] < bestCurrFit)
                    bestCurrFit = pop.getBestChromosome().fitness;
                results[2][indexRes++] = bestCurrFit;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        int dimension = DIMENSION_2;
        int maxEvals = EVALS_2;
        int popSize[] = { 20, 40, 60, 80, 100, 120, 140, 160, 180, 200 };

        if (dimension == DIMENSION_10) {
            maxEvals = EVALS_10;
        }
        // ile populacji, ile pól, ile osobników
        double[][][] res = new double[popSize.length][3][popSize[popSize.length - 1]];

        for (int i = 0; i < popSize.length; i++) {
            res[i] = AlgorytmGenetyczny(popSize[i], dimension, maxEvals);
        }

        /* */
        int i = 9;
        System.out.println("\nPopulacja " + i + " (" + popSize[i] + "):");
        for (int j = 0; j < res[0][0].length; j++) {

            for (int k = 0; k < res[0].length; k++) {

                System.out.print(res[i][k][j]);
                if (j == res[0][0].length - 1 && k == res[0].length - 1)
                    continue;
                System.out.print(", ");
            }
            System.out.println("");
        }

    }

}
