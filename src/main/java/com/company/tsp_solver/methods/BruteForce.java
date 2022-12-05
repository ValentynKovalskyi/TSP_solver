package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.Point;
import javafx.scene.shape.Line;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public @Data class BruteForce implements SolvingMethod {
    public final String name = "Brute Force Method";
    private List<Point> points;
    public BruteForce(List<Point> points) {
        this.points = points;
    }
    public double apply() {
        long start = System.currentTimeMillis();
        List<List<Point>> permutations = new ArrayList<>();
        permute(permutations,points,0,points.size() - 1);
        Iterator<List<Point>> it = permutations.iterator();
        boolean isFirst = true;
        double min = 0;
        List<Point> minWay = permutations.get(0);
        while (it.hasNext()){
            List<Point> currentPermutation = it.next();
            double distance = 0;
            for (int counter = 0; counter < currentPermutation.size(); counter++) {
                distance += counter == currentPermutation.size() - 1 ?
                        currentPermutation.get(counter).distance(currentPermutation.get(0)):
                        currentPermutation.get(counter).distance(currentPermutation.get(counter + 1));
            }
            if (isFirst) {
                min = distance;
                minWay = currentPermutation;
                isFirst = false;
                continue;
            }
            if(distance < min) {
                min = distance;
                minWay = currentPermutation;
            }
        }
        for (int counter = 0; counter < minWay.size(); counter++) {
                Point p1 = minWay.get(counter);
                Point p2 = minWay.get(counter != minWay.size() - 1 ? counter + 1 : 0);
                Line line = new Line(p1.getX(),p1.getY(),p2.getX(),p2.getY());
                Model.instance.lines.add(line);
                Model.instance.getController().mainField.getChildren().add(line);
        }
        Arrays.sort(new int[6]);
        System.out.println(System.currentTimeMillis() - start);
        return min;
    }

    private static void permute(List<List<Point>> permutations,List<Point> points,int start,int end) {
        if(start == end) {
            permutations.add(List.copyOf(points));
        } else {
            for (int i = start; i <= end; ++i) {
                swap(points,start,i);
                permute(permutations,points,start + 1,end);
                swap(points,start,i);
            }
        }
    }
    private static void swap(List<Point> list,int i,int j) {
        Point temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }
}
