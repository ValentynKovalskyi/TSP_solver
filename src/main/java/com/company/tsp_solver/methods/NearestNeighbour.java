package com.company.tsp_solver.methods;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.point.PointPane;
import com.company.tsp_solver.utils.TimeDistance;
import com.company.tsp_solver.utils.Utils;
import com.company.tsp_solver.utils.WayDrawer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighbour implements SolvingMethod {

    public final String name = "Nearest Neighbour Method";

    private final WayDrawer drawer = new WayDrawer();

    @Override
    public String getName() {
        return name;
    }

    public NearestNeighbour() {}

    public TimeDistance execute() {
        long startTime = System.currentTimeMillis();
        Map<Point,Boolean> pointVisiting = new HashMap<>();
        List<Point> points = Model.instance.points.stream().filter(point -> ! point.getPointPane().getDisableCheckBox().isSelected()).toList();
        points.forEach((point) -> pointVisiting.put(point,false));
        double result = 0;
        Point currentPoint;
        if(PointPane.isStart) {
            currentPoint = Model.instance.points.stream().filter(point -> point.getPointPane().getStartRButton().isSelected()).findAny().get();
        } else {
            currentPoint = points.get( Utils.random.nextInt(points.size()));
        }
        Point startPoint = currentPoint;
        pointVisiting.put(currentPoint,true);
        do {
            double minWay = 0;
            boolean isFirst = true;
            Point minWayPoint = null;
            for (Point point: points) {
                if(pointVisiting.get(point) || currentPoint == point) continue;
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
            drawer.drawLine(currentPoint.getX(),currentPoint.getY(),minWayPoint.getX(),minWayPoint.getY());
            currentPoint = minWayPoint;
            pointVisiting.put(currentPoint,true);
        } while (pointVisiting.containsValue(false));

        drawer.drawLine(currentPoint.getX(),currentPoint.getY(),startPoint.getX(),startPoint.getY());
        result += currentPoint.distance(startPoint);
        return new TimeDistance(System.currentTimeMillis() - startTime, result);
    }
}
