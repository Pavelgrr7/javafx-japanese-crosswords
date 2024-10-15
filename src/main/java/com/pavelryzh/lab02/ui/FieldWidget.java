package com.pavelryzh.lab02.ui;
//import com.pavelryzh.lab02.HelloApplication.FieldState.Notification;

import javafx.scene.canvas.GraphicsContext;

public interface FieldWidget {

    void setState(State state);
    void setFieldState(FieldState fieldState);
//    void drawField()

    void setOnCellClickListener(OnCellClickListener listener);

    interface OnCellClickListener {
        void onClick(int x, int y);
    }

    enum FieldState{
        INACTIVE,
        ACTIVE,
        WIN,
    }

    record State(
            CellWidget.State[][] cells
    ) {
        public record Notification(int code) {
        }
    }

    void drawNums(GraphicsContext gc);
}
