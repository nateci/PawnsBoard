package cs3500.controller;

/**
 * Interface for listening to player actions.
 */
public interface PlayerActionListener {

  /**
   * Called when a player selects a card.
   *
   * @param cardIndex The index of the selected card
   */
  void onCardSelected(int cardIndex);

  /**
   * Called when a player selects a cell.
   *
   * @param row The row of the selected cell
   * @param col The column of the selected cell
   */
  void onCellSelected(int row, int col);

  /**
   * Called when a player confirms a move.
   */
  void onMoveConfirmed();

  /**
   * Called when a player passes their turn.
   */
  void onPassTurn();
}
