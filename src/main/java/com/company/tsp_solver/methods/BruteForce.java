package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utilities.TimeDistance;
import javafx.scene.shape.Line;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public @Data class BruteForce implements SolvingMethod {
    public final String name = "Brute Force Method";
    public BruteForce() {}
    public TimeDistance apply() {
        long start = System.currentTimeMillis();
        List<Point> points = Model.instance.points.stream().filter(point -> ! point.getPointPane().getDisableCheckBox().isSelected()).collect(Collectors.toList());
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
        return new TimeDistance(System.currentTimeMillis() - start, min);
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
/*        public double apply(){
        Point[] points = Model.instance.getPoints().toArray(new Point[0]);
        long start = System.currentTimeMillis();
        int permutationsSize = 1;
        for (int factor = 2; factor <= Model.instance.points.size() - 1; factor++) {
            permutationsSize *= factor;
        }
        Point[][] permutations = new Point[permutationsSize][];
        permute(permutations,points,0,points.length - 1);
        boolean isFirst = true;
        double min = 0;
        Point[] minWay = permutations[0];
            for (Point[] currentPermutation : permutations) {
                double distance = 0;
                for (int counter = 0; counter < currentPermutation.length; ++counter) {
                    distance += counter == currentPermutation.length - 1 ?
                            currentPermutation[counter].distance(currentPermutation[0]) :
                            currentPermutation[counter].distance(currentPermutation[counter + 1]);
                }
                if (isFirst) {
                    min = distance;
                    minWay = currentPermutation;
                    isFirst = false;
                    continue;
                }
                if (distance < min) {
                    min = distance;
                    minWay = currentPermutation;
                }
            }
        for (int counter = 0; counter < minWay.length; counter++) {
            Point p1 = minWay[counter];
            Point p2 = minWay[counter != minWay.length - 1 ? counter + 1 : 0];
            Line line = new Line(p1.getX(),p1.getY(),p2.getX(),p2.getY());
            Model.instance.lines.add(line);
            Model.instance.getController().mainField.getChildren().add(line);
        }
        System.out.println(System.currentTimeMillis() - start);
        return min;
    }

    private static void permute(Point[][] permutations,Point[] points,int start,int end) {
        if(start == end) {
            for(int i = 0; i < permutations.length; ++i) {
                if(permutations[i] == null) {
                    permutations[i] = Arrays.copyOf(points, points.length);
                    break;
                }
            }
        } else {
            for (int i = start; i <= end; ++i) {
                swap(points,start,i);
                permute(permutations,points,start + 1,end);
                swap(points,start,i);
            }
        }
    }
    private static void swap(Point[] list,int i,int j) {
        Point temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }*/
}
