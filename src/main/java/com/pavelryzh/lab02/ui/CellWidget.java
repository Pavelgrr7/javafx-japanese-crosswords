package com.pavelryzh.lab02.ui;

public interface CellWidget {

    void setState(State state);

    void updateState(State state);

    interface OnClickListener {
        void onClick(int cellX, int cellY);
    }

    enum State {
        EMPTY,
        FILLED,
        POINT, OPEN_WRONG, OPEN_RIGHT, NULL
    }
}
