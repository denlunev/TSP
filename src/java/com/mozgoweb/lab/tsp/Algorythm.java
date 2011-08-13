package com.mozgoweb.lab.tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class Algorythm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int populationSize = 20;
        int graphCount = 20;
        int bestSolutionThreshold = (int) (graphCount * 4);
        int maxGeneration = 2 * graphCount;
        String prefix = "Node";
        Random rnd = new Random();

        ArrayList<City> cities = new ArrayList<City>();
        Map<String, Integer> distances = new HashMap<String, Integer>();

        for (int i = 0; i < graphCount; i++) {
            City city = new City(prefix + i);
            for (int j = 0; j < graphCount; j++) {

                if (i != j) {
                    Integer dist;
                    dist = distances.get(prefix + i + "-" + prefix + j);

                    if (dist == null) {
                        dist = rnd.nextInt(100) + 1;
                        distances.put(prefix + i + "-" + prefix + j, dist);
                        distances.put(prefix + j + "-" + prefix + i, dist);
                    }
                    city.setDistance(prefix + j, dist);
                }
            }
            cities.add(city);
        }


        Crossover crossover = new Crossover(cities);
        crossover.initPopulation(populationSize);
        printPopulation(crossover.getPopulation());

        int generationCount = 0;
        while (crossover.getBestChrorChromosome().getCost() > bestSolutionThreshold
                && generationCount < maxGeneration) {

            System.out.println("Generation" + generationCount);

            crossover.nextGeneration(populationSize);
            printPopulation(crossover.getPopulation());
            System.out.println("****\n");

            generationCount++;
        }

        Chromosome solution = crossover.getBestChrorChromosome();
        ArrayList<String> solutions = new ArrayList<String>();

        for (int i = 0; i < solution.getCities().size(); i++) {

            String source = solution.getCities().get(i).getName();
            String destination = solution.getNextCity(solution.getCities().get(i)).getName();

            solutions.add(source + "-" + destination);
            solutions.add(destination + "-" + source);

            System.out.println(source + "-" + destination);
            System.out.println(destination + "-" + source);
        }
    }

    public static void printPopulation(List<Chromosome> population) {
        for (Chromosome chromosome : population) {
            System.out.println(chromosome);
        }
    }
}
