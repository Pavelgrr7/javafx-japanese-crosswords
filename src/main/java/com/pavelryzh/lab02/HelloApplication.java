package com.pavelryzh.lab02;

import com.pavelryzh.lab02.cipher.Encoder;
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
    public void start(Stage stage) throws IOException {

        VBox vBox = new VBox();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("assets/crossword"));
        File selectedFile = (fileChooser.showOpenDialog(stage));
        if (selectedFile != null) {
            Encoder ec = new Encoder(selectedFile);
//            ec.encodeFile();
            String fileContent = ec.decodeFile();

            Resources res = new Resources(fileContent);
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

            state = new FieldWidget.State(cellWidgetState);
            fieldWidget.setState(state);

            fieldWidget.setOnCellClickListener((x, y) -> {
                System.out.println("Клик по клетке на координатах: " + x + ", " + y);
                // Дополнительная логика
            });


            fieldWidget.setFieldState(ACTIVE);
            fieldWidget.setState(res.state);
            fieldWidget.drawNums(canvas.getGraphicsContext2D());

        } else System.exit(1);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setTitle("WIP");
        stage.setResizable(false);
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