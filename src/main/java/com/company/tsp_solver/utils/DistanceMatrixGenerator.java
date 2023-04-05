package com.company.tsp_solver.utils;

import com.company.tsp_solver.point.Point;

import java.util.Arrays;
import java.util.List;

public class DistanceMatrixGenerator {

    public double[][] generateMatrix(List<Point> points) {

        double[][] distances = new double[points.size()][points.size()];

        for (int i = 0; i < distances.length; ++i) {

            for (int j = 0; j < distances.length; ++j) {
                distances[i][j] = points.get(i).distance(points.get(j));
            }
        }
        return distances;
    }
}
