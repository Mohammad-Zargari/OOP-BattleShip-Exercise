package org.example;

public class Ship {
    private int row, col, length;
    private boolean horizontal;
    private boolean[] health;

    public Ship(int row, int col, int length, boolean horizontal) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.horizontal = horizontal;
        this.health = new boolean[length];

        for (int i = 0; i < length; i++) {
            health[i] = true;
        }
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getLength() { return length; }
    public boolean isHorizontal() { return horizontal; }

    public boolean hit(int hitrow, int hitcol) {
        for (int i = 0; i < length; i++) {
            int shipRow = horizontal ? row : row + i;
            int shipCol = horizontal ? col + i : col;
            if (shipRow == hitrow && shipCol == hitcol) {
                if (!health[i]) {
                    return false;
                }
                health[i] = false;
                return true;
            }
        }
        return false ;
    }

    public boolean isSunk() {
        for (boolean part : health) {
            if (part) return false;
        }
        return true;
    }

}