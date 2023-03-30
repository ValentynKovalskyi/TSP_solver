package com.company.tsp_solver;

import javafx.application.Application;
import javafx.stage.Stage;

public class TSPSolver extends Application {
    @Override
    public void start(Stage stage) {
        Model.MODEL.setStage(stage);
        stage.setTitle("TSP Solver");
        stage.setResizable(false);
        stage.getIcons().add(Model.MODEL.getIcon());
        stage.setScene(Model.MODEL.getScene());
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}