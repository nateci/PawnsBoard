package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.strategy.Move;
import cs3500.strategy.Strategy;

import org.junit.Test;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MaximizeRowScoreStrategyTest {

  @Test
  public void testChoosesMoveThatWinsRow() {
    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);

    Card card = new Card("WinCard", 1, 3, new char[5][5], Color.RED);
    model.getPlayerHand(Color.RED).clear();
    model.getPlayerHand(Color.RED).add(card);

    List<Move> moves = strategy.chooseMoves(model, Color.RED);
    assertEquals(3, moves.size()); // tie will settle to 1
    Move move = moves.get(0);
    assertEquals(0, move.getCardIndex());
  }

  @Test
  public void testReturnsEmptyIfNoWinningMove() {
    Strategy strategy = new MaximizeRowScoreStrategy() {
      public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
        return Collections.emptyList();
      }
    };

    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    List<Move> result = strategy.chooseMoves(model, Color.RED);
    assertTrue(result.isEmpty());
  }
}
