package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.FillFirstStrategy;
import cs3500.strategy.Move;

import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for FillFirstStrategy.
 */
public class FillFirstStrategyTest {

  @Test
  public void testReturnsFirstValidMove() {
    FillFirstStrategy strategy = new FillFirstStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    // Assume all moves are valid. Strategy should pick card 0, row 0, col 0.
    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    Move move = result.get(0);
    assertEquals(0, move.getCardIndex());
    assertEquals(0, move.getRow());
    assertEquals(0, move.getCol());
  }

  @Test
  public void testStopsAtFirstPlayableLocation() {
    FillFirstStrategy strategy = new FillFirstStrategy();

    // Override mock to return false for first few positions
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED) {
      @Override
      public boolean isValidMove(int cardIndex, int row, int col) {
        return row == 1 && col == 1; // Only this is valid
      }
    };

    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    Move move = result.get(0);
    assertEquals(1, move.getRow());
    assertEquals(1, move.getCol());
  }

  @Test
  public void testNoValidMovesReturnsEmpty() {
    FillFirstStrategy strategy = new FillFirstStrategy();

    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED) {
      @Override
      public boolean isValidMove(int cardIndex, int row, int col) {
        return false; // No move is valid
      }
    };

    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertTrue(result.isEmpty());
  }
}
