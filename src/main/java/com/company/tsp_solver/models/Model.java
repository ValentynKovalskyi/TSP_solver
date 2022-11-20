package com.company.tsp_solver.models;

import com.company.tsp_solver.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.*;

public class Model { // FIXME name
    public final static Model MODEL;

    static {
        try {
            MODEL = new Model();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final List<Point> points = new ArrayList<>();
    public final Map<Point, PointPane> pointPanes = new HashMap<>();
    public final List<Line> lines = new LinkedList<>();
    public final Parent root;
    public final MainController controller;
    public final Scene scene; // you can make Model extend Scene by default
    public final Image icon;

    private Model() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        scene = new Scene(root, 1000, 600);
        scene.setOnKeyPressed(controller::onFieldKeyPressed);

        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))); // :)
        // please avoid using absolute paths. you won't be able to redistribute the app otherwise
        /* icon = */ new Image("D:\\Університет\\2 курс 1 семестр\\АСД\\TSP Solver\\src\\main\\resources\\com\\company\\tsp_solver\\icon.png");
        //якась хуйня, не може просто по нахві знайти, це тимчасво таке посилання на папку
    }

}
