package com.company.tsp_solver.utils;

import com.company.tsp_solver.point.Point;

import java.util.List;

public class DistanceCalculator {
    public double calculateDistance(List<Point> way) {
        double result = 0;
        for (int i = 0; i < way.size(); ++i) {
            if(i != way.size() - 1) {
                result += way.get(i).distance(way.get(i + 1));
            } else {
                result += way.get(i).distance(way.get(0));
            }
        }
        return result;
    }
}
