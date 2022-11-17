package com.company.tsp_solver;

import javafx.application.Application;
import javafx.stage.Stage;

public class TSPSolver extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("TSP Solver");
        stage.getIcons().add(Model.instance.getIcon());
        stage.setScene(Model.instance.getScene());
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}