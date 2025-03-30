package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {

    public Coordinate coordinate = new Coordinate();
    private char[][] grid;
    private int size;
    private ArrayList<Ship> ships;


    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        creategrid();
        this.ships = new ArrayList<>();

    }


    public void creategrid() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = '~';
            }
        }

    }

    public void printgrid() {
        System.out.print("   ");
        for (int x = 0; x < size; x++) {
            char letter = (char) ('A' + (x % 26));
            System.out.print(letter + " ");
        }
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            if (i < 9) {
                System.out.print("0" + (i + 1) + " ");
            } else if (i >= 9) {
                System.out.print(0 + (i + 1) + " ");
            }
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }

            System.out.println();
        }
    }

    public void shipsizehandler(int choice) {
        switch (size) {
            case 8:
            case 9:
            case 10:
                if (choice == 1) {
                    randomshipplac(2, 6);
                } else if (choice == 2 ) {
                    manualshipplace(2, 6);
                }
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                if (choice == 1) {
                    randomshipplac(3, 7);
                } else if (choice == 2) {
                    manualshipplace(3, 7);
                }
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                if (choice == 1) {
                    randomshipplac(4, 8);
                } else if (choice == 2) {
                    manualshipplace(4, 8);
                }
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                if (choice == 1) {
                    randomshipplac(5, 9);
                } else if (choice == 2) {
                    manualshipplace(5, 9);
                }
                break;
        }
    }

    public void randomshipplac(int i, int shipsize) {
        Random rand = new Random();
        while (i < shipsize) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            boolean horizontal = rand.nextBoolean();
            if (canplaceship(row, col, i, horizontal) && horizontal) {
                Ship ship = new Ship(row, col, i, horizontal);
                ships.add(ship);
                for (int x = 0; x < i; x++) {
                    grid[row][col + x] = '*';
                }
                i++;
            } else if (canplaceship(row, col, i, horizontal) && !horizontal) {
                Ship ship = new Ship(row, col, i, horizontal);
                ships.add(ship);
                for (int x = 0; x < i; x++) {
                    grid[row + x][col] = '*';
                }
                i++;
            }

        }



    }




    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void manualshipplace(int i, int shipsize) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Your grid size is : " + size);
        System.out.println("Your ship size is : " + i + " to " + shipsize);
        System.out.println("The ships are one house apart.");

        while (i < shipsize) {
            this.printgrid();
            System.out.println("Enter your size " + i + " th ship row and col  (example = A6) :");
            String word = scan.nextLine();
            if (coordinate.isValidCoordinate(word, size)) {
                int col = coordinate.col(word);
                int row = coordinate.row(word);
                boolean horizontal = false;
                System.out.println("horizontal? (YES/NO)");
                String word1 = scan.nextLine();
                if (coordinate.isValidhorizontal(word1)) {
                    if (word1.equals("YES")) {
                        horizontal = true;
                    } else if (word1.equals("NO")) {
                        horizontal = false;
                    }


                    if (canplaceship(row, col, i, horizontal) && horizontal) {
                        Ship ship = new Ship(row, col, i, horizontal);
                        ships.add(ship);

                        for (int x = 0; x < i; x++) {
                            grid[row][col + x] = '*';
                        }
                        i++;
                    } else if (canplaceship(row, col, i, horizontal) && !horizontal) {
                        Ship ship = new Ship(row, col, i, horizontal);
                        ships.add(ship);
                        for (int x = 0; x < i; x++) {
                            grid[row + x][col] = '*';
                        }
                        i++;
                    } else {
                        System.out.println("Can't place ship!");
                        System.out.println("Try again");
                    }


                } else {
                    System.out.println("Invalid horizontal");
                    System.out.println("Try again");

                }

            } else {
                System.out.println("Invalid coordinate");
                System.out.println("Try again");
            }

        }
        System.out.println("Your Final grid : ");
        this.printgrid();
        printShipsStatus();

    }

    public void printShipsStatus() {
        for (Ship ship : ships) {
            System.out.println("Ship at (" + ship.getRow() + ", " + ship.getCol() + "), Length: " + ship.getLength());
        }
    }

    public boolean allshipsunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean play(int row, int col) {
        for (Ship ship : ships) {
            if (ship.hit(row, col)) {
                System.out.println("🔥 Hit!");
                if (ship.isSunk()) {
                    System.out.println("💥 Ship sunk!");
                }
                return true;
            }
        }
        System.out.println("❌ Miss!");
        return false;
    }

    public void updateTrackingGrid(int row, int col, boolean hit) {
        if (grid[row][col] == 'X' || grid[row][col] == '0') {
            System.out.println("⚠️ Already shot here! You will definitely see above message  ");
        } else {
            grid[row][col] = hit ? 'X' : '0';
        }
    }
    public boolean canplaceship(int row, int col, int shipsize, boolean horizontal) {
        if (horizontal) {
            if (row < size && col + shipsize <= size) {
                for (int x = -1; x <= shipsize; x++) {
                    for (int y = -1; y <= 1; y++) {
                        int newRow = row + y;
                        int newCol = col + x;
                        if (isValid(newRow, newCol) && grid[newRow][newCol] == '*') {
                            return false;
                        }
                    }
                }
                return true;
            }
        } else {
            if (col < size && row + shipsize <= size) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= shipsize; y++) {
                        int newRow = row + y;
                        int newCol = col + x;
                        if (isValid(newRow, newCol) && grid[newRow][newCol] == '*') {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    private boolean isValid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}


