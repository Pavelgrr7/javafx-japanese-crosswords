package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.ui.CellWidget;
import javafx.scene.canvas.GraphicsContext;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

public class CanvasCellWidget implements CellWidget {

    private final int x;
    private final int y;
    private final GraphicsContext gc;

    public CanvasCellWidget(int x, int y, GraphicsContext gc) {
        this.x = x;
        this.y = y;
        this.gc = gc;
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {

    }

    @Override
    public void setState(State state) {
        switch(state) {
            case EMPTY:
                drawEmpty();
                break;
            case FILLED:
                drawFilled();
                break;
            default:
                System.err.println("Unknown state: " + state);
        }
    }

    private void clear() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    }

    void drawEmpty() {
        gc.strokeRect(x, y, WIDTH, HEIGHT);
    }

    void drawFilled() {
        gc.fillRect(x, y, WIDTH, HEIGHT);
    }
}
