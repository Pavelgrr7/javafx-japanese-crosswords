package com.pavelryzh.lab02.ui.canvas;

import com.pavelryzh.lab02.ui.CellWidget;
import javafx.scene.canvas.GraphicsContext;

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
                System.out.println("WIP");
                //CanvasFieldWidget.HEIGHT;
                //CanvasFieldWidget.WIDTH
                //todo
                break;
            case FILLED:
                //todo 2
                draw();
                break;
            default:
                System.out.println("ERROR");
        }
    }

    private void clear() {
        gc.clearRect(0, 0, CanvasFieldWidget.WIDTH, CanvasFieldWidget.HEIGHT);
    }

    void draw() {

    }
}
