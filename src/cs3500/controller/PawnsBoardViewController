package cs3500.controller;


import cs3500.pawnsboard.Game;

/**
 * Interface for the Pawns Board view controller.
 * Defines the methods needed to handle user interactions.
 */
public interface PawnsBoardViewController {

  /**
   * Handles a click on a card in the player's hand.
   *
   * @param cardIndex The index of the clicked card
   */
  void handleCardClick(int cardIndex);

  /**
   * Handles setting the game to a state.
   * @param game The game being set.
   */
  void setGame(Game game);

  /**
   * Handles a click on a cell in the game board.
   *
   * @param row The row of the clicked cell
   * @param col The column of the clicked cell
   */
  void handleCellClick(int row, int col);

  /**
   * Handles a key press for confirming a move.
   */
  void confirmMove();

  /**
   * Handles a key press for passing a turn.
   */
  void passTurn();
}
