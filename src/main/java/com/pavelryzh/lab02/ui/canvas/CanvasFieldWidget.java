package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CanvasFieldWidget implements FieldWidget {

    private static Canvas canvas;
    private final GraphicsContext gc;
    private final CellWidget[][] cells;
    private OnCellClickListener listener;

    public static final int CANVAS_WIDTH = 700;
    public static final int CANVAS_HEIGHT = 700;
    public static final int FIELD_SIZE = 5;
    public static final int FIELD_WIDTH = 5;
    public static final int FIELD_HEIGHT = 5;
    public static final int PADDING = 100;
    public static FieldState fieldState;
    public static final int WIDTH = (CANVAS_WIDTH - PADDING ) / FIELD_WIDTH;
    public static final int HEIGHT = (CANVAS_HEIGHT - PADDING ) / FIELD_HEIGHT;

    public static CanvasNumbers numbers = new CanvasNumbers(FIELD_WIDTH, FIELD_HEIGHT);
//    public static final int FILLED = 1;
//    public static final int EMPTY = 0;

    public CanvasFieldWidget(Canvas canvas) {
        CanvasFieldWidget.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        cells = new CellWidget[FIELD_SIZE][FIELD_SIZE];

        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                cells[i][j] = new CanvasCellWidget(i * WIDTH, j * HEIGHT, canvas);
                final int x = i * WIDTH;
                final int y = j * HEIGHT;
                cells[i][j].setOnClickListener(() -> listener.onClick(x, y));
            }
        }
    }

    @Override
    public void setState(State state) {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (state == null) {
                    System.out.println("state is null: you probably uploaded a file with wrong field size!");
                } else {
                    cells[i][j].setState(state.cells()[j][i]);
                }
            }
        }
    }


    public static State getStateFrom(File file) throws IOException {

        BufferedInputStream test = new BufferedInputStream(new FileInputStream(file.getPath()));
        InputStreamReader reader = new InputStreamReader(test, StandardCharsets.UTF_8);

        CellWidget.State[][] cellWidgetStates = new CellWidget.State[FIELD_WIDTH][FIELD_HEIGHT];
        CellWidget.State[] currCellState = new CellWidget.State[FIELD_SIZE];

        int cell;
        int i = 0;
        int j = 0;
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
            numbers.addElement(j, i, fileCell);
            i++;
            if (i == FIELD_WIDTH) {
                i = 0;
                cellWidgetStates[j] = Arrays.copyOf(currCellState, currCellState.length);
                j++;
            }
        }
        numbers.finishProcessing();
        System.out.printf("%s, \n %s \n", numbers.getRowSequences(), numbers.getColumnSequences());
        return new FieldWidget.State(cellWidgetStates);
    }

    @Override
    public void setFieldState(FieldState fieldState) {
        CanvasFieldWidget.fieldState = fieldState;
    }

    @Override
    public void setOnCellClickListener(OnCellClickListener listener) {
        this.listener = listener;
    }

    public void drawNums(GraphicsContext gc) {
        numbers.drawNumbers(gc);
    }

}
