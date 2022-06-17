package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameField {

    private final Scanner sc = new Scanner(System.in);
    private final Messenger msg = new Messenger();
    private final int DIM = 10;
    private final String[][] fields = new String[DIM][DIM];
    private final ArrayList<String> colNames = new ArrayList<>(
            Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    private final ArrayList<String> rowNames = new ArrayList<>(
            Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
    private final String shipSymbol = "O";

    public GameField() {
        for (String[] row : fields) {
            Arrays.fill(row, "~");
        }
    }

    public void printField() {
        System.out.println("  " + String.join(" ", colNames));
        for (int i = 0; i < DIM; i++) {
            System.out.println(rowNames.get(i) + " " + String.join(" ", fields[i]));
        }
    }

    public void placeShips(Fleet fleet) {
        for (Ship ship : fleet.getShips()) {
            placeShip(ship);
            printField();
        }
    }

    private void placeShip(Ship ship) {
        String[] shipPosition = requestPosition(ship);  // [col1, col2, row1, row2] (sorted String array)
        placeShipOnField(shipPosition);
    }

    private String[] requestPosition(Ship ship) {
        System.out.printf("%nEnter the coordinates of the %s (%s cells):%n%n",
                ship.getName(), ship.getLength());
        String[] coordinates;
        do {
            String input = sc.nextLine().trim().toUpperCase();
            System.out.println();
            coordinates = CoordinatesParser.parseCoordinates(input);
        } while (!areCoordinatesValid(coordinates, ship));
        return coordinates;
    }

    private void placeShipOnField(String[] coordinates) {
        int colIndexStart = colNames.indexOf(coordinates[0]);
        int colIndexStop = colNames.indexOf(coordinates[1]);
        int rowIndexStart = rowNames.indexOf(coordinates[2]);
        int rowIndexStop = rowNames.indexOf(coordinates[3]);
        if (colIndexStart == colIndexStop) {    // vertical position
            for (int row = rowIndexStart; row <= rowIndexStop; row++) {
                fields[row][colIndexStart] = shipSymbol;
            }
        } else {    // horizontal position
            for (int col = colIndexStart; col <= colIndexStop; col++) {
                fields[rowIndexStart][col] = shipSymbol;
            }
        }
    }

    private boolean areCoordinatesValid(String[] coordinates, Ship ship) {
        if (coordinates == null) {
            msg.formatErrorMessage();
            return false;
        }
        int colIndexStart = colNames.indexOf(coordinates[0]);
        int colIndexStop = colNames.indexOf(coordinates[1]);
        int rowIndexStart = rowNames.indexOf(coordinates[2]);
        int rowIndexStop = rowNames.indexOf(coordinates[3]);
        if (!isStraight(colIndexStart, colIndexStop, rowIndexStart, rowIndexStop)) {
            return false;
        }
        if (!hasCorrectLength(ship, colIndexStart, colIndexStop, rowIndexStart, rowIndexStop)) {
            return false;
        }
        return !hasCollision(colIndexStart, colIndexStop, rowIndexStart, rowIndexStop);
    }

    private boolean isStraight(int col1, int col2, int row1, int row2) {
        if (col1 != col2 && row1 != row2) {
            msg.locationErrorMessage();
            return false;
        }
        return true;
    }

    private boolean hasCorrectLength(Ship ship, int col1, int col2, int row1, int row2) {
        int shipLength = ship.getLength();
        if (!(col2 - col1 + 1 == shipLength ||
                row2 - row1 + 1 == shipLength)) {
            msg.lengthErrorMessage(ship);
            return false;
        }
        return true;
    }

    private boolean hasCollision(int col1, int col2, int row1, int row2) {
        for (int col = col1 - 1; col <= col2 + 1; col++) {
            if (col < 0 || col > 9) {
                continue;
            }
            if ((row1 > 0 && fields[row1 - 1][col].equals(shipSymbol)) ||
                    (row2 < 9 && fields[row2 + 1][col].equals(shipSymbol)))  {
                msg.collisionErrorMessage();
                return true;
            }
        }
        for (int row = row1 - 1; row <= row2 + 1; row++) {
            if (row < 0 || row > 9) {
                continue;
            }
            if ((col1 > 0 && fields[row][col1 - 1].equals(shipSymbol)) ||
                    (col2 < 9 && fields[row][col2 + 1].equals(shipSymbol)))  {
                msg.collisionErrorMessage();
                return true;
            }
        }
        return false;
    }
}
