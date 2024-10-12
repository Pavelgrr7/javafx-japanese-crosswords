package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        VBox vBox = new VBox();
        Canvas canvas = new Canvas(700, 700);
        vBox.getChildren().add(canvas);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        stage.setTitle("WIP");
        stage.setScene(scene);
        stage.show();

        FieldWidget fieldWidget = new CanvasFieldWidget(canvas);
        FieldWidget.State state = new FieldWidget.State(
                new CellWidget.State[][] {
                        new CellWidget.State[] {CellWidget.State.EMPTY}
                }
                );
        fieldWidget.setState(state);
    }
    public static void main(String[] args) {
        launch();
    }
}