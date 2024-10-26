package com.pavelryzh.lab02.ui;

public interface CellWidget {
    void setOnClickListener(OnClickListener listener);

    void setState(State state);

    interface OnClickListener {
        //void onClick();

        void onClick(int cellX, int cellY);
    }

    enum State {
        EMPTY,
        FILLED,
        POINT, NULL
    }
}
