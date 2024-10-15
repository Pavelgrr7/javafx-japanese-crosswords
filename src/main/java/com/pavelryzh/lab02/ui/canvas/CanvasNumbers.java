package com.pavelryzh.lab02.ui.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

public class CanvasNumbers {

    private static int MAXROWNUMS = 2;
    private static int MAXCOLNUMS = 2;
    private static final int XPADDING = 40;
    private static final int YPADDING = 30;
    private final List<List<Integer>> rowSequences;
    private final List<List<Integer>> columnSequences;
    private final int[] currentRowCounts;
    private final int[] currentColCounts;
    private final int rows;
    private final int cols;

    public CanvasNumbers(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        rowSequences = new ArrayList<>(rows);
        columnSequences = new ArrayList<>(cols);

        for (int i = 0; i < rows; i++) {
            rowSequences.add(new ArrayList<>());
        }

        for (int j = 0; j < cols; j++) {
            columnSequences.add(new ArrayList<>());
        }

        currentRowCounts = new int[rows];
        currentColCounts = new int[cols];
    }

    public void addElement(int i, int j, int value) {
        if (value == 49) {
            currentRowCounts[i]++;
            currentColCounts[j]++;
        } else {
            // 0 -> фиксируем текущие последовательности, если они существуют
            if (currentRowCounts[i] > 0) {
                rowSequences.get(i).add(currentRowCounts[i]);
                if (MAXROWNUMS < rowSequences.get(i).size()) MAXROWNUMS = rowSequences.get(i).size();
                currentRowCounts[i] = 0;
            }
            if (currentColCounts[j] > 0) {
                columnSequences.get(j).add(currentColCounts[j]);
                if (MAXCOLNUMS < columnSequences.get(i).size()) MAXCOLNUMS = columnSequences.get(i).size();
                currentColCounts[j] = 0;
            }
        }
    }

    // фиксиация оставшиеся последовательности
    public void finishProcessing() {
        for (int i = 0; i < rows; i++) {
            if (currentRowCounts[i] > 0) {
                rowSequences.get(i).add(currentRowCounts[i]);
            }
        }

        for (int j = 0; j < cols; j++) {
            if (currentColCounts[j] > 0) {
                columnSequences.get(j).add(currentColCounts[j]);
            }
        }
    }

    public List<List<Integer>> getRowSequences() {
        return rowSequences;
    }

    public List<List<Integer>> getColumnSequences() {
        return columnSequences;
    }

    public void drawNumbers(GraphicsContext gc) {

        gc.setFont(new Font("Consolas", 19));

        int numSize = PADDING / 2 - 5;
        int count = 0;

        for (int i = PADDING; i < CANVAS_WIDTH; i += WIDTH) {
            while (columnSequences.get(count).size() < MAXCOLNUMS) {
                columnSequences.get(count).add(0);
            }
            int k = 0;

            for (int j = 10; j < PADDING; j += numSize) {
                if (columnSequences.get(count).get(k) == 0) gc.fillText(" ", i, j);
                else gc.fillText(String.valueOf(columnSequences.get(count).get(k)), i + XPADDING, j + YPADDING);
                k++;
            }
            if (count < 4) count++;
        }
    }
}