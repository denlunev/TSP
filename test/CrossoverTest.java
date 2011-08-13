import com.mozgoweb.lab.tsp.Chromosome;
import com.mozgoweb.lab.tsp.City;
import java.util.ArrayList;
import com.mozgoweb.lab.tsp.Crossover;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class CrossoverTest {
    
    private ArrayList<City> cities = new ArrayList<City>();
    
    
    @Before
    public void initCities(){
        
        City aCity = new City("A");
        City bCity = new City("B");
        City cCity = new City("C");
        City dCity = new City("D");
        City eCity = new City("E");
        
        
        aCity.setDistance("B", 37);
        aCity.setDistance("C", 17);
        aCity.setDistance("D", 42);
        aCity.setDistance("E", 25);
        
        bCity.setDistance("A", 37);
        bCity.setDistance("C", 13);
        bCity.setDistance("D", 14);
        bCity.setDistance("E", 17);
        
        cCity.setDistance("A", 17);
        cCity.setDistance("B", 13);
        cCity.setDistance("D", 16);
        cCity.setDistance("E", 21);
        
        dCity.setDistance("A", 42);
        dCity.setDistance("B", 14);
        dCity.setDistance("C", 16);
        dCity.setDistance("E", 10);
        
        eCity.setDistance("A", 25);
        eCity.setDistance("B", 17);
        eCity.setDistance("C", 21);
        eCity.setDistance("D", 10);
        
        cities.add(aCity);
        cities.add(bCity);
        cities.add(cCity);
        cities.add(dCity);
        cities.add(eCity);
    }
    
    
    @Test
    public void crossoverTest() {
        Crossover crossover = new Crossover(cities);
        crossover.initPopulation(20);
        crossover.nextGeneration(20);
        
        Chromosome bestChromosome = crossover.getBestChrorChromosome();
        
        Assert.assertEquals(79, bestChromosome.getCost());
    }
}
