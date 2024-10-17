package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasNumbers;
import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Resources {

    public static int CELL_WIDTH;
    public static int CELL_HEIGHT;
    private File file;
    private static InputStreamReader reader;
    public static CanvasNumbers numbers;
    private static CellWidget.State[][] cellWidgetStates;
    private static CellWidget.State[] currCellState;


    public static final int CANVAS_WIDTH = 750;
    public static final int CANVAS_HEIGHT = 750;
    public static int WIDTH;
    public static int HEIGHT;
    public static int PADDING;

    public Resources(File file) {
        this.file = file;
        try {
            getSize();
            System.out.println(Arrays.deepToString(cellWidgetStates));
            PADDING = countPadding();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("PADDING " + PADDING);
    }

    public void getSize() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String firstLine = bufferedReader.readLine();  // Читаем первую строку
        if (firstLine != null) {
            WIDTH = firstLine.replaceAll("[^01]", "").length();  // Считаем длину строки без пробелов и символов новой строки
        } else {
            throw new IOException("Файл пустой");
        }

        HEIGHT = 1;  // Первая строка уже прочитана
        while (bufferedReader.readLine() != null) {
            HEIGHT++;
        }
        bufferedReader.close();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
        reader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);

        // CanvasNumbers с найденными размерами
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
        System.out.printf("%s, \n %s \n", numbers.getRowSequences(), numbers.getColumnSequences());
//        System.out.println(countPadding());
        System.out.printf("addit.: %s %s", Arrays.deepToString(cellWidgetStates), Arrays.deepToString(currCellState));
        return new FieldWidget.State(cellWidgetStates);
    }

    public static int countPadding() {
        int maxNum = 0;
        for (CellWidget.State[] stateArr: cellWidgetStates) {
            int count = 0;
            for (CellWidget.State state : stateArr) {
                if (state != CellWidget.State.EMPTY) {count++;}
                else break;
            }
            maxNum = Math.max(maxNum, count);
        }
        return maxNum;
    }

    public static void setPadding() {
        int PADDING = Resources.countPadding();
        System.out.println("setPadding " + PADDING);
        CELL_WIDTH = (CANVAS_WIDTH - PADDING ) / WIDTH;
        CELL_HEIGHT = (CANVAS_HEIGHT - PADDING ) / HEIGHT;
    }

}
