package com.pavelryzh.lab02;

import com.pavelryzh.lab02.cipher.Encoder;
import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
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


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File("assets/crossword"));
        File selectedFile = (fileChooser.showOpenDialog(stage));
        StackPane stackPane = null;

        if (selectedFile != null) {
            //использую encoder для расшифровки файла
            Encoder ec = new Encoder(selectedFile);
//            ec.encodeFile();
            String fileContent = ec.decodeFile();

            //передаю строку с полем в ресурсы для расчёта необходимых констант
            Resources res = new Resources(fileContent);

            Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

            Button surrenderBtn = new Button();
            surrenderBtn.setText("Я сдаюсь!");
            StackPane.setAlignment(surrenderBtn, javafx.geometry.Pos.TOP_LEFT);
            stackPane = new StackPane(canvas, surrenderBtn);
            StackPane.setMargin(surrenderBtn, new Insets(10, 0, 0, 10));

            CellWidget.State[][] cellWidgetState = new CellWidget.State[Resources.WIDTH][Resources.HEIGHT];
            for (int i = 0; i < FIELD_WIDTH; i++) {
                for (int j = 0; j < FIELD_HEIGHT; j++) {
                    cellWidgetState[i][j] = CellWidget.State.NULL;
                }
            }
            FieldWidget fieldWidget;
            FieldWidget.State state;

            fieldWidget = new CanvasFieldWidget(canvas);

            //получаю матрицу с полем
            cellWidgetState = (Resources.state).cells();
            state = new FieldWidget.State(cellWidgetState);

            fieldWidget.setFieldState(ACTIVE);
            fieldWidget.setState(state);
            //отрисовываю числа-подсказки
            fieldWidget.drawNums(canvas.getGraphicsContext2D());

            final EndObserver gameEndObserver = new GameEndObserver(fieldWidget, state.cells());

            //вешаю слушатель на поле
            fieldWidget.setOnCellClickListener(new OnCellClickListener() {


                @Override
                public void onClick(int x, int y, MouseButton button) {
                    if (fieldWidget.getFieldState() == WIN || fieldWidget.getFieldState() == INACTIVE) return;
                    if (button == MouseButton.PRIMARY) {
                        //System.out.println(state.cells()[y][x]);

                        // для удобства дальнейшей обработки заменяю клетки на 'открыто верно\неверно', либо обратно, если пользователь снова нажал по клетке
                        switch (state.cells()[y][x]) {
                            case CellWidget.State.FILLED -> state.cells()[y][x] = CellWidget.State.OPEN_RIGHT;
                            case CellWidget.State.EMPTY -> state.cells()[y][x] = CellWidget.State.OPEN_WRONG;
                            case CellWidget.State.OPEN_RIGHT -> state.cells()[y][x] = CellWidget.State.FILLED;

                            case CellWidget.State.OPEN_WRONG -> state.cells()[y][x] = CellWidget.State.EMPTY;
                        }
                        // передаю изменившуюся клетку в класс, наблюдающий за полем в ожидании состояния победы
                        gameEndObserver.cellStateChanged(state.cells()[y][x]);
                    } else if (button == MouseButton.SECONDARY || button == MouseButton.MIDDLE) {
                        // ставится точка и клетка блокируется
                        switch (state.cells()[y][x]) {
                            case CellWidget.State.EMPTY, CellWidget.State.FILLED ->
                                    state.cells()[y][x] = CellWidget.State.POINT;
                            case CellWidget.State.POINT -> state.cells()[y][x] = CellWidget.State.EMPTY;
                        }
                    }
                    // обновляю состояние поля
                    fieldWidget.updateState(state, x, y);
                }
            });

            //кнопка, если пользователь решит сдатся
            surrenderBtn.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    gameEndObserver.surrenderNotification();
                }
            });
        } else System.exit(1);

        stackPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(stackPane);
        stage.setTitle("Японский кроссворд!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}