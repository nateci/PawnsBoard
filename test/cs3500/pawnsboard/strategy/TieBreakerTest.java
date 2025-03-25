package cs3500.pawnsboard.strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cs3500.strategy.Move;
import cs3500.strategy.TieBreaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for the {@link TieBreaker} utility class.
 * <p>
 * These tests ensure that tie-breaking logic selects the most desirable move
 * based on row, column, and card index priority.
 */

public class TieBreakerTest {

  @Test
  public void testSelectsUpperLeftMost() {
    List<Move> candidates = Arrays.asList(
            new Move(1, 2, 0),
            new Move(0, 3, 1),  // should win
            new Move(0, 3, 2),
            new Move(1, 0, 0)
    );

    Move best = TieBreaker.selectBest(candidates);
    assertEquals(new Move(0, 3, 1), best);
  }

  @Test
  public void testCardIndexBreaksTie() {
    List<Move> tied = Arrays.asList(
            new Move(0, 1, 2),
            new Move(0, 1, 0),  // should win (lowest index)
            new Move(0, 1, 1)
    );

    Move best = TieBreaker.selectBest(tied);
    assertEquals(new Move(0, 1, 0), best);
  }

  @Test
  public void testEmptyListReturnsNull() {
    Move result = TieBreaker.selectBest(List.of());
    assertNull(result);
  }
}
