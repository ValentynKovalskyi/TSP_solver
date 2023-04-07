package com.company.tsp_solver.controllerview;

import com.company.tsp_solver.Model;
import com.company.tsp_solver.algorithms.PrimsAlgorithm;
import com.company.tsp_solver.methods.SolvingMethod;
import com.company.tsp_solver.point.Point;
import com.company.tsp_solver.utils.DistanceMatrixGenerator;
import com.company.tsp_solver.utils.MethodNameConverter;
import com.company.tsp_solver.utils.TimeDistance;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainController implements Initializable {

    public MenuBar menuBar;
    public AnchorPane mainField;
    public Button solveButton;
    public Button clearButton;
    public Button addImageButton;
    public Button primButton;
    public Button matrixButton;
    public TextArea appConsole;
    public ChoiceBox<SolvingMethod> solvingMethodChoice;

    public ImageView userImage;

    private final Stack<Point> unDoStack = new Stack<>();

    private final FileSaver fileSaver = new FileSaver();

    private final FileOpener fileOpener = new FileOpener();
    public ScrollPane matrixScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        solvingMethodChoice.setTooltip(new Tooltip("Choose Solving Method"));
        solvingMethodChoice.setConverter(new MethodNameConverter());
    }

    public void addPoint(MouseEvent event) {
        Point point = new Point(event.getX(),event.getY());
        mainField.getChildren().add(point.getPointView());
        unDoStack.push(point);
        Model.MODEL.points.add(point);
    }

    public void addImage(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Model.MODEL.getStage());
        userImage = new ImageView(file.getPath());
        userImage.setOpacity(0.75);
        mainField.getChildren().add(userImage);
    }
    public void onFieldKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Z -> {
                if (event.isControlDown()) {
                    Point pointToDelete = unDoStack.pop();
                    Model.MODEL.points.remove(pointToDelete);
                    mainField.getChildren().remove(pointToDelete.getPointView());
                    --Point.index;
                }
            }
            case EQUALS -> {
                if (userImage == null) break;
                userImage.setScaleX(userImage.getScaleX() + 0.01);
                userImage.setScaleY(userImage.getScaleY() + 0.01);
                System.out.println("+");
            }
            case MINUS -> {
                if (userImage == null) break;
                userImage.setScaleX(userImage.getScaleX() - 0.01);
                userImage.setScaleY(userImage.getScaleY() - 0.01);
            }
            case W -> userImage.setLayoutY(userImage.getLayoutY() - 10);
            case S -> userImage.setLayoutY(userImage.getLayoutY() + 10);
            case A -> userImage.setLayoutX(userImage.getLayoutX() - 10);
            case D -> userImage.setLayoutX(userImage.getLayoutX() + 10);
        }
    }

    public void onSolveButtonClick(Event event) {
        clearButton.fire();
        SolvingMethod method = solvingMethodChoice.getValue();
       TimeDistance result = method.execute();
        appConsole.setText
                (appConsole.getText() +
                String.format("%s for %d dots. Distance: %.2f d. Time: %d milliseconds\n",
                method.getName(), Model.MODEL.points.size(), result.getDistance(), result.getTime()));
    }

    public void clearButtonClick(Event event) {
        Model.MODEL.lines.forEach((line -> mainField.getChildren().remove(line)));
        Model.MODEL.lines.clear();
    }

    public void primAlgorithm(ActionEvent event) {
        clearButton.fire();
       TimeDistance result = PrimsAlgorithm.apply(Model.MODEL.points);
        appConsole.setText(appConsole.getText() + String.format("Prim's algorithm for %d dots. Distance: %.2f px. Time: %d milliseconds\n",
                Model.MODEL.points.size(), result.getDistance(), result.getTime()));
    }

    public void generateMatrix(ActionEvent event) {

        DistanceMatrixGenerator generator = new DistanceMatrixGenerator();
        GridPane graphMatrix = getNewGridPane();
        double[][] matrix = generator.generateMatrix(Model.MODEL.points);
        double fontSize = 18;
        //fill point number row
        Label[] numberRow = new Label[matrix.length + 1];
        for (int i = 0; i < numberRow.length; ++i) {
            Label number = new Label(String.valueOf(i));
            number.setFont(Font.font(fontSize));
            numberRow[i] = number;
        }
        graphMatrix.addRow(0,numberRow);

        for (int i = 0; i < matrix.length; ++i) {
            double[] row = matrix[i];
            Label[] labels = new Label[row.length + 1];
            Label pointNum = new Label(String.valueOf(i + 1));
            pointNum.setFont(Font.font(fontSize));
            labels[0] = pointNum;
            for (int j = 0; j < row.length; ++j) {
                Label label =  new Label(String.format("%.1f",row[j]));
                label.setFont(Font.font(fontSize));
                labels[j + 1] = label;
            }
            graphMatrix.addRow(i + 1,labels);
        }

    }

    private GridPane getNewGridPane() {
        GridPane graphMatrix = new GridPane();
        matrixScrollPane.setContent(graphMatrix);
        graphMatrix.setGridLinesVisible(true);
        graphMatrix.setHgap(1);
        graphMatrix.setVgap(1);
        return graphMatrix;
    }
    public void saveAsButton() {
        fileSaver.saveFileAs();
    }
    public void saveButton(){
        fileSaver.saveFile();
    }
    public void newMenuButton() {
        if(Model.MODEL.points.isEmpty()) return;
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
            clearGraph();
        }
    }

    public void clearGraph() {
        mainField.getChildren().clear();
        Model.MODEL.getPoints().clear();
        Point.index = 0;
    }

    public void menuOpen() {
        clearGraph();
        fileOpener.openFile();
    }

    public void menuQuit() {
    }
}
