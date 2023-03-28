package com.company.tsp_solver.point;

import com.company.tsp_solver.Model;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.io.Serial;
import java.io.Serializable;

public class Point implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static int index = 0;
    public Point(double v, double v1) {
        this.x = v;
        this.y = v1;
        point2D = new Point2D(x,y);
        pointView = new PointView(v,v1);
        name = "Name" + ((index == 0) ? "": index);
        ++index;
        id = index;
        pointPane = new PointPane(name);
        Model.instance.getController().sideList.getChildren().add(pointPane);
    }
    private final double x;
    private final double y;
    private transient Point2D point2D;
    private String name;
    private int id;
    private transient PointView pointView;
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
    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;
    }
    public void setPointView(PointView pointView) {
        this.pointView = pointView;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public Point2D getPoint2D() {
        return point2D;
    }
    public double distance(Point point) {
        return point2D.distance(point.getPoint2D());
    }
}
