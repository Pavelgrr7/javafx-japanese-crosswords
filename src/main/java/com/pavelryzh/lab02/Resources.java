package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasNumbers;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.FIELD_WIDTH;


public class Resources {

    public static int CELL_WIDTH;
    public static int CELL_HEIGHT;
    private File file;
    private static InputStreamReader reader;
    public static CanvasNumbers numbers;
    private static CellWidget.State[][] cellWidgetStates;
    private static CellWidget.State[] currCellState;

    public static int maxCanvasSize = (int) Math.min(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    public static int CANVAS_WIDTH = maxCanvasSize - maxCanvasSize / 8;
    public static int CANVAS_HEIGHT = maxCanvasSize - maxCanvasSize / 8;
    public static int WIDTH;
    public static int HEIGHT;
    public static int PADDING;
    public static FieldWidget.State state;

    public Resources(File file) {
        this.file = file;
        try {
            getSize();
//            System.out.println(Arrays.deepToString(cellWidgetStates));
            state = getState();
            setPadding();
            //PADDING = countPadding();
            getFieldSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("PADDING " + PADDING);
    }

    private static void getFieldSize() {
        CELL_HEIGHT = (CANVAS_HEIGHT - PADDING) / HEIGHT ;
        CELL_WIDTH = (CANVAS_WIDTH - PADDING) / WIDTH;
    }

    public void getSize() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String firstLine = bufferedReader.readLine();  // Читаем первую строку
        if (firstLine != null) {
            WIDTH = firstLine.replaceAll("[^01]", "").length();  // Считаем длину строки без пробелов и символов новой строки
        } else {
            throw new IOException("File is empty!");
        }

        HEIGHT = 1;  // Первая строка уже прочитана
        while (bufferedReader.readLine() != null) {
            HEIGHT++;
        }
        bufferedReader.close();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
        reader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);

        // CanvasNumbers с найденными размерами
        if (HEIGHT < 10 || WIDTH < 10) {
            throw new IllegalStateException("Field is too small!");
        }
        numbers = new CanvasNumbers(WIDTH, HEIGHT);
    }

    // Метод для получения состояния матрицы
    public static FieldWidget.State getState() throws IOException {

        cellWidgetStates = new CellWidget.State[WIDTH][HEIGHT];
        currCellState = new CellWidget.State[WIDTH];

        int cell;
        int i = 0;
        int j = 0;
        while ((cell = reader.read()) != -1) {
            char fileCell = (char) cell;

            // символы новой строки и возврата каретки
            if (fileCell == '\n' || fileCell == '\r') {
                continue;
            }
            // 49 - еденица :)
            if (fileCell == 49) {
                currCellState[i] = CellWidget.State.FILLED;
            } else {
                currCellState[i] = CellWidget.State.EMPTY;
            }
            numbers.addElement(j, i, fileCell);
            i++;
            if (i == WIDTH) {
                i = 0;
                cellWidgetStates[j] = Arrays.copyOf(currCellState, currCellState.length);
                j++;
            }
        }

        numbers.finishProcessing();
//        System.out.printf("%s, \n %s \n", numbers.getRowSequences(), numbers.getColumnSequences());
//        System.out.println(countPadding());
//        System.out.printf("addit.: %s %s", Arrays.deepToString(cellWidgetStates), Arrays.deepToString(currCellState));
        return new FieldWidget.State(cellWidgetStates);
    }

    public static void setPadding() {
        int maxNum = 0;
        for (CellWidget.State[] stateArr : cellWidgetStates) {
            int count = 0;
            for (CellWidget.State state : stateArr) {
                if (state != CellWidget.State.EMPTY) {
                    count++;
                } else break;
            }
            maxNum = Math.max(maxNum, count);
        }
        PADDING = maxNum * CELL_WIDTH;
    }
//        PADDING = countPadding() * CELL_SIZE;
//        System.out.println("setPadding " + PADDING);
//        System.out.println("width " + " " + WIDTH + " " + CANVAS_WIDTH);
//        System.out.println("height " + CANVAS_HEIGHT);
//        CELL_WIDTH = (CANVAS_WIDTH - PADDING  ) / WIDTH;
//        CELL_HEIGHT = (CANVAS_HEIGHT - PADDING ) / HEIGHT;

//
//
//    public static int getMaxRowHints() {
//        int maxRowHints = 0;
//        for (List<Integer> rowSequence : numbers.getRowSequences()) {
//            maxRowHints = Math.max(maxRowHints, rowSequence.size());
//        }
//        return maxRowHints;
//    }
//
//    public static int getMaxColumnHints() {
//        int maxColumnHints = 0;
//        for (List<Integer> colSequence : numbers.getColumnSequences()) {
//            maxColumnHints = Math.max(maxColumnHints, colSequence.size());
//        }
//        return maxColumnHints;
//    }

}
