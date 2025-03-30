package org.example;

import java.util.Random;
import java.util.Scanner;

public class AI {
  Coordinate coordinate;
  public AI() {
      coordinate = new Coordinate();
  }
  public String move(int size) {
      Random rand = new Random();
      char col = (char) (rand.nextInt(size) + 'A');
      int row = rand.nextInt(1,size);
      String attack = col + "" + row;
      return attack;

  }

}
