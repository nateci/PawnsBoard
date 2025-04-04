package cs3500.controller;

import java.awt.Color;

/**
 * Interface defining the features/events that a model can publish.
 * Controllers can subscribe to listen for these events.
 */
public interface ModelFeatures {

  /**
   * Notifies when it's a player's turn.
   *
   * @param playerColor The color of the player whose turn it is
   */
  void notifyPlayerTurn(Color playerColor);

  /**
   * Notifies when the game is over.
   *
   * @param winner The color of the winning player, or null if it's a tie
   * @param redScore The final score for the red player
   * @param blueScore The final score for the blue player
   */
  void notifyGameOver(Color winner, int redScore, int blueScore);

  /**
   * Notifies when an invalid move is attempted.
   *
   * @param message The error message explaining why the move is invalid
   */
  void notifyInvalidMove(String message);
}
