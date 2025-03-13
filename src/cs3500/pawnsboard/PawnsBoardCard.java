package cs3500.pawnsboard;

import java.awt.Color;


/**
 * Card interface representing all functions of a card within the Pawns Board game.
 */
public interface PawnsBoardCard {

  /**
   * Getter method for the name of the card.
   *
   * @return the name of the card
   */
  String getName();

  /**
   * Getter method for the cost of the card.
   *
   * @return the cost of the card
   */
  int getCost();

  /**
   * Getter method for the value of the card.
   *
   * @return the value of the card
   */
  int getValue();

  /**
   * Getter method for the influence grid of the card.
   *
   * @return the influence grid
   */
  char[][] getInfluenceGrid();

  /**
   * Getter method for the owner (player) of the card.
   *
   * @return the owner of the card
   */
  Color getOwner();

  /**
   * Gets the mirrored influence grid (strictly for the blue plauer).
   *
   * @return the mirrored influence grid
   */
  char[][] getMirroredInfluenceGrid();
}
