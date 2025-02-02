package com.pavelryzh.lab02;

import com.pavelryzh.lab02.ui.CellWidget;
import com.pavelryzh.lab02.ui.FieldWidget;
import com.pavelryzh.lab02.ui.canvas.CanvasNumbers;
//import com.pavelryzh.lab02.ui.canvas.CanvasFieldWidget.*;
//import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Resources {

    public static int CELL_WIDTH;
    public static int CELL_HEIGHT;
    private final String str;
    private static InputStreamReader reader;
    public static CanvasNumbers numbers;
    private static CellWidget.State[][] cellWidgetStates;

    public static int maxCanvasSize = (int) Math.min(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    public static int CANVAS_WIDTH = maxCanvasSize - maxCanvasSize / 8;
    public static int CANVAS_HEIGHT = maxCanvasSize - maxCanvasSize / 8;
    public static int WIDTH;
    public static int HEIGHT;
    public static int PADDING;
    public static FieldWidget.State state;
    public static int countFilled;

    public Resources(String str) {
        this.str = str;
        try {
            getSize();
            state = getState();
            setPadding();
            getFieldSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getFieldSize() {
        CELL_HEIGHT = (CANVAS_HEIGHT - PADDING) / HEIGHT;
        CELL_WIDTH = (CANVAS_WIDTH - PADDING) / WIDTH;
    }

    public void getSize() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
        String firstLine = bufferedReader.readLine();
        if (firstLine != null) {
            WIDTH = firstLine.replaceAll("[^01]", "").length();
        } else {
            throw new IOException("Input string is empty!");
        }

        HEIGHT = 1;
        while (bufferedReader.readLine() != null) {
            HEIGHT++;
        }
        bufferedReader.close();

        InputStream bufferedInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        reader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);

        // minimal field size: 10
        if (HEIGHT < 10 || WIDTH < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Uploaded field is too small! Minimal size is 10x10.");
            alert.setContentText("Try to upload larger field.");
            alert.showAndWait();
            throw new IllegalStateException("Field is too small!");

        }
        numbers = new CanvasNumbers(WIDTH, HEIGHT);
    }

    public static FieldWidget.State getState() throws IOException {

        cellWidgetStates = new CellWidget.State[WIDTH][HEIGHT];
        CellWidget.State[] currCellState = new CellWidget.State[WIDTH];

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
                countFilled++;
                currCellState[i] = CellWidget.State.FILLED;
            } else {
                currCellState[i] = CellWidget.State.EMPTY;
            }
            System.out.println(j + " " + i + " " + fileCell);
            numbers.addElement(j, i, fileCell);
            i++;
            if (i == WIDTH) {
                i = 0;
                cellWidgetStates[j] = Arrays.copyOf(currCellState, currCellState.length);
                j++;
            }
        }

        numbers.finishProcessing();
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
        PADDING = (int) Math.max( Math.pow(CANVAS_HEIGHT, 1.04) / Math.pow(HEIGHT, 1.18), CANVAS_WIDTH * 0.2);
    }

}
