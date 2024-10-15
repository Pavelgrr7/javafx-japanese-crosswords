package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import com.pavelryzh.lab02.ui.canvas.*;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.pavelryzh.lab02.ui.FieldWidget.FieldState.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

//import java.io.IOException;

public class HelloApplication extends Application {

    FieldWidget fieldWidget;
    FieldWidget.State state;

    @Override
    public void start(Stage stage) {

        VBox vBox = new VBox();
        Canvas canvas = new Canvas(700, 700);
        vBox.getChildren().add(canvas);
        vBox.setAlignment(Pos.CENTER);
//        Button uploadButton = new Button("Upload");
        //fieldWidget.setFieldState(INACTIVE);

//                            state.CellWidget.State[i] = new CellWidget.State[] {CellWidget.State.EMPTY, CellWidget.State.EMPTY, CellWidget.State.EMPTY},
//                        }
//                new FieldWidget.State.Notification(0)
        //System.out.println(Arrays.deepToString(cellWidgetState));

        //uploadButton.setOnAction(actionEvent -> {

            CellWidget.State[][] cellWidgetState = new CellWidget.State[FIELD_SIZE][FIELD_SIZE];
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    cellWidgetState[i][j] = CellWidget.State.NULL;
                }
            }
            fieldWidget = new CanvasFieldWidget(canvas);
            state = new FieldWidget.State(cellWidgetState);
            fieldWidget.setState(state);

            fieldWidget.setOnCellClickListener( (x, y) -> {
                System.out.println("Cell: " + x + ", " + y);
                state.cells()[x][y] = CellWidget.State.FILLED;
            });

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.setInitialDirectory(new File("assets/crossword"));
            File selectedFile = (fileChooser.showOpenDialog(stage));
            if (selectedFile != null) {
                try {
                    fieldWidget.setFieldState(ACTIVE);
                    fieldWidget.setState(getStateFrom(selectedFile));
                    fieldWidget.drawNums(canvas.getGraphicsContext2D());
//                    new FieldWidget.State = INACTIVE;
                } catch (IOException e) {
                    System.out.println("Error occurred while reading file");
                    e.printStackTrace();
                }
            }
//        vBox.getChildren().add(uploadButton);
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