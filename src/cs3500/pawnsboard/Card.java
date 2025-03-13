package cs3500.pawnsboard;

import java.awt.*;

/**
 * Representation for a card in Pawns Board.
 */
public class Card implements PawnsBoardCard {
  private final String name;
  private final int cost;
  private final int value;
  private final char[][] influenceGrid;
  private final Color owner;

  /**
   * An instance of a card used in Pawns Board.
   *
   * @param name the name of the card
   * @param cost the cost of the card
   * @param value the value of the card
   * @param influenceGrid the influenceGrid (how placing a card affects the other positions on the board)
   * @param owner the player (red or blue)
   */
  public Card(String name, int cost, int value, char[][] influenceGrid, Color owner) {
    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceGrid = influenceGrid;
    this.owner = owner;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public char[][] getInfluenceGrid() {
    return influenceGrid;
  }

  @Override
  public Color getOwner() {
    return owner;
  }

  @Override
  public char[][] getMirroredInfluenceGrid() {
    char[][] mirrored = new char[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        mirrored[r][4 - c] = influenceGrid[r][c]; // reversed grid starting from the 5th (4th) column
      }
    }
    return mirrored;
  }
}

