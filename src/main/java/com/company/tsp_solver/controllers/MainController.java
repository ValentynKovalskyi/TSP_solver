package com.company.tsp_solver.controllers;

import com.company.tsp_solver.models.Model;
import com.company.tsp_solver.models.Point;
import com.company.tsp_solver.models.PointPane;
import com.company.tsp_solver.methods.SolvingMethod;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import static com.company.tsp_solver.models.Model.MODEL;

public class MainController implements Initializable {
    public MenuBar menuBar;
    public VBox sideList;
    public AnchorPane mainField;
    public Button solveButton;
    public Button clearButton;
    public TextArea appConsole;
    public ChoiceBox<SolvingMethod> solvingMethodChoice;
    // public List<SolvingMethod> methods;

    private final Stack<Point> unDoStack = new Stack<>(); // stack is deprecated, prefer ArrayDeque instead

    private final StringConverter<SolvingMethod> converter = new StringConverter<>() {
        @Override
        public String toString(SolvingMethod solvingMethod) {
            return solvingMethod == null ? null : solvingMethod.getName();
        }

        @Override
        public SolvingMethod fromString(String s) {
            return solvingMethodChoice.getItems()
                    .stream()
                    .filter((solvingMethod -> solvingMethod.getName().equals(s)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("solving method not found"));
        }
    };

    public void addPoint(MouseEvent event) {
        Point point = new Point(event.getX(),event.getY());
        PointPane pane = new PointPane(point);
        mainField.getChildren().add(point.getPointView());
        unDoStack.push(point);
        MODEL.points.add(point);
        MODEL.pointPanes.put(point,pane);
        sideList.getChildren().add(pane);
    }

    public void onFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.Z) {
            if (event.isControlDown()) {
                Point pointToDelete = unDoStack.pop();
                MODEL.points.remove(pointToDelete);
                mainField.getChildren().remove(pointToDelete.getPointView());
                sideList.getChildren().remove(MODEL.pointPanes.get(pointToDelete));
            }
        }
    }

    public void onSolveButtonClick(Event event) {
        //запуск одного з SolvingMethods з solvingMethodChoice, але з цим була фігня якась, потім гляну
    }

    public void clearButtonClick(Event event) {
        MODEL.lines.forEach((line -> mainField.getChildren().remove(line)));
        MODEL.lines.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // methods = List.of(new BruteForce(Model.instance.points),new NearestNeighbour(Model.instance.points));
        solvingMethodChoice.setConverter(converter);
       // solvingMethodChoice.getItems().addAll(methods);
    }

}
