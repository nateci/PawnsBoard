package cs3500.pawnsboard;

import java.awt.*;

public class Card {
  private final String name;
  private final int cost;
  private final int value;
  private final char[][] influenceGrid;
  private final Color owner;

  public Card(String name, int cost, int value, char[][] influenceGrid, Color owner) {
    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceGrid = influenceGrid;
    this.owner = owner;
  }

  public String getName() {
    return name;
  }
  public int getCost() {
    return cost;
  }
  public int getValue() {
    return value;
  }
  public char[][] getInfluenceGrid() {
    return influenceGrid;
  }
  public Color getOwner() {
    return owner;
  }


  public char[][] getMirroredInfluenceGrid() {
    char[][] mirrored = new char[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        mirrored[r][4 - c] = influenceGrid[r][c];
      }
    }
    return mirrored;
  }
}

