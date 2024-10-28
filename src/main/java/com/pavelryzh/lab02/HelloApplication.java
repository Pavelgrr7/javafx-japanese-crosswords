package com.pavelryzh.lab02;

import com.pavelryzh.lab02.cipher.Encoder;
import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;

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
            //использую encoder для расшифровки файла
            Encoder ec = new Encoder(selectedFile);
//            ec.encodeFile();
            String fileContent = ec.decodeFile();
            System.out.println(fileContent);
            //передаю строку с полем в ресурсы для расчёта необходимых констант
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

            //получаю матрицу с полем
            cellWidgetState = (res.state).cells();
            state = new FieldWidget.State(cellWidgetState);

            fieldWidget.setFieldState(ACTIVE);
            fieldWidget.setState(state);
            //отрисовываю числа-подсказки
            fieldWidget.drawNums(canvas.getGraphicsContext2D());

            //вешаю слушатель на поле
            fieldWidget.setOnCellClickListener(new OnCellClickListener() {
                EndObserver gameEndObserver = new GameEndObserver(fieldWidget, state.cells());

                @Override
                public void onClick(int x, int y, MouseButton button) {
                    //System.out.println("Clicked: " + x + ", " + y);
                    if (fieldWidget.getFieldState() == WIN) return;
                    if (button == MouseButton.PRIMARY ) {
                        System.out.println(state.cells()[y][x]);

                        switch(state.cells()[y][x]) {
                            case CellWidget.State.FILLED -> state.cells()[y][x] = CellWidget.State.OPEN_RIGHT;
                            case CellWidget.State.EMPTY -> state.cells()[y][x] = CellWidget.State.OPEN_WRONG;
                            case CellWidget.State.OPEN_RIGHT ->
                                state.cells()[y][x] = CellWidget.State.FILLED;
                                //countRight++;

                            case CellWidget.State.OPEN_WRONG -> state.cells()[y][x] = CellWidget.State.EMPTY;
                        }
                        gameEndObserver.cellStateChanged(state.cells()[y][x]);
                    } else {
                        switch (state.cells()[y][x]) {
                            case CellWidget.State.EMPTY, CellWidget.State.FILLED ->
                                    state.cells()[y][x] = CellWidget.State.POINT;
                            case CellWidget.State.POINT -> state.cells()[y][x] = CellWidget.State.EMPTY;
                        }
                    }
//                    if (count == 5)
//                        gameEndObserver.victoryNotification();
                    fieldWidget.updateState(state, x, y);
//                    count++;
                }
            });

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