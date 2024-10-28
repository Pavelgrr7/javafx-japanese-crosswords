package com.pavelryzh.lab02.ui;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public interface FieldWidget {

    void setState(State state);
    void setFieldState(FieldState fieldState);
    void setOnCellClickListener(OnCellClickListener listener);
    FieldState getFieldState();
    void updateState(State state, int i, int j);

    interface OnCellClickListener {
        void onClick(int x, int y, MouseButton button);
    }

    enum FieldState{
        INACTIVE,
        ACTIVE,
        WIN,
    }

    record State(
            CellWidget.State[][] cells
    ){
    }

    void drawNums(GraphicsContext gc);
}
