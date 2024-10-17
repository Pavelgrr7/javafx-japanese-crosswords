package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.Resources;
import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.Arrays;

import static com.pavelryzh.lab02.Resources.numbers;

public class CanvasFieldWidget implements FieldWidget {

    private static Canvas canvas;
    private final GraphicsContext gc;
    private final CellWidget[][] cells;
    private OnCellClickListener listener;

    public static int FIELD_WIDTH = Resources.WIDTH;
    public static int FIELD_HEIGHT = Resources.HEIGHT;
    public static FieldState fieldState;
    public static int WIDTH;
    public static int HEIGHT;
    public static Resources res;

    public CanvasFieldWidget(Canvas canvas) {
        Resources.setPadding();
        FIELD_WIDTH = Resources.WIDTH;
        FIELD_HEIGHT = Resources.HEIGHT;
        WIDTH = Resources.CELL_WIDTH;
        HEIGHT = Resources.CELL_HEIGHT;

        System.out.printf("%s %s %s %s \n", FIELD_WIDTH, FIELD_HEIGHT, WIDTH, HEIGHT);
        CanvasFieldWidget.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        cells = new CellWidget[FIELD_WIDTH][FIELD_HEIGHT];

        for(int i = 0; i < FIELD_WIDTH; i++) {
            for(int j = 0; j < FIELD_HEIGHT; j++) {
                cells[i][j] = new CanvasCellWidget(i * WIDTH, j * HEIGHT, canvas);
                final int x = i * WIDTH;
                final int y = j * HEIGHT;
                cells[i][j].setOnClickListener(() -> listener.onClick(x, y));
            }
        }
    }



    @Override
    public void setState(State state) {
        for(int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                if (state == null) {
                    System.out.println("state is null: you probably uploaded a file with wrong field size!");
                } else {
                    cells[i][j].setState(state.cells()[j][i]);
                }
            }
        }
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
