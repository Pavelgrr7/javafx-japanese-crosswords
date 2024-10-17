package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.ui.CellWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import static com.pavelryzh.lab02.Resources.PADDING;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

public class CanvasCellWidget implements CellWidget {

    private final int x;
    private final int y;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private OnClickListener listener;

    public CanvasCellWidget(int x, int y, Canvas canvas) {
        this.x = x + PADDING;
        this.y = y + PADDING;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
//        canvas.setOnMouseClicked( e -> {
//                    if (listener != null &&
//                            e.getX() > x * WIDTH && e.getX() < (x + 1) * WIDTH &&
//                            e.getY() > y * HEIGHT && e.getY() < (y + 1) * HEIGHT) {
//                        listener.onClick();
//                    }
//        });
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setState(State state) {

        if (CanvasFieldWidget.fieldState == FieldState.ACTIVE) {
            drawField();
            switch (state) {
                case EMPTY:
                    drawEmpty();
                    break;
                case FILLED:
                    drawFilled();
                    break;
                case NULL:
                    clear();
                    break;
                default:
                    clear();
                    System.err.println("Unknown state: " + state);
            }
        }
    }

    private void clear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    void drawEmpty() {
        gc.strokeRect(x, y, WIDTH, HEIGHT);
    }

    void drawFilled() {
        gc.fillRect(x, y, WIDTH, HEIGHT);
    }

    void drawField(){
        gc.strokeLine(PADDING,0, PADDING, canvas.getWidth());
        gc.strokeLine(0, PADDING, canvas.getHeight(), PADDING);
    }
}
