package com.company.tsp_solver;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Point extends Point2D implements Serializable {
    public Point(double v, double v1) {
        super(v, v1);
        pointView = new Circle(3);
        pointView.setFill(Color.RED);
        pointView.setCenterX(getX());
        pointView.setCenterY(getY());
    }
    private String name;
    private final Circle pointView;
    public Circle getPointView() {
        return pointView;
    }
}
