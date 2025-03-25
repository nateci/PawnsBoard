package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Chains multiple strategies together and chooses the first non-empty result.
 */
public class ChainedStrategy implements Strategy {
  private final List<Strategy> strategies;

  /**
   * Constructor for chaining strategies together.
   * @param strategies take in a strategy.
   */
  public ChainedStrategy(List<Strategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    for (Strategy strategy : strategies) {
      List<Move> moves = strategy.chooseMoves(model, playerColor);
      if (!moves.isEmpty()) {
        return moves;
      }
    }
    return new ArrayList<>(); // all failed — pass
  }
}
