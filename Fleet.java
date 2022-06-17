package battleship;

import java.util.ArrayList;

public class Fleet {

    private ArrayList<Ship> ships;
    private final int numberOfAircraftCarriers;
    private final int numberOfBattleships;
    private final int numberOfSubmarines;
    private final int numberOfCruisers;
    private final int numberOfDestroyers;

    public Fleet(int numberOfAircraftCarriers,
                 int numberOfBattleships,
                 int numberOfSubmarines,
                 int numberOfCruisers,
                 int numberOfDestroyers) {
        this.numberOfAircraftCarriers = numberOfAircraftCarriers;
        this.numberOfBattleships = numberOfBattleships;
        this.numberOfSubmarines = numberOfSubmarines;
        this.numberOfCruisers = numberOfCruisers;
        this.numberOfDestroyers = numberOfDestroyers;
        createFleet();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    private void createFleet() {
        ships = new ArrayList<>();
        for (int i = 0; i < numberOfAircraftCarriers; i++) {
            ships.add(Ship.AIRCRAFT_CARRIER);
        }
        for (int i = 0; i < numberOfBattleships; i++) {
            ships.add(Ship.BATTLESHIP);
        }
        for (int i = 0; i < numberOfSubmarines; i++) {
            ships.add(Ship.SUBMARINE);
        }
        for (int i = 0; i < numberOfCruisers; i++) {
            ships.add(Ship.CRUISER);
        }
        for (int i = 0; i < numberOfDestroyers; i++) {
            ships.add(Ship.DESTROYER);
        }
    }
}
