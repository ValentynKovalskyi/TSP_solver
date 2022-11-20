package com.company.tsp_solver.models;

import javafx.animation.AnimationTimer;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class PointPane extends BorderPane {
    private final Point point;
    private final Label nameLabel;
    private final RadioButton startRButton;
    private final CheckBox disableCheckBox;
    private final ToggleGroup radioButtonGroup = new ToggleGroup();

    public PointPane(Point point) {
        this.point = point;
        this.nameLabel = new Label("Name");
        this.startRButton = new RadioButton("Start");
        this.disableCheckBox = new CheckBox("Disable");

        setLeft(nameLabel);
        setCenter(startRButton);
        setRight(disableCheckBox);
        startRButton.setToggleGroup(radioButtonGroup);

        nameLabel.setOnMouseClicked(mouseEvent -> {
            TextField field = new TextField(nameLabel.getText());
            AnimationTimer unfocusTimer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (!field.isFocused()) {
                        nameLabel.setText(field.getText());
                        setLeft(nameLabel);
                        stop();
                    }
                }
            };
            new AnimationTimer(){
                @Override
                public void handle(long l) {
                    if (field.isFocused()) {
                        unfocusTimer.start();
                        stop();
                    }
                }
            }.start();
            setLeft(field);
        });
    }
}
