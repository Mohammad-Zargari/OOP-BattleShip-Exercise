package org.example;

public class Coordinate {
    public boolean isValidCoordinate(String input, int size) {
        if (input.length() != 2 && input.length() != 3) {
            return false;
        }

        char col = input.charAt(0);
        String rowstr = input.substring(1);

        if (!rowstr.matches("\\d+")) {
            return false;
        }

        int row = Integer.parseInt(rowstr);

        if (input.length() == 2) {
            return (col >= 'A' && col <= 'J') && (row >= 0 && row <= size);
        }

        if (input.length() == 3) {
            return (col >= 'A' && col <= 'J') && (row >= 1 && row <= size);
        }

        return false;
    }

    public boolean isValidhorizontal(String input) {
        return input.equals("YES") || input.equals("NO");
    }
public  int col (String word) {

    return  word.charAt(0) -'A';
}
public  int row (String word) {
    String num = word.substring(1);
    return Integer.parseInt(num)-1;
}
}