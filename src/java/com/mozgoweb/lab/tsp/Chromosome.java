package com.mozgoweb.lab.tsp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class Chromosome {

    private List<City> cities;

    public Chromosome() {
        cities = new ArrayList<City>();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getCost() {

        int cost = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            cost += cities.get(i).getDistance(cities.get(i + 1).getName());
        }
        
        cost += cities.get(cities.size() - 1).getDistance(cities.get(0).getName());
        
        return cost;
    }
    
    public City getNextCity(City currentCity){
        
        for (int i = 0; i < cities.size(); i++) {
            if(currentCity.getName().equals(cities.get(i).getName())){
               if(i + 1 < cities.size()){
                   return cities.get(i + 1); 
               }else{
                   return cities.get(0); 
               }
            }
        }
        
        return cities.get(0);
    }   
    
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       
        for (City city : cities) {
            sb.append(city.getName()).append(" ");
        }
        
        sb.append(getCost());
        
        return sb.toString();
    }
}
