package com.company.tsp_solver;

import com.company.tsp_solver.methods.BruteForce;
import com.company.tsp_solver.methods.NearestNeighbour;
import com.company.tsp_solver.methods.SolvingMethod;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainController implements Initializable {
    public MenuBar menuBar;
    public VBox sideList;
    public AnchorPane mainField;
    public Button solveButton;
    public Button clearButton;
    public TextArea appConsole;
    public ChoiceBox<SolvingMethod> solvingMethodChoice;
    // public List<SolvingMethod> methods;
    private final Stack<Point> unDoStack = new Stack<>();

    public void addPoint(MouseEvent event) {
        Point point = new Point(event.getX(),event.getY());
        PointPane pane = new PointPane(point);
        mainField.getChildren().add(point.getPointView());
        unDoStack.push(point);
        Model.instance.points.add(point);
        Model.instance.pointPanes.put(point,pane);
        sideList.getChildren().add(pane);
    }

    public void onFieldKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Z:
                if(event.isControlDown()) {
                    Point pointToDelete = unDoStack.pop();
                    Model.instance.points.remove(pointToDelete);
                    mainField.getChildren().remove(pointToDelete.getPointView());
                    sideList.getChildren().remove(Model.instance.pointPanes.get(pointToDelete));
                }
        }
    }

    public void onSolveButtonClick(Event event) {
        new NearestNeighbour(Model.instance.points).apply();//
    }

    public void clearButtonClick(Event event) {
        Model.instance.lines.forEach((line -> mainField.getChildren().remove(line)));
        Model.instance.lines.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };
       // methods = List.of(new BruteForce(Model.instance.points),new NearestNeighbour(Model.instance.points));
        solvingMethodChoice.setConverter(converter);
       // solvingMethodChoice.getItems().addAll(methods);
    }
    private StringConverter<SolvingMethod> converter = new StringConverter<>() {
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
                    .get();
        }
    };
}
