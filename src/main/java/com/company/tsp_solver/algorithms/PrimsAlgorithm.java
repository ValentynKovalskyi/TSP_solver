package com.company.tsp_solver.algorithms;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.Point;
import com.company.tsp_solver.Utilities;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrimsAlgorithm {
    public static long apply(List<Point> pointsList) {
        long start = System.currentTimeMillis();
        Point[] points = new Point[pointsList.size()];
        Point[] visitedPoints = new Point[pointsList.size()];
        pointsList.toArray(points);
        int index = 1;
        visitedPoints[0] = points[Utilities.random.nextInt(points.length)];

        while(Arrays.stream(visitedPoints).anyMatch(Objects::isNull)) {
            double minWay = 0;
            Point[] edge = new Point[2];
            boolean isFirst = true;
            for (Point currentPoint: visitedPoints) {
                if(currentPoint == null) continue;
                for (Point point: points) {
                    if(Arrays.stream(visitedPoints).anyMatch(point1 -> point1 == point)) continue;
                    double distance = currentPoint.distance(point);
                    if(isFirst) {
                        minWay = distance;
                        edge[0] = currentPoint;
                        edge[1] = point;
                        isFirst = false;
                        continue;
                    }
                    if(distance < minWay) {
                        minWay = distance;
                        edge[0] = currentPoint;
                        edge[1] = point;
                    }
                }
            }
            Line currentLine = new Line(edge[0].getX(),edge[0].getY(),edge[1].getX(),edge[1].getY());
            Model.instance.getController().mainField.getChildren().add(currentLine);
            Model.instance.lines.add(currentLine);
            visitedPoints[index++] = edge[1];
        }
        return System.currentTimeMillis() - start;
    }
}
