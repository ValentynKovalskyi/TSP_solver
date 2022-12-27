package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.point.PointPane;
import com.company.tsp_solver.utilities.TimeDistance;
import com.company.tsp_solver.utilities.Utilities;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighbour implements SolvingMethod {
    public final String name = "Nearest Neighbour Method";
    @Override
    public String getName() {
        return name;
    }
    public NearestNeighbour() {}
    public TimeDistance apply() {
        long start = System.currentTimeMillis();
        Map<Point,Boolean> pointMap = new HashMap<>();
        List<Point> points = Model.instance.points.stream().filter(point -> ! point.getPointPane().getDisableCheckBox().isSelected()).toList();
        points.forEach((point) -> pointMap.put(point,false));
        double result = 0;
        Point currentPoint;
        if(PointPane.isStart) {
            currentPoint = Model.instance.points.stream().filter(point -> point.getPointPane().getStartRButton().isSelected()).findAny().get();
        } else {
            currentPoint = points.get( Utilities.random.nextInt(points.size()));
        }
        Point startPoint = currentPoint;
        pointMap.put(currentPoint,true);
        do {
            double minWay = 0;
            boolean isFirst = true;
            Point minWayPoint = null;
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
            assert minWayPoint != null;
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
        return new TimeDistance(System.currentTimeMillis() - start, result);
    }
}
