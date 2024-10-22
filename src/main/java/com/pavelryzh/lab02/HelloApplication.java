package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

import static com.pavelryzh.lab02.Resources.*;
import static com.pavelryzh.lab02.ui.FieldWidget.FieldState.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        VBox vBox = new VBox();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("assets/crossword"));
        File selectedFile = (fileChooser.showOpenDialog(stage));
        if (selectedFile != null) {
//                try {
            Resources res = new Resources(selectedFile);
            Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            vBox.getChildren().add(canvas);
            CellWidget.State[][] cellWidgetState = new CellWidget.State[res.WIDTH][res.HEIGHT];
            for (int i = 0; i < FIELD_WIDTH; i++) {
                for (int j = 0; j < FIELD_HEIGHT; j++) {
                    cellWidgetState[i][j] = CellWidget.State.NULL;
                }
            }
            FieldWidget fieldWidget;
            FieldWidget.State state;

            fieldWidget = new CanvasFieldWidget(canvas);

            //res.setCanvasFieldWidget(fieldWidget);

            state = new FieldWidget.State(cellWidgetState);
            fieldWidget.setState(state);

            fieldWidget.setOnCellClickListener((x, y) -> {
                System.out.println("Cell: " + x + ", " + y);
                state.cells()[x][y] = CellWidget.State.FILLED;
            });

            fieldWidget.setFieldState(ACTIVE);
            fieldWidget.setState(res.state);
            fieldWidget.drawNums(canvas.getGraphicsContext2D());
//                    new FieldWidget.State = INACTIVE;

        }
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setTitle("WIP");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

//    public record FieldState(CanvasCellWidget[][] cells) {
//
//
//        public record Notification(int code) {
//        }
//    }
}