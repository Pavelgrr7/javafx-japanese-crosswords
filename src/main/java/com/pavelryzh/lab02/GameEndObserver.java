package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import javafx.scene.control.Alert;

import static com.pavelryzh.lab02.Resources.countFilled;
import static com.pavelryzh.lab02.ui.FieldWidget.FieldState.INACTIVE;
import static com.pavelryzh.lab02.ui.FieldWidget.FieldState.WIN;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_HEIGHT;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_WIDTH;

public class GameEndObserver implements EndObserver {
    int countRight = 0;
    int countWrong = 0;
    CellWidget.State state;
    CellWidget.State[][] cells;
    FieldWidget fieldWidget;
    public GameEndObserver(FieldWidget fw, CellWidget.State[][] cells) {
        this.fieldWidget = fw;
        this.cells = cells;
        System.out.println(countFilled);
    }

    @Override
    public void cellStateChanged(CellWidget.State state) {
        this.state = state;
        switch(state) {
            case CellWidget.State.FILLED -> countRight++;
            case CellWidget.State.EMPTY -> countWrong++;
            case CellWidget.State.OPEN_RIGHT -> countRight--;
            case CellWidget.State.OPEN_WRONG -> countWrong--;
        }
        if (Math.abs(countRight) == countFilled && countWrong == 0){
            victoryNotification();
        }
        System.out.println("counts:" + countRight + " " + countWrong);
    }

    @Override
    public void victoryNotification() {
        for(int i = 0; i < FIELD_WIDTH; i++) {
            for(int j = 0; j < FIELD_HEIGHT; j++) {
                if (cells[i][j] == CellWidget.State.POINT) {
                    System.out.println("POINT: " + i + " " + j);
                    cells[i][j] = CellWidget.State.EMPTY;

                    fieldWidget.updateState(new FieldWidget.State(cells), i,j);
                } else if (cells[i][j] == CellWidget.State.FILLED)
                    cells[i][j] = CellWidget.State.OPEN_RIGHT; fieldWidget.updateState(new FieldWidget.State(cells), i,j);
            }
        }
//        System.out.println(Arrays.deepToString(cells));

        fieldWidget.setFieldState(WIN);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Поздравляем!");
        alert.setHeaderText("Отлично! Все клетки закрашены правильно!");
        alert.setContentText("Наслаждайтесь картинкой!");
        alert.showAndWait();
    }

    @Override
    public void surrenderNotification() {
        fieldWidget.setFieldState(INACTIVE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(":(");
        alert.setHeaderText("Ваш счёт:");
        alert.setContentText(String.format("Правильно закрашенных клеток: %s из %s\n Неправильно закрашенных клеток: %s", Math.abs(countRight), countFilled, Math.abs(countWrong)));
        alert.showAndWait();
    }

}
