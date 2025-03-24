package cs3500.strategy;

import java.util.Comparator;
import java.util.List;

/**
 * Utility to break ties between equally good moves.
 */
public class TieBreaker {

  /**
   * Selects the move that is uppermost, then leftmost, then lowest card index.
   *
   * @param moves a list of valid moves
   * @return the best move based on tie-breaking rules, or null if list is empty
   */
  public static Move selectBest(List<Move> moves) {
    return moves.stream()
            .min(Comparator
                    .comparingInt(Move::getRow)
                    .thenComparingInt(Move::getCol)
                    .thenComparingInt(Move::getCardIndex))
            .orElse(null);
  }
}
