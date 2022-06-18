package battleship;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinatesParser {

    private static final Pattern coordinatesPattern = Pattern.compile("^[A-J][1-9]0?\\s+[A-J][1-9]0?$");
    private static final Pattern coordinatePattern = Pattern.compile("^[A-J][1-9]0?$");

    public static String[] parseCoordinates(String input) {
        Matcher matcher = coordinatesPattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        String[] splitCoordinates = input.split("\\s+");
        int intCol1 = Integer.parseInt(splitCoordinates[0].substring(1));
        int intCol2 = Integer.parseInt(splitCoordinates[1].substring(1));
        if (intCol1 > 10 || intCol2 > 10) {
            return null;
        }
        String row1 = splitCoordinates[0].substring(0, 1);
        String row2 = splitCoordinates[1].substring(0, 1);
        String[] coordinates = new String[]{row1, row2, "0", "0"};
        Arrays.sort(coordinates);
        coordinates[0] = String.valueOf(Math.min(intCol1, intCol2));
        coordinates[1] = String.valueOf(Math.max(intCol1, intCol2));
        return coordinates;
    }

    public static String[] parseCoordinate(String input) {
        Matcher matcher = coordinatePattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        String row = input.substring(0, 1);
        String col = input.substring(1);
        String[] coordinate = new String[]{col, row};
        if (Integer.parseInt(coordinate[0]) > 10) {
            return null;
        }
        return coordinate;
    }
}
