package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameField {

    private final Scanner sc = new Scanner(System.in);
    private final Messenger msg = Messenger.getInstance();
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

    public ArrayList<String> getColNames() {
        return colNames;
    }

    public ArrayList<String> getRowNames() {
        return rowNames;
    }

    public String[][] getFields() {
        return fields;
    }

    private void printFields(boolean withFog) {
        System.out.println("  " + String.join(" ", colNames));
        for (int i = 0; i < DIM; i++) {
            String row = String.join(" ", fields[i]);
            System.out.println(rowNames.get(i) + " "
                    + (withFog ? row.replace("O", "~") : row));
        }
    }

    public void printWithoutFog() {
        printFields(false);
    }

    public void printWithFog() {
        printFields(true);
    }

    public void placeShips(Fleet fleet) {
        for (Ship ship : fleet.getShips()) {
            placeShip(ship);
            printWithoutFog();
        }
    }

    private void placeShip(Ship ship) {
        String[] shipPosition = requestPosition(ship);
        placeShipOnField(shipPosition);
    }

    private String[] requestPosition(Ship ship) {       // [col1, col2, row1, row2] (sorted String array)
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

    public String[] requestCoordinate() {           // [col, row] (sorted String array)
        String[] coordinate;
        do {
            System.out.println();
            String input = sc.nextLine().trim().toUpperCase();
            System.out.println();
            coordinate = CoordinatesParser.parseCoordinate(input);
        } while (!isCoordinateValid(coordinate));
        return coordinate;
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
            msg.format1ErrorMessage();
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
            for (int row = row1 - 1; row <= row2 + 1; row++) {
                if (col < 0 || col > colNames.size() - 1 || row < 0 || row > rowNames.size() - 1) {
                    continue;
                }
                if (fields[row][col].equals(shipSymbol))  {
                    msg.collisionErrorMessage();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCoordinateValid(String[] coordinate) {
        if (coordinate == null) {
            msg.format2ErrorMessage();
            return false;
        }
//        int col = colNames.indexOf(coordinate[0]);
//        int row = rowNames.indexOf(coordinate[1]);
//        if (fields[row][col].matches("^X|M$")) {
//            msg.format2ErrorMessage();
//            return false;
//        }
        return true;
    }
}
