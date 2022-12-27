package com.company.tsp_solver;

import com.company.tsp_solver.methods.BruteForce;
import com.company.tsp_solver.methods.NearestNeighbour;
import com.company.tsp_solver.methods.SolvingMethod;
import com.company.tsp_solver.point.Point;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public @Data class Model {
    public final static Model instance = new Model();
    public final List<Point> points = new ArrayList<>();
    public final List<Line> lines = new LinkedList<>();
    private SolvingMethod[] methods = { new BruteForce(), new NearestNeighbour() };
    private Parent root;
    private MainController controller;
    private Scene scene;
    private Stage stage;
    private Image icon;
    private File loadedFile;
    private Model(){
        FXMLLoader fxmlLoader = new FXMLLoader(Model.class.getResource("sample.fxml"));
        try {
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(root, 1000, 600);
        scene.setOnKeyPressed(event -> controller.onFieldKeyPressed(event));
        icon = new Image(Objects.requireNonNull(Model.class.getResource("icon.png")).toString());
        for (SolvingMethod method:methods) {
            controller.solvingMethodChoice.getItems().add(method.getName());
        }
    }
}
