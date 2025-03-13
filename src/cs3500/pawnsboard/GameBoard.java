package cs3500.pawnsboard;

import java.awt.Color;

/**
 * Game board interface representing all functions of game board within the Pawns Board game.
 */
public interface GameBoard {

  /**
  * Places a card onto the board.
  *
  * @param player The player placing the card (red or blue)
  * @param card the card the player is placing
  * @param row the row of the placed card
  * @param col the column of the placed card
  * @return if the card has been placed at the designated position
  */
  boolean placeCard(Player player, Card card, int row, int col);

  /**
   * Determines if the move a player is trying to make is valid.
   *
   * @param player the player
   * @param card the card being placed
   * @param row the row the card is being placed at
   * @param col the column the card is being placed at
   * @return if the move is valid or not
   */
  boolean isValidMove(Player player, Card card, int row, int col);

  /**
   * Prints the textual view of the game.
   */
  void printTextView();

  /**
   * Calculates the score of a designated row for both players.
   *
   * @param row the row that's currently being used to calculate the score
   * @return the score of both players for the designated row
   */
  int[] calculateRowScores(int row);

  /**
   * Calculates the total score of the game.
   *
   * @param playerColor the player's color (red or blue)
   * @return the total score of the winning player
   */
  int calculateTotalScore(Color playerColor);

  /**
   * Getter method for the rows in the game board.
   *
   * @return the number of rows
   */
  int getRows();

  /**
   * Getter method for the columns in the game board.
   *
   * @return the number of columns
   */
  int getCols();

  /**
   * Getter method for the cells of the game board.
   *
   * @param row the current row of the game board
   * @param col the current column of the game board
   * @return the designated cell of the game board
   */
  Cell getCell(int row, int col);
}
