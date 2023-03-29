package com.company.tsp_solver.utils;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import javafx.scene.shape.Line;

import java.util.List;

public class WayDrawer {
    public void drawLine(double x1, double y1, double x2, double y2) {
        Line wayView = new Line(x1,y1,x2,y2);
        Model.instance.getController().mainField.getChildren().add(wayView);
        Model.instance.lines.add(wayView);
    }

    public void drawLine(Point p1, Point p2) {
        drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    public void drawWay(List<Point> way) {
        for (int i = 0; i < way.size(); ++i) {
            if(i != way.size() - 1) {
                drawLine(way.get(i), way.get(i + 1));
            } else {
                drawLine(way.get(i), way.get(0));
            }
        }
    }
}
