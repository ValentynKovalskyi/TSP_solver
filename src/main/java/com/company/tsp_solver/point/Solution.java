package com.company.tsp_solver.point;

import com.company.tsp_solver.utils.DistanceCalculator;

import java.util.List;

public class Solution {

    private List<Point> way;

    private double distance;

    private long time;

    public Solution(List<Point> way, long time) {
        DistanceCalculator calculator = new DistanceCalculator();
        this.way = way;
        distance = calculator.calculateDistance(way);
    }
}
