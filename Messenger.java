package battleship;

public class Messenger {

    private static Messenger instance;

    private Messenger() {}

    public static Messenger getInstance() {
        if (instance == null) {
            instance = new Messenger();
        }
        return instance;
    }

    public void format1ErrorMessage() {
        System.out.println("Error! Wrong format. Correct format: '[A-J][1-10] [A-J][1-10]'! Try again:");
        System.out.println();
    }

    public void format2ErrorMessage() {
        System.out.println("Error! You entered the wrong coordinates! Try again:");
    }

    public void lengthErrorMessage(Ship ship) {
        System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
        System.out.println();
    }

    public void locationErrorMessage() {
        System.out.println("Error! Wrong ship location! Try again:");
        System.out.println();
    }

    public void collisionErrorMessage() {
        System.out.println("Error! You placed it too close to another one. Try again:");
        System.out.println();
    }

    public void hitMessage() {
        System.out.println("You hit a ship!");
    }

    public void missMessage() {
        System.out.println("You missed!");
    }

    public void sunkShipMessage() {
        System.out.println("You sank a ship!");
    }

    public void sunkAllShipsMessage() {
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public void initiateBoardMessage(int id) {
        System.out.printf("Player %d, place your ships on the game field%n%n", id);
    }

    public void askToClearMessage() {
        System.out.println("\nPress Enter and pass the move to another player");
    }

    public void clearScreen() {
        System.out.println("\n".repeat(30));
    }

    public void boardSeparationLine() {
        System.out.println("-".repeat(21));
    }

    public void turnInformation(int id) {
        System.out.printf("%nPlayer %d, it's your turn:%n", id);
    }
}
