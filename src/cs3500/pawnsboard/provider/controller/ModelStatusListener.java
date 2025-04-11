package cs3500.pawnsboard.provider.controller;

import cs3500.pawnsboard.provider.model.PlayerInt;

/**
 * Listener interface for model status events.
 * Implementations are notified when key events occur in the model,
 * such as a change of turn or game termination.
 */
public interface ModelStatusListener {

  /**
   * Called when the active player changes.
   *
   * @param newTurn the {@link PlayerInt.PlayerColor} of the new active player.
   */
  void turnChanged(PlayerInt.PlayerColor newTurn);

  /**
   * Called when the game is over.
   *
   * @param finalScores an array of final scores, where index 0 is red and index 1 is blue.
   * @param winner      a string indicating the winner or if the game is a tie.
   */
  void gameOver(int[] finalScores, String winner);
}
