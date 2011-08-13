package com.mozgoweb.lab.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class Crossover {

    private ArrayList<City> cities;
    private Random rnd;
    private ArrayList<Chromosome> population;
    private double mutationChance = 0.2;
    
    public Crossover(ArrayList<City> cities) {
        this.cities = cities;
        this.population = new ArrayList<Chromosome>();
        rnd = new Random();
    }
    
    public List<Chromosome> nextGeneration(int populationSize) {
        
        if(population.isEmpty()){
            initPopulation(populationSize);
        }
        
        int half = (int) populationSize / 2;
        
        //Remove worst part of population
        for (int i = half; i < population.size(); i++) {
            population.remove(i);
        }
        
        while(population.size() != populationSize){
            
            Chromosome parent1 = population.get(rnd.nextInt(half));
            Chromosome parent2 = population.get(rnd.nextInt(half));
            
            Chromosome child = makeChild(parent1, parent2);
            if(Math.random() < mutationChance){
                child = mutate(child);
            }
            population.add(child);
        }
        
        sortPopulation();
        
        return population;
    }
    
    public void initPopulation(int populationSize){
        
        for (int i = 0; i < populationSize; i++) {
            population.add(newChromosome());
        }
        
        sortPopulation();
    }
    
    private Chromosome newChromosome(){
        
        Chromosome chromosome = new Chromosome();
        chromosome.setCities((ArrayList)cities.clone());
        Collections.shuffle(chromosome.getCities());
        
        return chromosome;
    }
    
    private void sortPopulation(){
        
        Collections.sort(population, new Comparator<Chromosome>() {
        @Override
        public int compare(Chromosome c1, Chromosome c2) {
            int cost1 = c1.getCost();
            int cost2 = c2.getCost();
             
            return (cost1 < cost2 ? -1 : (cost1 > cost2 ? 1 : 0));
         }
      });
    }
    
    public Chromosome mutate(Chromosome chromosome){
        
        int i = 0;
        int j = 0;
        
        while(i == j){
            i = rnd.nextInt(cities.size() - 1);
            j = rnd.nextInt(cities.size() - 1);
            
            City buff = chromosome.getCities().get(i);
            chromosome.getCities().set(i, chromosome.getCities().get(j));
            chromosome.getCities().set(j, buff);
        }
        
        return chromosome;
    }
    
    private Chromosome makeChild(Chromosome parent1, Chromosome parent2){
        
        Chromosome child = new Chromosome();
        
        LinkedList<City> visited = new LinkedList<City>();
        LinkedList<City> notVisited = new LinkedList<City>();
        
        visited.add(parent1.getCities().get(0));
        for (int i = 1; i < parent1.getCities().size(); i++) {
            notVisited.add(parent1.getCities().get(i));
        }
        
        while(notVisited.size() > 0){
            
           City current = visited.getLast();
           City next1 = parent1.getNextCity(current);
           City next2 = parent2.getNextCity(current);
           City cityToAdd;
           City reserveCity;
           
           if(next1.getDistance(current.getName()) < next2.getDistance(current.getName())) {
               cityToAdd = next1;
               reserveCity = next2;
           }else{
               cityToAdd = next2;
               reserveCity = next1;
           }
           
           if(visited.contains(cityToAdd)) {
               
               if(!visited.contains(reserveCity)){
                   cityToAdd = reserveCity;
               }else{
                   cityToAdd = notVisited.getFirst();
               }
           }
           
           visited.add(cityToAdd);
           notVisited.remove(cityToAdd);
        }
        
        child.setCities(visited);
        return child;
    }

    public ArrayList<Chromosome> getPopulation() {
        return population;
    }
    
    public Chromosome getBestChrorChromosome(){
        return population.get(0);
    }
}
