package com.company.tsp_solver.point;

import com.company.tsp_solver.Model;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.io.Serial;
import java.io.Serializable;

public class Point extends Point2D implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static int index = 0;
    public Point(double v, double v1) {
        super(v, v1);
        pointView = new PointView(v,v1);
        name = "Name" + ((index == 0) ? "": index);
        ++index;
        pointPane = new PointPane(name);
        Model.instance.getController().sideList.getChildren().add(pointPane);
    }
    private String name;
    private final PointView pointView;
    private transient PointPane pointPane;
    public Circle getPointView() {
        return pointView;
    }
    public PointPane getPointPane() {
        return pointPane;
    }
    public String getName() {
        return name;
    }
    public void setPointPane(PointPane pointPane) {
        this.pointPane = pointPane;
    }
}
