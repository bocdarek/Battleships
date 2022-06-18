package battleship;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private final GameField gameField;
    private final Fleet fleet;

    private int sunkShips = 0;
    private final Set<String> alreadyHitFields = new HashSet<>();

    public Player(GameField gameField, Fleet fleet) {
        this.gameField = gameField;
        this.fleet = fleet;
        gameField.placeShips(fleet);
    }

    public GameField getGameField() {
        return gameField;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public int getSunkShips() {
        return sunkShips;
    }

    public Set<String> getAlreadyHitFields() {
        return alreadyHitFields;
    }

    public void addSunkShip() {
        sunkShips++;
    }

    public void updateAlreadyHitFields(String field) {
        alreadyHitFields.add(field);
    }
}
