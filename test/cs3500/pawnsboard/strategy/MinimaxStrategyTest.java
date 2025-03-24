package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.MinimaxStrategy;
import cs3500.strategy.Move;

import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.*;

public class MinimaxStrategyTest {

  @Test
  public void testChoosesMoveThatMinimizesOpponentOptions() {
    MinimaxStrategy strategy = new MinimaxStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    Card testCard = new Card("Blocker", 1, 1, new char[5][5], Color.RED);
    model.getPlayerHand(Color.RED).clear();
    model.getPlayerHand(Color.RED).add(testCard);

    List<Move> moves = strategy.chooseMoves(model, Color.RED);
    assertNotNull(moves);
  }
}
