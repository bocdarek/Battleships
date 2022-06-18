package battleship;

import java.util.Scanner;

public class BattleShip {

    private final Messenger msg = Messenger.getInstance();
    private final Scanner sc = GameField.getScanner();

    public void game() {

        // set up Players
        Player player1 = new Player(new GameField(1), new Fleet(1, 1, 1, 1, 1));
        Player player2 = new Player(new GameField(2), new Fleet(1, 1, 1, 1, 1));

        // game
        boolean isGameFinished;
        while (true) {

            // 1st player turn
            isGameFinished = nextTurn(player1, player2);
            if (isGameFinished) {
                break;
            }

            // 2nd player turn
            isGameFinished = nextTurn(player2, player1);
            if (isGameFinished) {
                break;
            }
        }
    }

    // return true id game is over after this turn
    private boolean nextTurn(Player player, Player opponent) {

        displayBoards(player, opponent);

        // make a shot
        String[] coordinate = opponent.getGameField().requestCoordinate();
        int col = opponent.getGameField().getColNames().indexOf(coordinate[0]);
        int row = opponent.getGameField().getRowNames().indexOf(coordinate[1]);

        // evaluate the shot
        boolean isHit = takeShot(opponent.getGameField(), row, col);
        String trace = String.format("%d%d", row, col);
        if (isHit && isSunk(opponent.getGameField(), row, col)) {
            if (!opponent.getAlreadyHitFields().contains(trace)) {
                opponent.updateAlreadyHitFields(trace);
                opponent.addSunkShip();
            }
            if (opponent.getSunkShips() == opponent.getFleet().getShips().size()) {
                msg.sunkAllShipsMessage();
                return true;
            }
            msg.sunkShipMessage();
        } else if (isHit) {
            opponent.updateAlreadyHitFields(trace);
            msg.hitMessage();
        } else {
            msg.missMessage();
        }

        clearScreen();
        return false;
    }

    private void displayBoards(Player player, Player opponent) {
        opponent.getGameField().printWithFog();
        msg.boardSeparationLine();
        player.getGameField().printWithoutFog();
        msg.turnInformation(player.getGameField().getId());
    }

    private void clearScreen() {
        msg.askToClearMessage();
        sc.nextLine();
        msg.clearScreen();
    }

    private boolean takeShot(GameField gameField, int row, int col) {
        if (gameField.getFields()[row][col].equals("O")) {
            gameField.getFields()[row][col] = "X";
            return true;
        } else {
            gameField.getFields()[row][col] = "M";
            return false;
        }
    }

    private boolean isSunk(GameField gameField, int row, int col) {
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

