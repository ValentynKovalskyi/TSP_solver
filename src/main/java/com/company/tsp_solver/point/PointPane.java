package com.company.tsp_solver.point;

import javafx.animation.AnimationTimer;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public @Data class PointPane extends BorderPane {
    private Label nameLabel;
    private RadioButton startRButton = new RadioButton();
    private CheckBox disableCheckBox = new CheckBox();
    private static final ToggleGroup radioButtonGroup = new ToggleGroup();
    public static final List<RadioButton> startRBList = new LinkedList<>();
    public static boolean isStart = false;
    public PointPane(String name) {
        nameLabel = new Label(name);
        setLeft(nameLabel);
        setCenter(startRButton);
        setRight(disableCheckBox);

        startRButton.setToggleGroup(radioButtonGroup);
        startRBList.add(startRButton);
        startRButton.setDisable(!isStart);

        nameLabel.setOnMouseClicked(mouseEvent -> {
            TextField field = new TextField(nameLabel.getText());
            AnimationTimer unfocusTimer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if(!field.isFocused()) {
                        nameLabel.setText(field.getText());
                        setLeft(nameLabel);
                        stop();
                    }
                }
            };
            new AnimationTimer(){
                @Override
                public void handle(long l) {
                    if(field.isFocused()) {
                        unfocusTimer.start();
                        stop();
                    }
                }
            }.start();
            setLeft(field);
        });
    }
}
