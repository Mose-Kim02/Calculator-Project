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
    private StackPane result;
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
        inputs.setAlignment(Pos.TOP_RIGHT);//Align text to center
        inputs.setPrefWidth(120);
        hbForTextField.getChildren().addAll(inputs);


        BorderPane.setMargin(hbForTextField, insetTop);

        buttons = new Button[19];
        tilePaneNumbers = new TilePane();
        tilePaneNumbers.setPadding(new Insets(45));

        int i = 0;
        int buttonValues = 7;
        while ( i < 12) {
            Button button = new Button();
            if (i == 9) {
                button = new Button("(");
            } else if ( i == 10) {
                button = new Button(Integer.toString(0));
            } else if ( i == 11) {
                button = new Button(")");
            } else {
                button = new Button(Integer.toString(buttonValues));
            }
            Font font = new Font(20);
            button.setFont(font);
            button.setPrefWidth(65);
            button.setPrefHeight(65);
            tilePaneNumbers.getChildren().add(button);
            if (buttonValues % 3 == 0) {
                buttonValues = buttonValues - 5;
            }
            else {
                buttonValues++;
            }
            buttons[i] = button;
            i++;
        }

        tilePaneNumbers.setOrientation(Orientation.HORIZONTAL);
        tilePaneNumbers.setHgap(10);
        tilePaneNumbers.setVgap(10);

        tilePaneOperators = new TilePane();

        for (int j = 12; j <= 18; j++) {
            Button button = new Button();
            button.setPrefWidth(40);
            button.setPrefHeight(40);
            tilePaneOperators.getChildren().add(button);
            buttons[j] = button;
            Font font = new Font(18);
            button.setFont(font);
        }
        buttons[12].setText("←");
        buttons[13].setText("/");
        buttons[14].setText("*");
        buttons[15].setText("+");
        buttons[16].setText("-");
        buttons[17].setText(".");
        buttons[18].setText("=");

        buttons[0].setOnAction(e -> { inputs.appendText(buttons[0].getText());});
        buttons[1].setOnAction(e -> { inputs.appendText(buttons[1].getText());});
        buttons[2].setOnAction(e -> { inputs.appendText(buttons[2].getText());});
        buttons[3].setOnAction(e -> { inputs.appendText(buttons[3].getText());});
        buttons[4].setOnAction(e -> { inputs.appendText(buttons[4].getText());});
        buttons[5].setOnAction(e -> { inputs.appendText(buttons[5].getText());});
        buttons[6].setOnAction(e -> { inputs.appendText(buttons[6].getText());});
        buttons[7].setOnAction(e -> { inputs.appendText(buttons[7].getText());});
        buttons[8].setOnAction(e -> { inputs.appendText(buttons[8].getText());});
        buttons[9].setOnAction(e -> { inputs.appendText(buttons[9].getText());});
        buttons[10].setOnAction(e -> { inputs.appendText(buttons[10].getText());});
        buttons[11].setOnAction(e -> { inputs.appendText(buttons[11].getText());});
        buttons[12].setOnAction(e -> { inputs.deleteText(inputs.getLength()-1, inputs.getLength());});
        buttons[13].setOnAction(e -> { inputs.appendText(buttons[13].getText());});
        buttons[14].setOnAction(e -> { inputs.appendText(buttons[14].getText());});
        buttons[15].setOnAction(e -> { inputs.appendText(buttons[15].getText());});
        buttons[16].setOnAction(e -> { inputs.appendText(buttons[16].getText());});
        buttons[17].setOnAction(e -> { inputs.appendText(buttons[17].getText());});
        buttons[18].setOnAction(e -> {

            System.out.println("HAHAHAHAH:   " + num.calculate(inputs.getText()));
            answer = num.calculate(inputs.getText());
            inputs.setText(answer);
            ;}
        );

        tilePaneOperators.setOrientation(Orientation.VERTICAL);
        tilePaneOperators.setPadding(new Insets(20));
        tilePaneOperators.setHgap(10);
        tilePaneOperators.setVgap(10);


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
