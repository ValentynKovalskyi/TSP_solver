package com.company.tsp_solver.utilities;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import javafx.scene.shape.Line;

public class WayDrawer {
    public void drawLine(double x1, double y1, double x2, double y2) {
        Line wayView = new Line(x1,y1,x2,y2);
        Model.instance.getController().mainField.getChildren().add(wayView);
        Model.instance.lines.add(wayView);
    }
}
