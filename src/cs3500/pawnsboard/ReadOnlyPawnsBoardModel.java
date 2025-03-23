package cs3500.pawnsboard;

import java.awt.Color;
import java.util.List;

/**
 * A read-only interface for the Pawns Board game model.
 * This interface provides methods to observe the state of the game
 * without the ability to mutate it.
 */
public interface ReadOnlyPawnsBoardModel {

  /**
   * Gets the number of rows in the game board.
   *
   * @return the number of rows
   */
  int getBoardRows();

  /**
   * Gets the number of columns in the game board.
   *
   * @return the number of columns
   */
  int getBoardCols();

  /**
   * Gets a read-only representation of a cell at the specified position.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @return a read-only representation of the cell
   * @throws IllegalArgumentException if the position is invalid
   */
  ReadOnlyPawnsBoardCell getCell(int row, int col);

  /**
   * Gets the current player's color.
   *
   * @return the color of the current player (RED or BLUE)
   */
  Color getCurrentPlayerColor();

  /**
   * Gets a read-only view of the current player's hand.
   *
   * @return a list of cards in the current player's hand
   */
  List<ReadOnlyPawnsBoardCard> getCurrentPlayerHand();

  /**
   * Gets a read-only view of the specified player's hand.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return a list of cards in the specified player's hand
   */
  List<ReadOnlyPawnsBoardCard> getPlayerHand(Color playerColor);

  /**
   * Calculates the score of a designated row for both players.
   *
   * @param row the row index
   * @return an array containing [redScore, blueScore] for the row
   * @throws IllegalArgumentException if the row index is invalid
   */
  int[] calculateRowScores(int row);

  /**
   * Calculates the total score of a player.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return the total score of the player
   */
  int calculateTotalScore(Color playerColor);

  /**
   * Checks if a move is valid for the current player.
   *
   * @param cardIndex the index of the card in the player's hand
   * @param row the row where the card would be placed
   * @param col the column where the card would be placed
   * @return true if the move is valid, false otherwise
   */
  boolean isValidMove(int cardIndex, int row, int col);

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Gets the winning player's color, or null if there is no winner yet or it's a tie.
   *
   * @return the color of the winning player, or null if no winner or tie
   */
  Color getWinner();

  /**
   * Checks if a player has any valid moves available.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return true if the player has at least 1 valid move, false otherwise
   */
  boolean hasValidMoves(Color playerColor);
}




