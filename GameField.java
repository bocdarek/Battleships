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

    private boolean areCoordinatesValid(String[] coordinates, Ship ship) {
        if (coordinates == null) {
            msg.formatErrorMessage();
            return false;
        }
        int columnIndexStart = colNames.indexOf(coordinates[0]);
        int columnIndexStop = colNames.indexOf(coordinates[1]);
        int rowIndexStart = rowNames.indexOf(coordinates[2]);
        int rowIndexStop = rowNames.indexOf(coordinates[3]);
        if (columnIndexStart != columnIndexStop && rowIndexStart != rowIndexStop) {
            msg.locationErrorMessage();      // check if not diagonal coordinates
            return false;
        }
        int shipLength = ship.getLength();
        if (!(columnIndexStop - columnIndexStart + 1 == shipLength ||
                rowIndexStop - rowIndexStart + 1 == shipLength)) {
            msg.lengthErrorMessage(ship);       // check if correct length
            return false;
        }
        // check if no collision

        for (int i = columnIndexStart - 1; i <= columnIndexStop + 1; i++) {
            if (i < 0 || i > 9) {
                continue;
            }
            if ((rowIndexStart > 0 && fields[rowIndexStart - 1][i].equals(shipSymbol)) ||
                    (rowIndexStop < 9 && fields[rowIndexStop + 1][i].equals(shipSymbol)))  {
                msg.collisionErrorMessage();
                return false;
            }
        }

        for (int i = rowIndexStart - 1; i <= rowIndexStop + 1; i++) {
            if (i < 0 || i > 9) {
                continue;
            }
            if ((columnIndexStart > 0 && fields[columnIndexStart - 1][i].equals(shipSymbol)) ||
                    (columnIndexStop < 9 && fields[columnIndexStop + 1][i].equals(shipSymbol)))  {
                msg.collisionErrorMessage();
                return false;
            }
        }
        return true;

    }

    private void placeShipOnField(String[] coordinates) {
        int columnIndexStart = colNames.indexOf(coordinates[0]);
        int columnIndexStop = colNames.indexOf(coordinates[1]);
        int rowIndexStart = rowNames.indexOf(coordinates[2]);
        int rowIndexStop = rowNames.indexOf(coordinates[3]);
        if (columnIndexStart == columnIndexStop) {    // vertical position
            for (int row = rowIndexStart; row <= rowIndexStop; row++) {
                fields[row][columnIndexStart] = shipSymbol;
            }
        } else {    // horizontal position
            for (int col = columnIndexStart; col <= columnIndexStop; col++) {
                fields[rowIndexStart][col] = shipSymbol;
            }
        }
    }
}
