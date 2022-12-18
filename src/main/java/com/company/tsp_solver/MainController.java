package com.company.tsp_solver;

import com.company.tsp_solver.algorithms.KruskalsAlgorithm;
import com.company.tsp_solver.algorithms.PrimsAlgorithm;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainController implements Initializable {
    public MenuBar menuBar;
    public VBox sideList;
    public AnchorPane mainField;
    public Button solveButton;
    public Button clearButton;
    public Button addImageButton;
    public Button primButton;
    public Button kruskalButton;
    public CheckBox checkBStart;
    public TextArea appConsole;
    public ChoiceBox<String> solvingMethodChoice;
    public ImageView userImage;
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
    public void checkBStartAction(Event event) {
        if(PointPane.isStart) PointPane.startRBList.forEach(radioButton -> {
            radioButton.setSelected(false);
            radioButton.setDisable(true);
            PointPane.isStart = false;
        });
        else PointPane.startRBList.forEach(radioButton -> {
            radioButton.setDisable(false);
            PointPane.isStart = true;
        });
    }
    public void addImage(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Model.instance.getStage());
        userImage = new ImageView(file.getPath());
        userImage.setOpacity(0.75);
        mainField.getChildren().add(userImage);
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
                break;
            case EQUALS:
                if(userImage == null) break;
                userImage.setScaleX(userImage.getScaleX() + 0.01);
                userImage.setScaleY(userImage.getScaleY() + 0.01);
                System.out.println("+");
                break;
            case MINUS:
                if(userImage == null) break;
                userImage.setScaleX(userImage.getScaleX() - 0.01);
                userImage.setScaleY(userImage.getScaleY() - 0.01);
                break;
            case W:
                userImage.setLayoutY(userImage.getLayoutY() - 10);
                break;
            case S:
                userImage.setLayoutY(userImage.getLayoutY() + 10);
                break;
            case A:
                userImage.setLayoutX(userImage.getLayoutX() - 10);
                break;
            case D:
                userImage.setLayoutX(userImage.getLayoutX() + 10);
                break;
        }
    }

    public void onSolveButtonClick(Event event) {
        clearButton.fire();
        String methodName = solvingMethodChoice.getValue();
       double result = Model.instance.getMethods()[solvingMethodChoice.getItems().indexOf(methodName)].apply();
        appConsole.setText(appConsole.getText() + String.format("%s for %d dots: %.1f d\n", methodName, Model.instance.points.size(), result));
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
        solvingMethodChoice.setTooltip(new Tooltip("Choose Solving Method"));
    }

    public void primAlgorithm(ActionEvent event) {
        clearButton.fire();
       long result = PrimsAlgorithm.apply(Model.instance.points);
        appConsole.setText(appConsole.getText() + String.format("Prim's algorithm for %d dots. Time %d milliseconds\n", Model.instance.points.size(), result));
    }

    public void kruskalAlgorithm(ActionEvent event) {
        clearButton.fire();
        long result = KruskalsAlgorithm.apply(Model.instance.points);
        appConsole.setText(appConsole.getText() + String.format("Kruskal's algorithm for %d dots. Time %d milliseconds\n", Model.instance.points.size(), result));
    }

}
