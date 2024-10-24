package com.pavelryzh.lab02.ui.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pavelryzh.lab02.Resources.*;
import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_WIDTH;

public class CanvasNumbers {

    private static int MAXROWNUMS = 0;
    private static int MAXCOLNUMS = 0;
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

//    public CanvasNumbers(File file) {
//        this.file = file;
//
//    }

    public void addElement(int i, int j, int value) {
        if (value == 49) {
            currentRowCounts[i]++;
            currentColCounts[j]++;
        } else {
            // 0 -> фиксируем текущие последовательности, если они существуют
            if (currentRowCounts[i] > 0) {
                rowSequences.get(i).add(currentRowCounts[i]);
                currentRowCounts[i] = 0;
            }
            if (currentColCounts[j] > 0) {
                columnSequences.get(j).add(currentColCounts[j]);
                currentColCounts[j] = 0;
            }
        }
    }

    // фиксиация оставшиеся последовательности
    public void finishProcessing() {
        for (int i = 0; i < rows; i++) {
            if (currentRowCounts[i] > 0) {
                rowSequences.get(i).add(currentRowCounts[i]);
                if (rowSequences.get(i).size() > MAXROWNUMS) MAXROWNUMS = rowSequences.get(i).size();
            }
        }

        for (int j = 0; j < cols; j++) {
            if (currentColCounts[j] > 0) {
                columnSequences.get(j).add(currentColCounts[j]);
                if (columnSequences.get(j).size() > MAXCOLNUMS) MAXCOLNUMS = columnSequences.get(j).size();
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
        int XPADDING = CELL_WIDTH / 2;
        int YPADDING = CELL_HEIGHT / 2;
        int max = Math.max(MAXROWNUMS, MAXCOLNUMS);
        gc.setFont(new Font("Consolas", Math.pow((double) PADDING / max, 0.75)));
        System.out.println(Math.pow(max, 1.3));

        int numSize = (int) ((PADDING * 1.2 +  max) / max);
//        System.out.println("arrays: " + columnSequences + "\n" + rowSequences);
//        System.out.println("Padding + numsize " + PADDING + " " + numSize);
        int count = 0;

        for (int i = PADDING; i + XPADDING < CANVAS_WIDTH; i += CELL_WIDTH) {
            while (columnSequences.get(count).size() < MAXCOLNUMS) {
                columnSequences.get(count).add(0);
            }
            while (rowSequences.get(count).size() < MAXROWNUMS) {
                rowSequences.get(count).add(0);
            }
//            System.out.println(XPADDING + " " + YPADDING);
            int k = 0;
            for (int j = YPADDING; j < PADDING; j += numSize) {
//                System.out.println(j + " and " + i);
                if (columnSequences.get(count).get(k) == 0) gc.fillText(" ", i + XPADDING, j);
                else gc.fillText(String.valueOf(columnSequences.get(count).get(k)), i + XPADDING, j);
                if (rowSequences.get(count).get(k) == 0) gc.fillText(" ", j, i + XPADDING);
                else gc.fillText(String.valueOf(rowSequences.get(count).get(k)), j, i + XPADDING);
                k++;
            }
            if (count < FIELD_WIDTH - 1) count++;
        }
//        for (int i = PADDING; i < CANVAS_HEIGHT; i += HEIGHT) {
//            while (rowSequences.get(count).size() < MAXROWNUMS) {
//                rowSequences.get(count).add(0);
//            }
//            int k = 0;
//
//            for (int j = 10; j < PADDING; j += numSize) {
//                if (rowSequences.get(count).get(k) == 0) gc.fillText(" ", i, j);
//
//                k++;
//            }
//            if (count < 4) count++;
//        }
    }
}