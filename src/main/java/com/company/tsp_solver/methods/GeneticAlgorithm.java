package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.TimeDistance;
import com.company.tsp_solver.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm implements SolvingMethod{

    private int populationSize = 10;

    private double crossPart = 0.5;

    private List<List<Point>> population;

    private int genotypeSize;

    private List<Point> bestIndividual;

    @Override
    public String getName() {
        return "GeneticAlgorithm";
    }

    @Override
    public TimeDistance execute() {
        genotypeSize = Model.MODEL.points.size();
        return geneticAlgorithm();
    }

    private TimeDistance geneticAlgorithm() {
        population = initPopulation();
        population = populationCrossover(population);
        return null;
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

            resultPopulation.add(crossover(genotype1, genotype2));
        }
        return resultPopulation;
    }

    private List<Point> crossover(List<Point> genotype1, List<Point> genotype2) {

        List<List<Point>> tempList = new ArrayList<>(List.of(genotype1,genotype2));

        int position = Utils.random.nextInt(1,genotypeSize - 1);
        int firstGenotype = Utils.random.nextInt(0,2);
        int secondGenotype = (firstGenotype == 0)? 1: 0;
        List<Point> descendant = tempList.get(firstGenotype).subList(0,position);
        descendant.addAll(tempList.get(secondGenotype).subList(position, genotypeSize));
        return descendant;
    }

    private double appropriation(List<Point> gt) {
        double result = 0;

        return result;
    }

}
