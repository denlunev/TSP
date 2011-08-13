package com.mozgoweb.lab.tsp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class City {
    
    private String name;
    private Map<String, Integer> distanceMap = new HashMap<String, Integer>();

    public City(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance(String cityName) {
        if(!cityName.equals(name)){
            return distanceMap.get(cityName);
        }
        
        return 0;
    }

    public void setDistance(String cityName, int distance) {
        if(cityName != name){
            distanceMap.put(cityName, distance);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
