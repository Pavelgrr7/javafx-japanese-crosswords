package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasNumbers;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.pavelryzh.lab02.ui.FieldWidget.FieldState.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_SIZE;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_WIDTH;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_HEIGHT;

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
        Button uploadButton = new Button("Upload");
        //fieldWidget.setFieldState(INACTIVE);

//                            state.CellWidget.State[i] = new CellWidget.State[] {CellWidget.State.EMPTY, CellWidget.State.EMPTY, CellWidget.State.EMPTY},
//                        }
//                new FieldWidget.State.Notification(0)
        //System.out.println(Arrays.deepToString(cellWidgetState));

        uploadButton.setOnAction(actionEvent -> {

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
                    uploadField(selectedFile);
//                    new FieldWidget.State = INACTIVE;
                } catch (IOException e) {
                    System.out.println("Error occurred while reading file");
                    e.printStackTrace();
                }
            }
        });
        vBox.getChildren().add(uploadButton);
        Scene scene = new Scene(vBox);
        stage.setTitle("WIP");
        stage.setScene(scene);
        stage.show();


    }

    void uploadField(File file) throws IOException {

        BufferedInputStream test = new BufferedInputStream(new FileInputStream(file.getPath()));
        InputStreamReader reader = new InputStreamReader(test, StandardCharsets.UTF_8);

        CellWidget.State[][] cellWidgetStates = new CellWidget.State[FIELD_WIDTH][FIELD_HEIGHT];
        CellWidget.State[] currCellState = new CellWidget.State[FIELD_SIZE];

        int cell;
        int i = 0;
        int j = 0;
        CanvasNumbers numbers = new CanvasNumbers(FIELD_WIDTH, FIELD_HEIGHT);
        while ((cell = reader.read()) != -1) {
            char fileCell = (char) cell;

            // символы новой строки и возврата каретки
            if (fileCell == '\n' || fileCell == '\r') {
                continue;
            }
//            System.out.printf("Column: %d, Row: %d, Element: %c\n", i, j, fileCell);

            // 49 - еденица :)
            if (fileCell == 49) {
                //System.out.println((int)(fileCell));
                currCellState[i] = CellWidget.State.FILLED;
            } else {
                currCellState[i] = CellWidget.State.EMPTY;
            }
            numbers.addElement(i, j, fileCell);
            i++;
            if (i == FIELD_WIDTH) {
                i = 0;
                cellWidgetStates[j] = Arrays.copyOf(currCellState, currCellState.length);
                j++;
            }
        }
        numbers.finishProcessing();
        System.out.printf("%s, %s \n", numbers.getRowSequences(), numbers.getColumnSequences());
        fieldWidget.setState(new FieldWidget.State(cellWidgetStates));
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