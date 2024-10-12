package com.pavelryzh.lab02.ui;

public interface FieldWidget {

    void setState(State state);

    void setOnCellClickListener(OnCellClickListener listener);

    interface OnCellClickListener {
        void onClick(int x, int y);
    }

    enum fieldState{
        ACTIVE,
        WIN,
    }

    record State(
            CellWidget.State[][] cells
    ) {
    }
}
