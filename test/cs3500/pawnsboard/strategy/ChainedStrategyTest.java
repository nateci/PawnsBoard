package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.ChainedStrategy;
import cs3500.strategy.Move;
import cs3500.strategy.Strategy;

import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Unit tests for the {@link ChainedStrategy} class.
 * These tests verify the behavior of chained strategies, ensuring
 * fallback works properly and priority is respected.
 */
public class ChainedStrategyTest {

  @Test
  public void testFallbackToSecondStrategy() {
    Strategy strategy1 = (model, color) -> Collections.emptyList();
    Strategy strategy2 = (model, color) -> Collections.singletonList(new Move(1, 1, 0));

    ChainedStrategy chained = new ChainedStrategy(Arrays.asList(strategy1, strategy2));
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    List<Move> result = chained.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    assertEquals(new Move(1, 1, 0), result.get(0));
  }

  @Test
  public void testFirstStrategyTakesPriority() {
    Strategy first = (model, color) -> Collections.singletonList(new Move(0, 0, 1));
    Strategy second = (model, color) -> Collections.singletonList(new Move(2, 2, 0));

    ChainedStrategy chained = new ChainedStrategy(Arrays.asList(first, second));
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    List<Move> result = chained.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    assertEquals(new Move(0, 0, 1), result.get(0));
  }

  @Test
  public void testAllStrategiesEmpty() {
    Strategy empty1 = (model, color) -> Collections.emptyList();
    Strategy empty2 = (model, color) -> Collections.emptyList();

    ChainedStrategy chained = new ChainedStrategy(Arrays.asList(empty1, empty2));
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    List<Move> result = chained.chooseMoves(model, Color.RED);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testEmptyStrategyChain() {
    ChainedStrategy chained = new ChainedStrategy(Collections.emptyList());
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    List<Move> result = chained.chooseMoves(model, Color.RED);
    assertTrue(result.isEmpty());
  }
}
