package com.company.tsp_solver.utils;

import com.company.tsp_solver.point.Point;

import java.util.List;

public class DistanceCalculator {
    public double calculateDistance(List<Point> way) {
        double result = 0;
        int size = way.size();
        for (int i = 0; i < size; ++i) {
            if(i != size - 1) {
                result += way.get(i).distance(way.get(i + 1));
            } else {
                result += way.get(i).distance(way.get(0));
            }
        }
        return result;
    }
}
