package com.company.tsp_solver.controllerview;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.point.PointView;
import javafx.geometry.Point2D;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class FileOpener {
    protected void openFile() {
        FileChooser fileChooser = new FileChooser();
        File fileToOpen = fileChooser.showOpenDialog(Model.MODEL.getStage());
        InputStream in;
        ObjectInputStream objectInput = null;
        ArrayList<Point> loadedList;
        try {
            in = new FileInputStream(fileToOpen);
            objectInput = new ObjectInputStream(in);
        } catch (IOException e) {
            System.out.println("Exception occured during opening of file");
            return;
        }
        try {
            loadedList = (ArrayList<Point>) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception occured during reading of objects");
            e.printStackTrace();
            return;
        }
        Model.MODEL.points.addAll(loadedList);
        Model.MODEL.points.forEach(point -> {
            point.setPoint2D(new Point2D(point.getX(),point.getY()));
            point.setPointView(new PointView(point.getX(),point.getY()));
            Model.MODEL.getController().mainField.getChildren().add(point.getPointView());
        });
        Point.index = Model.MODEL.points.size();
        Model.MODEL.setLoadedFile(fileToOpen);
        try {
            in.close();
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
