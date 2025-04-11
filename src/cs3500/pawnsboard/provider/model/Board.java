package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.Player;

/**
 * Board represents the contract for a game board in Pawns Board.
 * A board is a rectangular grid of cells, with the precondition that
 * rows > 0, cols > 1, and cols is odd.
 */
public interface Board {

  /**
   * Initializes the board by placing starting pawns.
   * For each row, a red pawn is added to the first column and a blue pawn to the last column.
   *
   * @param redPlayer  the Player object representing the Red player.
   * @param bluePlayer the Player object representing the Blue player.
   */
  void initialize(Player redPlayer, Player bluePlayer);

  /**
   * Retrieves the cell at the specified row and column.
   *
   * @param row the row index.
   * @param col the column index.
   * @return the Cell at the specified location, or null if out of bounds.
   */
  Cell getCell(int row, int col);

  /**
   * Applies the influence of a card on the board.
   * The card's influence grid is overlaid on the board with the given (row, col) as the center,
   * and the affected cells are updated according to the rules.
   *
   * @param card          the card whose influence is applied.
   * @param row           the row where the card was placed.
   * @param col           the column where the card was placed.
   * @param currentPlayer the player who placed the card.
   */
  void applyCardInfluence(Card card, int row, int col, Player currentPlayer);

  /**
   * Renders the board textually by printing each cell's content.
   */
  void renderTextually();

  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows.
   */
  int getRows();

  /**
   * Returns the number of columns in the board.
   *
   * @return the number of columns.
   */
  int getCols();

  /**
   * Renders the board textually along with row-scores.
   * For each row, the row-scores for Red and Blue are displayed on the sides.
   */
  void renderTextualWithRowScores();
}
