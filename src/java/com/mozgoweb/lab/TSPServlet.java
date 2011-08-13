package com.mozgoweb.lab;

import com.mozgoweb.lab.tsp.Chromosome;
import com.mozgoweb.lab.tsp.City;
import com.mozgoweb.lab.tsp.Crossover;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Denis Lunev <den@mozgoweb.com>
 */
public class TSPServlet extends HttpServlet {

    private int populationSize = 20;
    private int graphCount = 4;
    private final static int MIN_GRAPH_COUNT = 3;
    private final static int MAX_GRAPH_COUNT = 30;
    private final static int OPTIMAL_GRAPH_COUNT = 7;
    private int bestSolutionThreshold = (int) (graphCount * 4);
    private int maxGeneration = 2 * graphCount;
    private String prefix = "City";
    private Random rnd = new Random();
    private Crossover crossover;
    private StringBuilder log;

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("count") != null
                && !request.getParameter("count").equals("")) {

            String param = request.getParameter("count");

            try {
                graphCount = Integer.valueOf(param);
                if (graphCount < MIN_GRAPH_COUNT || graphCount > MAX_GRAPH_COUNT) {
                    graphCount = OPTIMAL_GRAPH_COUNT;
                }

            } catch (Exception ex) {
                graphCount = OPTIMAL_GRAPH_COUNT;
            }
        }

        maxGeneration = 2 * graphCount;

        log = new StringBuilder();

        ArrayList<City> cities = initGraph();
        ArrayList<String> solution = findSolution(cities);
        try {
            response.getWriter().print(collectJson(cities, solution));
        } catch (JSONException ex) {
            Logger.getLogger(TSPServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private ArrayList<City> initGraph() {

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

        return cities;
    }

    private String collectJson(ArrayList<City> cities, ArrayList<String> solution) throws JSONException {
        JSONArray edges = new JSONArray();
        JSONArray nodes = new JSONArray();

        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                boolean isSolution = solution.contains(cities.get(i) + "-" + cities.get(j));
                
                JSONObject edge = new JSONObject();
                edge.put("source", cities.get(i));
                edge.put("target", cities.get(j));
                edge.put("is_solution", isSolution);
                edge.put("weight", cities.get(i).getDistance(cities.get(j).getName()));
                
                edges.put(edge);
            }
        }

        return new JSONObject().put("edges", edges)
                .put("log", log).toString();
    }

    private ArrayList<String> findSolution(ArrayList<City> cities) {

        log = new StringBuilder();

        crossover = new Crossover(cities);
        crossover.initPopulation(populationSize);

        log.append("Init Population").append("<br/>");
        crossover.nextGeneration(populationSize);

        for (Chromosome chromosome : crossover.getPopulation()) {
            log.append(chromosome).append("<br/>");
        }
        log.append("****************").append("<br/><br/>");

        int generationCount = 0;
        while (crossover.getBestChrorChromosome().getCost() > bestSolutionThreshold
                && generationCount < maxGeneration) {

            log.append("Generation ").append(generationCount).append("<br/>");
            crossover.nextGeneration(populationSize);

            for (Chromosome chromosome : crossover.getPopulation()) {
                log.append(chromosome).append("<br/>");
            }
            log.append("****************").append("<br/><br/>");
            generationCount++;
        }

        Chromosome solution = crossover.getBestChrorChromosome();

        log.append("Best solution: ").append("<br/>");
        log.append(solution);

        ArrayList<String> solutions = new ArrayList<String>();

        for (int i = 0; i < solution.getCities().size(); i++) {
            String source = solution.getCities().get(i).getName();
            String destination = solution.getNextCity(solution.getCities().get(i)).getName();
            solutions.add(source + "-" + destination);
            solutions.add(destination + "-" + source);
        }

        return solutions;
    }
}
