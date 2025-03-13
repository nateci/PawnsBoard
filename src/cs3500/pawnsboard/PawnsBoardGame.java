package cs3500.pawnsboard;

/**
 * Game interface representing all functions of a game of Pawns Board.
 */
public interface PawnsBoardGame {

  /**
   * Main game loop that manages player turns and game state. Players can either pass their turn
   * or place a card by specifying row, column, and card index.
   */
  void play();

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Calculates and displays the final scores, determining the winner of the game.
   */
  void determineWinner();
}
