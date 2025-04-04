package cs3500.controller;

import java.awt.Color;

/**
 * Interface for player controllers that can take actions
 * in the Pawns Board game.
 */
public interface PlayerController {

  /**
   * Notifies the player that it's their turn.
   */
  void startTurn();

  /**
   * Notifies the player that the game is over.
   *
   * @param winner The color of the winning player, or null if it's a tie
   * @param redScore The final score for the red player
   * @param blueScore The final score for the blue player
   */
  void gameOver(Color winner, int redScore, int blueScore);

  /**
   * Gets the color of the player being controlled.
   *
   * @return The player's color
   */
  Color getPlayerColor();
}
