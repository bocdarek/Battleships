package battleship;

public class BattleShip {

    private final GameField gameField = new GameField();
    private final Fleet fleet = new Fleet(1, 1, 1, 1, 1);

    public void game() {
        gameField.printField();
        gameField.placeShips(fleet);
    }
}
