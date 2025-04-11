package cs3500.pawnsboard.provider.controller;

/**
 * Listener interface for player actions emitted by the view.
 * Implementations of this interface respond to user events such as selecting a card,
 * selecting a board cell, confirming a move, or passing a turn.
 */
public interface PlayerActionListener {

  /**
   * Invoked when a player selects a card from their hand.
   *
   * @param cardIndex the index of the selected card.
   */
  void cardSelected(int cardIndex);

  /**
   * Invoked when a player selects a cell on the board.
   *
   * @param row the row index of the selected cell.
   * @param col the column index of the selected cell.
   */
  void cellSelected(int row, int col);

  /**
   * Invoked when the player confirms their move.
   */
  void confirmMove();

  /**
   * Invoked when the player chooses to pass their turn.
   */
  void passMove();
}
