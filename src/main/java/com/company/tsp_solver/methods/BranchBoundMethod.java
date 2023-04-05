package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.DistanceCalculator;
import com.company.tsp_solver.utils.TimeDistance;
import com.company.tsp_solver.utils.Utils;
import com.company.tsp_solver.utils.WayDrawer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BranchBoundMethod implements SolvingMethod{

    private List<Point> unvisited = new LinkedList<>();

    private Point startPoint;

    private WayDrawer drawer = new WayDrawer();

    private DistanceCalculator calculator = new DistanceCalculator();
    @Override
    public String getName() {
        return "Branches and Bounds Method";
    }

    @Override
    public TimeDistance execute() {
        unvisited = Model.MODEL.points;
        return null;
    }

    private TimeDistance launch() {
        startPoint = unvisited.get(Utils.random.nextInt(unvisited.size()));
        List<Point> resultWay = new LinkedList<>(List.of(startPoint));
        unvisited.remove(startPoint);
        getMinWay(startPoint,resultWay, unvisited);
        return null;
    }

    private List<Point> getMinWay(Point startPoint,List<Point> result, List<Point> unvisited) {
        if(unvisited.isEmpty()) {
            return result;
        }
        Map<Point,Double> lowerBounds = new HashMap<>();

        unvisited.forEach(point -> {
            List<Point> unvisitedPoints = new LinkedList<>(List.copyOf(unvisited));
            unvisitedPoints.remove(point);
           lowerBounds.put(point,lowerBound(point, unvisitedPoints,calculator.calculateLineLength(result)));
        });
        Point minLBPoint = lowerBounds.entrySet().stream().min((e1,e2) -> (int) (e1.getValue() - e2.getValue())).get().getKey();
        result.add(minLBPoint);
        unvisited.remove(minLBPoint);
        return getMinWay(minLBPoint,result,unvisited);
    }

    private double lowerBound(Point startPoint, List<Point> points, double curLength) {
        double result = curLength;

        return 0;
    }

    private

}
