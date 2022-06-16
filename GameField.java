package battleship;

import java.util.Arrays;

public class GameField {

    private final int DIM = 10;
    private final String[][] fields = new String[DIM][DIM];
    private final String[] rowNames = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private final String[] colNames = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public GameField() {
        for (String[] row : fields) {
            Arrays.fill(row, "~");
        }
    }

    public void printField() {
        System.out.println("  " + String.join(" ", colNames));
        for (int i = 0; i < DIM; i++) {
            System.out.println(rowNames[i] + " " + String.join(" ", fields[i]));
        }
    }

}
