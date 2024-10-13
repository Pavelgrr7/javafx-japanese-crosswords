package com.pavelryzh.lab02.ui;
//import com.pavelryzh.lab02.HelloApplication.FieldState.Notification;

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
        WIN, input,
    }

    record State(
            CellWidget.State[][] cells
    ) {
        public record Notification(int code) {
        }
    }
}
