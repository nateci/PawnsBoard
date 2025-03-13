package cs3500.pawnsboard;

import java.awt.Color;

/**
 * Cell interface representing all functions of a cell within the Pawns Board game.
 */
public interface PawnsBoardCell {

  /**
   * Setter method for the pawns of the game.
   *
   * @param count the number of pawns
   * @param owner the owner (player) that the pawns belong to.
   */
  void setPawns(int count, Color owner);

  /**
   * Sets a card within a cell.
   *
   * @param card the card being used
   */
  void setCard(Card card);

  /**
   * Checks if a cell has a card.
   *
   * @return if the cell has a card or not
   */
  boolean hasCard();

  /**
   * Gets the card at a cell.
   *
   * @return the card at the cell
   */
  Card getCard();

  /**
   * Getter method for the owner of a card at a cell.
   *
   * @return the owner of the card
   */
  Color getOwner();

  /**
   * Checks if you can place a card in a cell or not.
   *
   * @param player the player
   * @param card the card
   * @return if you can place a card or not
   */
  boolean canPlaceCard(Player player, Card card);

  /**
   * Actual logic for influencing the board accroding to the influence grid.
   *
   * @param playerColor the color of the player (red or blue)
   * @param influenceType the influence type
   */
  void influenceBoard(Color playerColor, char influenceType);

  /**
   * Getter method for the number of pawns.
   *
   * @return the number of pawns
   */
  int getPawnCount();

  /**
   * The textual view of the game.
   *
   * @return a String of the textual view of the game
   */
  String toTextualView();
}
