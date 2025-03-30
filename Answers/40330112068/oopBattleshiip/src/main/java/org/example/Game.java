package org.example;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    int gridSize;
    Scanner input = new Scanner(System.in);
    Scanner input1 = new Scanner(System.in);
    private Utils utils;
    public Board p1grid, p2grid,p1trackinggrid,p2trackinggrid,board;
    public Coordinate coordinate;
    public AI ai;
    public Game() {
    coordinate = new Coordinate();
        utils = new Utils();
ai = new AI();
    }

    public void startmenu() {

        System.out.println("Welcome to the Battle Ship Game");
        System.out.println("Choose an option : ");
        System.out.println("1. Play With AI");
        System.out.println("2. Two-Player Mode");
        String option = input.nextLine();
        if (utils.choose(option)) {
            if (Objects.equals(option, "1")) {
                aimode();
            }else if (Objects.equals(option, "2")) {
                twoplayermode();
            }
        } else {
            System.out.println("invalid input");
            startmenu();
        }
    }
    public void aimode(){
        System.out.println("AI Mode");
        System.out.println("Enter grid size : ( 8 to 26 )");
        gridSize = input1.nextInt();
        if(utils.checksize(gridSize)) {
            p1grid = new Board(gridSize);
            p1trackinggrid = new Board(gridSize);
            p2trackinggrid = new Board(gridSize);
            board = new Board(gridSize);
            p2grid = new Board(gridSize);
            p1grid.creategrid();
            p2grid.creategrid();
            p1trackinggrid.creategrid();
            p2trackinggrid.creategrid();
            shipmenu(1);

        }else {
            System.out.println("invalid input");
            aimode();
        }
    }
    public void twoplayermode(){
        System.out.println("Two-Player Mode");
        System.out.println("Enter grid size : ( 8 to 26 )");
        gridSize = input1.nextInt();
        if(utils.checksize(gridSize)) {
            p1grid = new Board(gridSize);
            p1trackinggrid = new Board(gridSize);
            p2trackinggrid = new Board(gridSize);
            p2grid = new Board(gridSize);
            board=new Board(gridSize);
            p1grid.creategrid();
            p2grid.creategrid();
            p1trackinggrid.creategrid();
            p2trackinggrid.creategrid();
            shipmenu(2);
        }else {
            System.out.println("invalid input");
            twoplayermode();
        }
    }
    public void shipmenu(int x) {
        System.out.println("Choose an option : ");
        System.out.println("1.Random Ship Placement");
        System.out.println("2.Manual Ship Placement");
        String option = input.nextLine();
        if (utils.choose(option)) {
            if (Objects.equals(option, "1") &&x==1) {
                p1grid.shipsizehandler(1);
                p2grid.shipsizehandler(1);
                aiplayGame();
            }else if (Objects.equals(option, "1")&&x==2) {
                p1grid.shipsizehandler(1);
                p2grid.shipsizehandler(1);
                playGame();
            }
            else if (Objects.equals(option, "2")&&x==1) {
                System.out.println("Place your ship :");
                p1grid.shipsizehandler(2);
                System.out.println(" AI ship place:");
                p2grid.shipsizehandler(1);
aiplayGame();
            }else{
                System.out.println("Player 1 ship place :");
                p1grid.shipsizehandler(2);
                System.out.println("Player 2 ship place :");
                p2grid.shipsizehandler(2);
                playGame();
            }
        } else {
            System.out.println("invalid input");
            shipmenu(x);
        }
    }
    public void playGame() {
        Scanner input = new Scanner(System.in);
        boolean player1Turn = true;
        while (!p1grid.allshipsunk() && !p2grid.allshipsunk()) {
            Board opponentGrid = player1Turn ? p2grid : p1grid;
            Board trackingGrid = player1Turn ? p1trackinggrid : p2trackinggrid;
            trackingGrid.printgrid();
            System.out.println("Player " + (player1Turn ? "1" : "2") + ", enter your target (e.g., A6): ");
            String target = input.nextLine();

            if (coordinate.isValidCoordinate(target,gridSize)) {
                int row = coordinate.row(target);
                int col = coordinate.col(target);

                boolean hit = opponentGrid.play(row, col);
                trackingGrid.updateTrackingGrid(row, col, hit);
                if (opponentGrid.allshipsunk()) {
                    System.out.println("🎉 Player " + (player1Turn ? "1" : "2") + " wins!");
                    System.out.println("Game Over!");
                    break;
                }
                player1Turn = !player1Turn;
            } else {
                System.out.println("Invalid coordinate");
                player1Turn = !player1Turn;
            }
        }
    }
    public void aiplayGame() {
        String target = null;
        Scanner input = new Scanner(System.in);
        boolean player1Turn = true;
        while (!p1grid.allshipsunk() && !p2grid.allshipsunk()) {
            Board opponentGrid = player1Turn ? p2grid : p1grid;
            Board trackingGrid = player1Turn ? p1trackinggrid : p2trackinggrid;
            trackingGrid.printgrid();
            System.out.println( (player1Turn ? "player 1 Enter the target (e.g., A6): " : "AI Select Target") );
            if(player1Turn) {
                target = input.nextLine();
            }else {

           target =  ai.move(gridSize);
                System.out.println(target);

            }


            if (coordinate.isValidCoordinate(target,gridSize)) {
                int row = coordinate.row(target);
                int col = coordinate.col(target);

                boolean hit = opponentGrid.play(row, col);
                trackingGrid.updateTrackingGrid(row, col, hit);
                if (opponentGrid.allshipsunk()) {
                    System.out.println((player1Turn ? "player 1 win " : "AI win"));
                    System.out.println("Game Over!");
                    System.out.println("Do you want to see game replay? (yes/no)");

                    break;
                }
                player1Turn = !player1Turn;
            } else {
                System.out.println("Invalid coordinate");
                player1Turn = !player1Turn;
            }
        }
    }

    }





