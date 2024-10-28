package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.ui.CellWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import static com.pavelryzh.lab02.Resources.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

public class CanvasCellWidget implements CellWidget {

    private final int x;
    private final int y;
    private final Canvas canvas;
    private final GraphicsContext gc;

    public CanvasCellWidget(int x, int y, Canvas canvas) {
        this.x = x + PADDING;
        this.y = y + PADDING;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

    }

    @Override
    public void setState(State state) {
        if (CanvasFieldWidget.fieldState == FieldState.ACTIVE) {
            drawField();
            drawEmpty();
        }
    }

    @Override
    public void updateState(State state) {
        if (CanvasFieldWidget.fieldState == FieldState.ACTIVE) {
            drawField();
            switch (state) {
                case EMPTY, FILLED:
                    drawEmpty();
                    break;
                case OPEN_RIGHT, OPEN_WRONG:
                    drawFilled();
                    break;
                case NULL:
                    clear();
                    break;
                case POINT:
                    drawEmpty();
                    drawPoint();
                    break;
                default:
                    clear();
                    System.err.println("Unknown state: " + state);
                    break;
            }
        }
    }

    private void clear() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    void drawEmpty() {
        gc.stroke();
        gc.clearRect(x, y, CELL_WIDTH + 1, CELL_HEIGHT + 1);
        gc.strokeRect(x, y, CELL_WIDTH, CELL_HEIGHT);
    }

    void drawPoint() {
        gc.stroke();
        gc.strokeOval(x + (double) CELL_WIDTH /4, y + (double) CELL_HEIGHT /4, CELL_WIDTH /2, CELL_HEIGHT /2);
    }
    void drawFilled() {
        gc.stroke();
        gc.clearRect(x, y, CELL_WIDTH + 1, CELL_HEIGHT + 1);
        gc.fillRect(x, y, CELL_WIDTH -1 , CELL_HEIGHT - 1);
    }

    void drawField(){
        gc.strokeLine(PADDING,0, PADDING, canvas.getWidth());
        gc.strokeLine(0, PADDING, canvas.getHeight(), PADDING);
        gc.stroke();
    }
}
