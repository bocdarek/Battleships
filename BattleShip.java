package battleship;

public class BattleShip {

    private final GameField gameField = new GameField();
    private final Fleet fleet = new Fleet(1, 1, 1, 1, 1);
    private final Messenger msg = new Messenger();

    public void game() {
        gameField.printField();
        gameField.placeShips(fleet);
        System.out.println();
        gameStartMessage();
        takeShot();
    }

    private void gameStartMessage() {
        System.out.println("The game starts!");
        System.out.println();
        gameField.printField();
    }

    private void takeShot() {
        System.out.println();
        System.out.println("Take a shot!");
        String[] coordinate = gameField.requestCoordinate();
        int col = gameField.getColNames().indexOf(coordinate[0]);
        int row = gameField.getRowNames().indexOf(coordinate[1]);
        gameField.printField();
        System.out.println();
        if (gameField.getFields()[row][col].equals("O")) {
            gameField.getFields()[row][col] = "X";
            gameField.printField();
            System.out.println("\nYou hit a ship!");
        } else {
            gameField.getFields()[row][col] = "M";
            gameField.printField();
            System.out.println("\nYou missed!");
        }
    }
}

