package com.company.tsp_solver;

import javafx.application.Application;
import javafx.stage.Stage;

import static com.company.tsp_solver.models.Model.MODEL;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("TSP Solver");
        stage.getIcons().add(MODEL.icon);
        stage.setScene(MODEL.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
