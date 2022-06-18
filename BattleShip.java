package battleship;

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
        while (true) {
            System.out.println("\nTake a shot!");
            String[] coordinate = gameField.requestCoordinate();
            int col = gameField.getColNames().indexOf(coordinate[0]);
            int row = gameField.getRowNames().indexOf(coordinate[1]);
            boolean isHit = takeShot(row, col);
            if (isHit && isSunk(row, col)) {
                sunkShips++;
                if (sunkShips == fleet.getShips().size()) {
                    break;
                }
                msg.sunkShipMessage();
            } else if (isHit) {
                msg.hitMessage();
            } else {
                msg.missMessage();
            }
        }
        msg.sunkAllShipsMessage();
    }

    private void gameStartMessage() {
        System.out.println("The game starts!");
        System.out.println();
        gameField.printWithFog();
    }

    private boolean takeShot(int row, int col) {
        if (gameField.getFields()[row][col].equals("O")) {
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

