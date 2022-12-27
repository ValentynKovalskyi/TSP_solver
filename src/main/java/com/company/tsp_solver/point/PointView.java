package com.company.tsp_solver.point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serial;
import java.io.Serializable;

public class PointView extends Circle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public PointView(double X, double Y) {
        super(3);
        this.setCenterX(X);
        this.setCenterY(Y);
        this.setFill(Color.RED);
    }
}
