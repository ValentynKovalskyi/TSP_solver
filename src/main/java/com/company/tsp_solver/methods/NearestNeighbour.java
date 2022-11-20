package com.company.tsp_solver.methods;

import com.company.tsp_solver.models.Model;
import com.company.tsp_solver.models.Point;
import com.company.tsp_solver.Utilities;
import javafx.scene.shape.Line;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NearestNeighbour implements SolvingMethod {
    private final List<Point> points;

    @Override
    public String getName() {
        return "Nearest Neighbour Method";
    }

    public double apply() {
        Map<Point, Boolean> pointMap = new HashMap<>();
        int beginPoint = Utilities.random.nextInt(points.size());
        points.forEach((point) -> pointMap.put(point,false));
        double result = 0;
        Point currentPoint = points.get(beginPoint);
        pointMap.put(currentPoint,true);

        do {
            double minWay = 0;
            boolean isFirst = true;
            Point minWayPoint = new Point(2,3);
            for (Point point: points) {
                if(pointMap.get(point) || currentPoint == point) continue;
                double distance = currentPoint.distance(point);
                if(isFirst) {
                    isFirst = false;
                    minWay = distance;
                    minWayPoint = point;
                    continue;
                }
                if(distance < minWay) {
                    minWay = distance;
                    minWayPoint = point;
                }
                }
            result += currentPoint.distance(minWayPoint);
            Line wayView = new Line(currentPoint.getX(),currentPoint.getY(),minWayPoint.getX(),minWayPoint.getY());
            Model.MODEL.controller.mainField.getChildren().add(wayView);
            Model.MODEL.lines.add(wayView);
            currentPoint = minWayPoint;
            pointMap.put(currentPoint,true);
        } while (pointMap.containsValue(false));

        Line wayView = new Line(currentPoint.getX(),currentPoint.getY(),points.get(beginPoint).getX(),points.get(beginPoint).getY());
        Model.MODEL.controller.mainField.getChildren().add(wayView);
        Model.MODEL.lines.add(wayView);

        return result + currentPoint.distance(points.get(beginPoint));
    }
}
