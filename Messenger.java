package battleship;

public class Messenger {

    public void formatErrorMessage() {
        System.out.println("Error! Wrong format. Correct format: '[A-J][1-10] [A-J][1-10]'! Try again:");
        System.out.println();
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
}
