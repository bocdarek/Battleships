package battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleShip {

    private final GameField gameField = new GameField();
    private final Fleet fleet = new Fleet(1, 1, 1, 1, 1);
    private final Messenger msg = Messenger.getInstance();

    public void game() {
        int sunkShips = 0;
        gameField.printWithoutFog();
        gameField.placeShips(fleet);
        System.out.println();
        gameStartMessage();
        gameField.printWithFog();
        System.out.println("\nTake a shot!");
        Set<String> alreadyHitFields = new HashSet<>();
        while (true) {
            String[] coordinate = gameField.requestCoordinate();
            int col = gameField.getColNames().indexOf(coordinate[0]);
            int row = gameField.getRowNames().indexOf(coordinate[1]);
            boolean isHit = takeShot(row, col);
            gameField.printWithFog();
            String id = String.format("%d%d", row, col);
            if (isHit && isSunk(row, col)) {
                if (!alreadyHitFields.contains(id)) {
                    alreadyHitFields.add(id);
                    sunkShips++;
                }
                if (sunkShips == fleet.getShips().size()) {
                    msg.sunkAllShipsMessage();
                    break;
                }
                msg.sunkShipMessage();
            } else if (isHit) {
                alreadyHitFields.add(id);
                msg.hitMessage();
            } else {
                msg.missMessage();
            }
        }
    }

    private void gameStartMessage() {
        System.out.println("The game starts!");
        System.out.println();
    }

    private boolean takeShot(int row, int col) {
        if (gameField.getFields()[row][col].equals("O") || gameField.getFields()[row][col].equals("X")) {
            gameField.getFields()[row][col] = "X";
            return true;
        } else {
            gameField.getFields()[row][col] = "M";
            return false;
        }
    }

    private boolean isSunk(int row, int col) {
        for (int i = row; i >= 0; i--) {
            String field = gameField.getFields()[i][col];
            if (field.equals("O")) {
                return false;
            }
            if (field.matches("^~|M$")) {
                break;
            }
        }
        for (int i = row + 1; i < gameField.getRowNames().size(); i++) {
            String field = gameField.getFields()[i][col];
            if (field.equals("O")) {
                return false;
            }
            if (field.matches("^~|M$")) {
                break;
            }
        }
        for (int i = col; i >= 0; i--) {
            String field = gameField.getFields()[row][i];
            if (field.equals("O")) {
                return false;
            }
            if (field.matches("^~|M$")) {
                break;
            }
        }
        for (int i = col + 1; i < gameField.getColNames().size(); i++) {
            String field = gameField.getFields()[row][i];
            if (field.equals("O")) {
                return false;
            }
            if (field.matches("^~|M$")) {
                break;
            }
        }
        return true;
    }
}

