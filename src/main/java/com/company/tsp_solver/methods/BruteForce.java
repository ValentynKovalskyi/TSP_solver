package com.company.tsp_solver.methods;

import com.company.tsp_solver.models.Point;
import javafx.scene.shape.Line;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.company.tsp_solver.models.Model.MODEL;

@RequiredArgsConstructor
public class BruteForce implements SolvingMethod {
    private final List<Point> points;

    @Override
    public String getName() {
        return "Brute Force Method";
    }

    public double apply() {
        List<List<Point>> permutations = new ArrayList<>();
        permute(permutations, points, 0, points.size() - 1);
        System.out.println(3);
        boolean isFirst = true;
        double min = 0;
        List<Point> minWay = permutations.get(0);

        for (var permutation : permutations) {
            double distance = 0;

            for (int counter = 0; counter < permutation.size(); counter++)
                distance += permutation.get(counter).distance(permutation.get(
                        counter != permutation.size() - 1 ? counter + 1 : 0
                ));

            if (isFirst) {
                min = distance;
                minWay = permutation;
                isFirst = false;
                continue;
            }
            if (distance < min) {
                min = distance;
                minWay = permutation;
            }
        }

        for (int counter = 0; counter < minWay.size(); counter++) {
            var p1 = minWay.get(counter);
            var p2 = minWay.get(counter != minWay.size() - 1 ? counter + 1 : 0);

            MODEL.controller.mainField.getChildren().add(
                    new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY())
            );
        }

        return 0; // should we really be returning 0 here?
    }

    private void permute(List<List<Point>> permutations,List<Point> points, int start, int end) {
        if (start == end)
            permutations.add(List.copyOf(points));
        else
            for (int i = start; i <= end; ++i) {
                swap(points, start, i);
                permute(permutations, points, start + 1, end);
                swap(points, start, i);
            }
    }

    private void swap(List<Point> list, int i, int j) {
        Point temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
