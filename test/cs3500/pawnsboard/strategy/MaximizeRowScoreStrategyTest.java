package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.strategy.Move;

import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for MaximizeRowScoreStrategy.
 */
public class MaximizeRowScoreStrategyTest {

  @Test
  public void testChoosesMoveThatWinsTopRow() {
    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();

    MockReadOnlyPawnsBoardModel model = new
            MockReadOnlyPawnsBoardModel(3, 3, Color.RED) {
      @Override
      public int[] calculateRowScores(int row) {
        if (row == 0) {
          return new int[]{2, 3}; // Not winning
        } else if (row == 1) {
          return new int[]{4, 2}; // Already winning
        } else {
          return new int[]{1, 5}; // Not winning
        }
      }


      @Override
      public boolean isValidMove(int cardIndex, int row, int col) {
        return row == 0 && col == 0; // Only one valid winning move in row 0
      }
    };

    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    Move move = result.get(0);
    assertEquals(0, move.getRow());
  }

  @Test
  public void testSkipsUnwinnableRows() {
    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();

    MockReadOnlyPawnsBoardModel model = new
            MockReadOnlyPawnsBoardModel(3, 3, Color.RED) {
      @Override
      public int[] calculateRowScores(int row) {
        return new int[]{3, 6}; // Always losing
      }

      @Override
      public boolean isValidMove(int cardIndex, int row, int col) {
        return true;
      }
    };

    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertTrue(result.isEmpty()); // No way to win any row
  }

  @Test
  public void testChoosesFirstImprovingMoveInRow() {
    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();

    MockReadOnlyPawnsBoardModel model = new
            MockReadOnlyPawnsBoardModel(3, 3, Color.RED) {
      @Override
      public int[] calculateRowScores(int row) {
        return new int[]{2, 2}; // Equal → valid if increase is possible
      }

      @Override
      public boolean isValidMove(int cardIndex, int row, int col) {
        return row == 1 && col == 1; // Only one valid move
      }
    };

    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertEquals(1, result.size());
    Move move = result.get(0);
    assertEquals(1, move.getRow());
    assertEquals(1, move.getCol());
  }
}
