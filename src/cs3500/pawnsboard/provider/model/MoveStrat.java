package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.Player;
import cs3500.strategy.Move;

/**
 * MoveStrat is a simple interface for choosing a move given a GameModel and a Player.
 */
public interface MoveStrat {

  /**
   * Chooses a move for the given player based on the current game model.
   *
   * @param model  the game model (can be a mock or real implementation)
   * @param player the player for whom we want a move
   * @return a Move representing the chosen move; if no legal move, return a pass move
   */
  Move chooseMove(GameModel model, Player player);
}
