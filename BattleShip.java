package battleship;

public class BattleShip {

    private final GameField gameField = new GameField();
    private final Fleet fleet = new Fleet(1, 1, 1, 1, 1);

    public void game() {
        gameField.printWithoutFog();
        gameField.placeShips(fleet);
        System.out.println();
        gameStartMessage();
        takeShot();
        System.out.println();
        gameField.printWithoutFog();
    }

    private void gameStartMessage() {
        System.out.println("The game starts!");
        System.out.println();
        gameField.printWithFog();
    }

    private void takeShot() {
        System.out.println();
        System.out.println("Take a shot!");
        String[] coordinate = gameField.requestCoordinate();
        int col = gameField.getColNames().indexOf(coordinate[0]);
        int row = gameField.getRowNames().indexOf(coordinate[1]);
        if (gameField.getFields()[row][col].equals("O")) {
            gameField.getFields()[row][col] = "X";
            gameField.printWithFog();
            System.out.println("\nYou hit a ship!");
        } else {
            gameField.getFields()[row][col] = "M";
            gameField.printWithFog();
            System.out.println("\nYou missed!");
        }
    }
}

