package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.GameBoard;
import cs3500.pawnsboard.Player;
import cs3500.strategy.Move;

/**
 * The GameModel interface defines operations for the game model.
 * It handles applying moves, checking for game end conditions,
 * switching turns, calculating scores, and providing access to the
 * game board and the current player.
 */
public interface GameModel extends ReadOnlyGameModel {

  /**
   * Applies a move to update the game state.
   *
   * @param move the move to apply.
   */
  void applyMove(Move move);

  /**
   * Checks whether the game has reached an end state.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean checkGameEnd();

  /**
   * Switches the turn to the next player.
   */
  void switchTurn();

  /**
   * Calculates and returns the current scores for the players.
   *
   * @return an array of integers representing the scores.
   */
  int[] calculateScores();

  /**
   * Returns the game board.
   *
   * @return the GameBoard instance for this game.
   */
  GameBoard getBoard();

  /**
   * Returns the player whose turn is currently active.
   *
   * @return the current Player.
   */
  Player getCurrentPlayer();

  /**
   * Method to signal that the game should quit.
   */
  void quit();
}
