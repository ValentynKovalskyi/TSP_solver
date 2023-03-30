package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.DistanceCalculator;
import com.company.tsp_solver.utils.TimeDistance;
import com.company.tsp_solver.utils.Utils;
import com.company.tsp_solver.utils.WayDrawer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm implements SolvingMethod{

    private int populationSize = 10;

    private double crossPart = 0.5;

    private List<List<Point>> population;

    private int genotypeSize;

    private List<Point> bestIndividual;

    private DistanceCalculator calculator = new DistanceCalculator();

    @Override
    public String getName() {
        return "GeneticAlgorithm";
    }

    @Override
    public TimeDistance execute() {
        genotypeSize = Model.MODEL.points.size();
        geneticAlgorithm();
        return new TimeDistance(1,1);
    }

    private void geneticAlgorithm() {
        population = initPopulation();
        bestIndividual = population.get(0);
        for (int i = 0; i < 10000; ++i){
            population = populationCrossover(population);
            population = selection(population);
            if(calculator.calculateDistance(bestIndividual) < calculator.calculateDistance(population.get(0))) {
                bestIndividual = population.get(0);
                i = 0;
            }
        }
        new WayDrawer().drawWay(population.get(populationSize - 1));
    }

    private List<List<Point>> initPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i) {
            List<Point> genotype = new LinkedList<>();
            List<Point> genoPool = new LinkedList<>(Model.MODEL.points);
            while (!genoPool.isEmpty()) {
                Point gene = genoPool.get(Utils.random.nextInt(genoPool.size()));
                genoPool.remove(gene);
                genotype.add(gene);
            }
            population.add(genotype);
        }
        return population;
    }

    private List<List<Point>> populationCrossover(List<List<Point>> population) {

        List<List<Point>> resultPopulation = new LinkedList<>(population);

        List<List<Point>> crossPops = new ArrayList<>();

        for (int i = 0; i < population.size() * crossPart; ++i) {
            crossPops.add(population.get(Utils.random.nextInt(populationSize)));
        }

        while (!(crossPops.size() < 2)) {
            List<Point> genotype1 = crossPops.get(Utils.random.nextInt(crossPops.size()));
            crossPops.remove(genotype1);
            List<Point> genotype2 = crossPops.get(Utils.random.nextInt(crossPops.size()));
            crossPops.remove(genotype2);
            List<Point> descendant = crossover(genotype1, genotype2);
            correctGt(descendant);
            resultPopulation.add(descendant);
        }
        return resultPopulation;
    }

    private void correctGt(List<Point> gt) {
        //replace same points with unvisited ones
        for (int i = 0; i < genotypeSize; i++) {
            for (int j = 0; j < genotypeSize; j++) {
                if(i == j) continue;
                if(gt.get(i) == gt.get(j)) {
                    gt.set(j, Model.MODEL.points.stream().filter(point -> !gt.contains(point)).findFirst().get());
                }
            }
        }
    }

    private List<Point> crossover(List<Point> genotype1, List<Point> genotype2) {

        List<List<Point>> tempList = new ArrayList<>(List.of(genotype1,genotype2));

        int firstGenotype = Utils.random.nextInt(0,2);
        int secondGenotype = (firstGenotype == 0)? 1: 0;
        int position1 = Utils.random.nextInt(genotypeSize);
        int position2 = Utils.random.nextInt(genotypeSize);
        Point gene = tempList.get(firstGenotype).get(position1);

        List<Point> descendant = tempList.get(secondGenotype);
        descendant = new ArrayList<>(descendant);
        descendant.set(position2,gene);
        return descendant;
    }

    private List<List<Point>> selection(List<List<Point>> population) {
        List<List<Point>> result = new LinkedList<>(population);
        result.sort(Comparator.comparingDouble(this::appropriation));
        result = result.subList(0,populationSize);
        return result;
    }

    public List<List<Point>> mutation(List<List<Point>> population) {

        List<List<Point>> resultPopulation = new LinkedList<>(population);

        List<List<Point>> mutationPops = new ArrayList<>();

        for (int i = 0; i < population.size() * crossPart; ++i) {
            mutationPops.add(population.get(Utils.random.nextInt(populationSize)));
        }

        while (!mutationPops.isEmpty()) {
            List<Point> genotype = mutationPops.get(Utils.random.nextInt(mutationPops.size()));
            mutationPops.remove(genotype);
            int position1 = Utils.random.nextInt(genotypeSize);
            int position2 = Utils.random.nextInt(genotypeSize);
            Point gene = genotype.get(position1);
            genotype.set(position1,genotype.get(position2));
            genotype.set(position2,gene);
            resultPopulation.add(genotype);
        }
        return resultPopulation;
    }

    private double appropriation(List<Point> gt) {
        double result = 0;
        result += calculator.calculateDistance(gt);
        return result;
    }



}
