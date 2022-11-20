package com.company.tsp_solver.models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Point extends Point2D implements Serializable {
    private String name; // what is that here for
    private final Circle pointView;

    public Point(double v, double v1) {
        super(v, v1);
        pointView = new Circle(3);
        pointView.setFill(Color.RED);
        pointView.setCenterX(getX());
        pointView.setCenterY(getY());
    }
}
