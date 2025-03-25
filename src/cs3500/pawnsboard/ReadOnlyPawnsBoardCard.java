package cs3500.pawnsboard;


import java.awt.Color;

/**
 * A read-only interface for a card within the Pawns Board game.
 */
public interface ReadOnlyPawnsBoardCard {

  /**
   * Gets the name of the card.
   *
   * @return the name of the card
   */
  String getName();

  /**
   * Gets the cost of the card.
   *
   * @return the cost of the card
   */
  int getCost();

  /**
   * Gets the value of the card.
   *
   * @return the value of the card
   */
  int getValue();

  /**
   * Gets a copy of the influence grid of the card.
   *
   * @return a copy of the influence grid
   */
  char[][] getInfluenceGrid();

  /**
   * Gets the owner of the card.
   *
   * @return the color of the card's owner
   */
  Color getOwner();
}
