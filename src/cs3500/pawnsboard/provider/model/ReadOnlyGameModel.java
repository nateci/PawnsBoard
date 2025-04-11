package cs3500.pawnsboard.provider.model;

import cs3500.strategy.Move;

import java.util.List;

/**
 * A read-only interface for the Pawns Board game model.
 * Contains only query methods.
 */
public interface ReadOnlyGameModel {

  /**
   * Returns the number of rows in the board.
   */
  int getBoardRows();

  /**
   * Returns the number of columns in the board.
   */
  int getBoardCols();

  /**
   * Returns true if the game is over (e.g. two passes in a row).
   */
  boolean isGameOver();

  /**
   * Returns the current player's color (RED or BLUE).
   */
  PlayerInt.PlayerColor getCurrentPlayerColor();

  /**
   * Returns the list of cards (the hand) of the current player.
   */
  List<Card> getCurrentPlayerHand();

  /**
   * Returns the total scores for both players, e.g. { redScore, blueScore }.
   */
  int[] calculateScores();

  /**
   * Returns read-only information about the cell at (row, col).
   * You might choose to return a small data class (e.g. an immutable "CellState"),
   * or simply the existing Cell if it, too, is read-only. If your current Cell
   * is mutable, consider a read-only wrapper or add only the needed getters.
   */
  Cell getCellAt(int row, int col);

  /**
   * Returns the content of the cell at (row, col), or null if out of bounds.
   */
  Cell.CellContent getCellContent(int row, int col);

  /**
   * Returns how many pawns are in the cell at (row, col).
   * Returns 0 if out of bounds or empty.
   */
  int getPawnCount(int row, int col);

  /**
   * Returns the card placed in the cell at (row, col), or null if no card or out of bounds.
   */
  Card getCellCard(int row, int col);

  /**
   * Returns the color of the player who owns the cell at (row, col),
   * or null if the cell is out of bounds or has no owner.
   */
  PlayerInt.PlayerColor getCellOwner(int row, int col);

  /**
   * Returns the cell at the specified coordinates.
   *
   * @param row the row index.
   * @param col the column index.
   * @return the cell at the specified location.
   */
  Cell getCell(int row, int col);

  /**
   * Returns the row score for the given row and player color.
   *
   * @param row   the row index.
   * @param color the player's color.
   * @return the score for that row for the specified player.
   */
  int getRowScore(int row, PlayerInt.PlayerColor color);

  /**
   * Applies the given move to the game model.
   *
   * @param move the move to apply.
   */
  void applyMove(Move move);

  /**
   * Returns the red player.
   *
   * @return the red player as a PlayerInt.
   */
  PlayerInt getRedPlayer();

  /**
   * Returns the blue player.
   *
   * @return the red player as a PlayerInt.
   */
  PlayerInt getBluePlayer();
}
