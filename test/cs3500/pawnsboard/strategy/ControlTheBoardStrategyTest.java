package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.ControlTheBoardStrategy;
import cs3500.strategy.Move;

import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.*;

public class ControlTheBoardStrategyTest {

  @Test
  public void testChoosesMoveToGainMostCells() {
    ControlTheBoardStrategy strategy = new ControlTheBoardStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.BLUE);

    Card strongCard = new Card("Strong", 1, 1, new char[][]{
            {'I', 'I', 'I', 'I', 'I'},
            {'I', 'I', 'I', 'I', 'I'},
            {'I', 'I', 'C', 'I', 'I'},
            {'I', 'I', 'I', 'I', 'I'},
            {'I', 'I', 'I', 'I', 'I'}
    }, Color.BLUE);

    model.getPlayerHand(Color.BLUE).clear();
    model.getPlayerHand(Color.BLUE).add(strongCard);

    List<Move> best = strategy.chooseMoves(model, Color.BLUE);
    assertFalse(best.isEmpty());
  }
}
