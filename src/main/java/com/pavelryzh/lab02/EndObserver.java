package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;

public interface EndObserver {
    void cellStateChanged(CellWidget.State state);
    void victoryNotification();
}
