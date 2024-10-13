package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.HelloApplication;
import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class CanvasFieldWidget implements FieldWidget {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final CellWidget[][] cells;

    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    public static final int FIELD_SIZE = 3;
//    public static final int FILLED = 1;
//    public static final int EMPTY = 0;

    public CanvasFieldWidget(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        cells = new CellWidget[FIELD_SIZE][FIELD_SIZE];

        for(int i = 0; i < FIELD_SIZE; i++) {
            for(int j = 0; j < FIELD_SIZE; j++) {
                cells[i][j] = new CanvasCellWidget(i * WIDTH, j * HEIGHT, gc);
            }
        }
    }

    @Override
    public void setState(State state) {
        for(int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.println(state.cells()[i][j]);
                cells[i][j].setState(state.cells()[i][j]);
            }
        }
    }

    @Override
    public void setFieldState(FieldState fieldState) {

    }

    @Override
    public void setOnCellClickListener(OnCellClickListener listener) {

    }
}
