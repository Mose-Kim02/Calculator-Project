package com.example.calculator;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainView {
    private Stage stage;
    private BorderPane root;
    private TilePane tilePaneNumbers;
    private TilePane tilePaneOperators;
    private Scene scene;
    private Button[] buttons;
    private String answer = "";

    CalculatorCode num = new CalculatorCode();

    public MainView(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    private void buildUI() {
        root = new BorderPane();
        Insets insetTop = new Insets(15);

        HBox hbForTextField = new HBox();
        TextField inputs = new TextField();
        inputs.setMinWidth(350);
        inputs.setMinHeight(50);
        inputs.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        inputs.setStyle("-fx-background-color: darkseagreen;");
        inputs.setAlignment(Pos.TOP_RIGHT);
        hbForTextField.getChildren().add(inputs);

        BorderPane.setMargin(hbForTextField, insetTop);

        buttons = new Button[19];
        tilePaneNumbers = new TilePane();
        tilePaneNumbers.setPadding(new Insets(45));
        tilePaneNumbers.setHgap(10);
        tilePaneNumbers.setVgap(10);

        int i = 0;
        int buttonValues = 7;
        while (i < 12) {
            Button button;
            if (i == 9) {
                button = new Button("(");
            } else if (i == 10) {
                button = new Button("0");
            } else if (i == 11) {
                button = new Button(")");
            } else {
                button = new Button(Integer.toString(buttonValues));
            }
            button.setFont(new Font(20));
            button.setPrefWidth(65);
            button.setPrefHeight(65);
            tilePaneNumbers.getChildren().add(button);

            if (buttonValues % 3 == 0) {
                buttonValues -= 5;
            } else {
                buttonValues++;
            }
            buttons[i] = button;
            i++;
        }

        tilePaneOperators = new TilePane();
        tilePaneOperators.setOrientation(Orientation.VERTICAL);
        tilePaneOperators.setPadding(new Insets(20));
        tilePaneOperators.setHgap(10);
        tilePaneOperators.setVgap(10);

        String[] operatorSymbols = {"←", "/", "*", "+", "-", ".", "="};
        for (int j = 12; j < 19; j++) {
            Button button = new Button(operatorSymbols[j - 12]);
            button.setFont(new Font(18));
            button.setPrefWidth(65);
            button.setPrefHeight(65);
            tilePaneOperators.getChildren().add(button);
            buttons[j] = button;
        }

        // Add event handlers for buttons
        for (int k = 0; k < 12; k++) {
            int index = k; // Required for lambda expression to use the correct index
            buttons[k].setOnAction(e -> inputs.appendText(buttons[index].getText()));
        }

        buttons[12].setOnAction(e -> {
            if (inputs.getLength() > 0) {
                inputs.deleteText(inputs.getLength() - 1, inputs.getLength());
            }
        });

        for (int j = 13; j < 18; j++) {
            int index = j; // To use within the lambda expression
            buttons[j].setOnAction(e -> inputs.appendText(buttons[index].getText()));
        }

        buttons[18].setOnAction(e -> {
            try {
                answer = num.calculate(inputs.getText());
                inputs.setText(answer);
            } catch (Exception ex) {
                inputs.setText("Error");
            }
        });

        root.setTop(hbForTextField);
        root.setCenter(tilePaneNumbers);
        root.setRight(tilePaneOperators);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(408);
        stage.setHeight(550);
        stage.setResizable(false);
        stage.show();
    }
}
