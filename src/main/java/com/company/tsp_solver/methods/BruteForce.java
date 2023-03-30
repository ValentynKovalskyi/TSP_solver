package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.DistanceCalculator;
import com.company.tsp_solver.utils.TimeDistance;
import com.company.tsp_solver.utils.WayDrawer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public @Data class BruteForce implements SolvingMethod {
    public final String name = "Brute Force Method";
    public BruteForce() {}
    public TimeDistance execute() {
        long start = System.currentTimeMillis();
        List<Point> points = Model.MODEL.points;
        List<List<Point>> permutations = new ArrayList<>();
        DistanceCalculator calculator = new DistanceCalculator();
        WayDrawer drawer = new WayDrawer();
        permute(permutations,points,0,points.size() - 1);
        Iterator<List<Point>> it = permutations.iterator();
        boolean isFirst = true;
        List<Point> minWay = permutations.get(0);
        while (it.hasNext()){
            List<Point> currentWay = it.next();
            if (calculator.calculateDistance(currentWay) < calculator.calculateDistance(minWay)) minWay = currentWay;
        }
        drawer.drawWay(minWay);
        return new TimeDistance(System.currentTimeMillis() - start, calculator.calculateDistance(minWay));
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
