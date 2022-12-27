package com.company.tsp_solver;

import com.company.tsp_solver.algorithms.KruskalsAlgorithm;
import com.company.tsp_solver.algorithms.PrimsAlgorithm;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.point.PointPane;
import com.company.tsp_solver.utilities.TimeDistance;
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
        mainField.getChildren().add(point.getPointView());
        unDoStack.push(point);
        Model.instance.points.add(point);
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
                    sideList.getChildren().remove(pointToDelete.getPointPane());
                    --Point.index;
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
       TimeDistance result = Model.instance.getMethods()[solvingMethodChoice.getItems().indexOf(methodName)].apply();
        appConsole.setText(appConsole.getText() + String.format("%s for %d dots. Distance: %.2f d. Time: %d milliseconds\n",
                methodName, Model.instance.points.size(), result.getDistance(), result.getTime()));
    }

    public void clearButtonClick(Event event) {
        Model.instance.lines.forEach((line -> mainField.getChildren().remove(line)));
        Model.instance.lines.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kruskalButton.setVisible(false);
        solvingMethodChoice.setTooltip(new Tooltip("Choose Solving Method"));
    }

    public void primAlgorithm(ActionEvent event) {
        clearButton.fire();
       TimeDistance result = PrimsAlgorithm.apply(Model.instance.points);
        appConsole.setText(appConsole.getText() + String.format("Prim's algorithm for %d dots. Distance: %.2f d. Time: %d milliseconds\n",
                Model.instance.points.size(), result.getDistance(), result.getTime()));
    }

    public void kruskalAlgorithm(ActionEvent event) {
        clearButton.fire();
        long result = KruskalsAlgorithm.apply(Model.instance.points);
        appConsole.setText(appConsole.getText() + String.format("Kruskal's algorithm for %d dots. Time %d milliseconds\n", Model.instance.points.size(), result));
    }
    public void saveAsButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("graph.txt");
        File fileToSave = fileChooser.showSaveDialog(Model.instance.getStage());
        OutputStream out;
        ObjectOutputStream objectOut = null;
        try {
            out = new FileOutputStream(fileToSave);
            objectOut = new ObjectOutputStream(out);
        } catch (IOException e) {
            System.out.println("Exception occured during saving of file");
            return;
        }
        try {
            objectOut.writeObject(Model.instance.points);
        } catch (IOException e) {
            System.out.println("Exception occured during writing objects into file");
            e.printStackTrace();
            return;
        }
        try {
            out.close();
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.instance.setLoadedFile(fileToSave);
    }
    public void saveButton(){
        if(Model.instance.getLoadedFile() == null) {
            saveAsButton();
        } else {
            OutputStream out;
            ObjectOutputStream objectOut = null;
            try {
                out = new FileOutputStream(Model.instance.getLoadedFile());
                objectOut = new ObjectOutputStream(out);
            } catch (IOException e) {
                System.out.println("Exception occured during saving of file");
                return;
            }
            try {
                objectOut.writeObject(Model.instance.points);
            } catch (IOException e) {
                System.out.println("Exception occured during writing objects into file");
            }
            try {
                out.close();
                objectOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void newMenuButton() {
        Dialog<ButtonType> dialogWindow = new Dialog<>();
        dialogWindow.setTitle("Data saving");
        dialogWindow.setContentText("Do you want save current data?");
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        dialogWindow.getDialogPane().getButtonTypes().add(okButton);
        dialogWindow.getDialogPane().getButtonTypes().add(noButton);
        Optional<ButtonType> result = dialogWindow.showAndWait();
        if(result.isPresent()) {
            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) saveButton();
            sideList.getChildren().clear();
            mainField.getChildren().clear();
            Model.instance.getPoints().clear();
            Point.index = 0;
        }
    }

    public void menuOpen() {
        newMenuButton();
        FileChooser fileChooser = new FileChooser();
        File fileToOpen = fileChooser.showOpenDialog(Model.instance.getStage());
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
        Model.instance.points.addAll(loadedList);
        Model.instance.points.forEach(point -> {
            PointPane pointPane = new PointPane(point.getName());
            point.setPointPane(pointPane);
            sideList.getChildren().add(pointPane);
            mainField.getChildren().add(point.getPointView());
        });
        Point.index = Model.instance.points.size();
        Model.instance.setLoadedFile(fileToOpen);
        try {
            in.close();
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menuQuit() {
    }
}
