package com.company.tsp_solver.algorithms;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import javafx.scene.shape.Line;

import java.util.*;
@Deprecated
public class KruskalsAlgorithm {
    public static long apply(List<Point> pointsList) {
        long start = System.currentTimeMillis();
        int n = pointsList.size();
        Point[] points = new Point[n];
        pointsList.toArray(points);
        Point[][] edges = new Point[(n * (n - 1)) / 2][];
        List<List<Point>> trees = new ArrayList<>();
        Double[] distances = new Double[(n * (n - 1)) / 2];
        int index = 0;
        for (Point point: points) {
            for (Point point1: points){
                if(point1 == point || Arrays.stream(edges).anyMatch(edge -> edge != null && edge[0] == point1 && edge[1] == point)) continue;
                edges[index] = new Point[]{point, point1};
                distances[index] = point.distance(point1);
                ++index;
            }
        }
        while(Arrays.stream(edges).anyMatch(Objects::nonNull)){
            int minValueIndex = 0;
            for (int i = 1; i < index; i++) {
                if(distances[i] != null &&
                        (distances[minValueIndex] == null
                                || distances[i] < distances[minValueIndex])) minValueIndex = i;
            }
            Point point = edges[minValueIndex][0];
            Point point1 = edges[minValueIndex][1];
            List<List<Point>> containingTrees = trees.stream().filter(tree -> tree.contains(point) || tree.contains(point1)).toList();
            if (containingTrees.size() == 0) {
                List<Point> newEdge = new ArrayList<>();
                newEdge.add(point);
                newEdge.add(point1);
                trees.add(newEdge);
            } else if (containingTrees.size() == 1){
                if(containingTrees.get(0).contains(point) && containingTrees.get(0).contains(point1))
                {
                    distances[minValueIndex] = null;
                    edges[minValueIndex] = null;
                    continue;
                } else if(containingTrees.get(0).contains(point)) {
                    containingTrees.get(0).add(point1);
                } else containingTrees.get(0).add(point);
            } else {
                List<Point> generalList = trees.stream().filter(tree -> tree.contains(point)).findFirst().get();
                List<Point> listToReduce = trees.stream().filter(tree -> tree.contains(point1)).findFirst().get();
                trees.remove(listToReduce);
                generalList.addAll(listToReduce);
            }
            Line currentLine = new Line(edges[minValueIndex][0].getX(),edges[minValueIndex][0].getY(),edges[minValueIndex][1].getX(),edges[minValueIndex][1].getY());
            Model.MODEL.getController().mainField.getChildren().add(currentLine);
            Model.MODEL.lines.add(currentLine);
            distances[minValueIndex] = null;
            edges[minValueIndex] = null;

        }
        return System.currentTimeMillis() - start;
    }
}
