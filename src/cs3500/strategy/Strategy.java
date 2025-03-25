package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;
import java.awt.Color;
import java.util.List;

/**
 * A strategy interface that returns a list of equally optimal moves.
 */
public interface Strategy {

  /**
   * Computes the best move(s) for the given player.
   * @return a list of best moves — possibly empty if no valid moves exist.
   */
  List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor);
}
