package com.pavelryzh.lab02.ui.canvas;

//import com.pavelryzh.lab02.Resources;
import com.pavelryzh.lab02.ui.CellWidget;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;


import static com.pavelryzh.lab02.Resources.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.HEIGHT;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.WIDTH;

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
//            if (listener != null &&
//                    e.getX() > x * WIDTH && e.getX() < (x + 1) * WIDTH &&
//                    e.getY() > y * HEIGHT && e.getY() < (y + 1) * HEIGHT) {
//
//                if (e.getButton() == MouseButton.PRIMARY) {
//                    listener.onClick();
//                }
//            }
//        });
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
//        System.out.println("listener!");
//        canvas.setOnMouseClicked(event -> {
//            System.out.println("listener canvas!");
//            double mouseX = event.getX();
//            double mouseY = event.getY();
//
//            int cellX = (int) (mouseX);
//            int cellY = (int) (mouseY);
//
//            // Проверяем, что клик произошел внутри поля
//            if (listener != null && cellX >= 0 && cellX < FIELD_WIDTH && cellY >= 0 && cellY < FIELD_HEIGHT) {
//                if (cellX > PADDING && cellY > PADDING) {
//                    listener.onClick(cellX, cellY);
//                    System.out.println("Клик по клетке на координатах2: " + cellX + ", " + cellY);
//                } else System.out.println(cellX + ", " + cellY);
//
//            } else System.out.println("listener is null.");
//        });
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
        gc.clearRect(x, y, CELL_WIDTH, CELL_HEIGHT);
        gc.strokeRect(x, y, CELL_WIDTH, CELL_HEIGHT);
    }

    void drawPoint() {
        gc.strokeOval(x + (double) CELL_WIDTH /4, y + (double) CELL_HEIGHT /4, CELL_WIDTH /2, CELL_HEIGHT /2);
    }
    void drawFilled() {
        gc.clearRect(x, y, CELL_WIDTH, CELL_HEIGHT);
        gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
    }

    void drawField(){
        gc.strokeLine(PADDING,0, PADDING, canvas.getWidth());
        gc.strokeLine(0, PADDING, canvas.getHeight(), PADDING);
    }
}
