package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.Resources;
import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static com.pavelryzh.lab02.Resources.*;

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

    public CanvasFieldWidget(Canvas canvas) {
        Resources.setPadding();
        FIELD_WIDTH = Resources.WIDTH;
        FIELD_HEIGHT = Resources.HEIGHT;
        WIDTH = Resources.CELL_WIDTH;
        HEIGHT = Resources.CELL_HEIGHT;

        CanvasFieldWidget.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        cells = new CellWidget[FIELD_WIDTH][FIELD_HEIGHT];

        for(int i = 0; i < FIELD_WIDTH; i++) {
            for(int j = 0; j < FIELD_HEIGHT; j++) {
                cells[i][j] = new CanvasCellWidget(i * WIDTH, j * HEIGHT, canvas);
            }
        }
        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            int cellX = (int) (mouseX) - PADDING;
            int cellY = (int) (mouseY) - PADDING;
            if (listener != null) {
                listener.onClick(cellX / CELL_WIDTH, cellY / CELL_HEIGHT, event.getButton());
            } else System.out.println("listener is null.");
        });
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

    @Override
    public FieldState getFieldState() {
        return CanvasFieldWidget.fieldState;
    }

    @Override
    public void updateState(State state, int i, int j) {
        cells[i][j].updateState(state.cells()[j][i]);
    }

    public void drawNums(GraphicsContext gc) {
        numbers.drawNumbers(gc);
    }
}
