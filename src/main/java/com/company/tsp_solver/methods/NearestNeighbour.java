package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.Point;
import com.company.tsp_solver.PointPane;
import com.company.tsp_solver.Utilities;
import javafx.scene.shape.Line;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public @Data class NearestNeighbour implements SolvingMethod {
    public final String name = "Nearest Neighbour Method";
    private List<Point> points;
    public NearestNeighbour(List<Point> points) {
        this.points = points;
    }
    public double apply() {
        Map<Point,Boolean> pointMap = new HashMap<>();

        points.forEach((point) -> pointMap.put(point,false));
        double result = 0;
        Point currentPoint;
        if(PointPane.isStart) {
            currentPoint = Model.instance.points.stream().filter(point -> Model.instance.pointPanes.get(point).getStartRButton().isSelected()).findAny().get();
        } else {
            currentPoint = points.get( Utilities.random.nextInt(points.size()));
        }
        Point startPoint = currentPoint;
        pointMap.put(currentPoint,true);
        do {
            double minWay = 0;
            boolean isFirst = true;
            Point minWayPoint = new Point(0,0);
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
            Model.instance.getController().mainField.getChildren().add(wayView);
            Model.instance.lines.add(wayView);
            currentPoint = minWayPoint;
            pointMap.put(currentPoint,true);
        } while (pointMap.containsValue(false));

        Line wayView = new Line(currentPoint.getX(),currentPoint.getY(),startPoint.getX(),startPoint.getY());
        Model.instance.getController().mainField.getChildren().add(wayView);
        Model.instance.lines.add(wayView);
        result += currentPoint.distance(startPoint);
        return result;
    }
}
