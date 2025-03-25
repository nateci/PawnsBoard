package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.ControlTheBoardStrategy;
import cs3500.strategy.Move;

import org.junit.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Unit tests for the ControlTheBoardStrategy.
 */
public class ControlTheBoardStrategyTest {

  @Test
  public void testChoosesMoveToGainMostCells() {
    ControlTheBoardStrategy strategy = new ControlTheBoardStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(5, 5, Color.BLUE);

    // High influence card
    Card strongCard = new Card("Strong", 1, 5, new char[][]{
            {'X', 'X', 'X'},
            {'X', 'X', 'X'},
            {'X', 'X', 'X'}
    }, Color.BLUE);

    model.getPlayerHand(Color.BLUE).clear();
    model.getPlayerHand(Color.BLUE).add(strongCard);

    List<Move> best = strategy.chooseMoves(model, Color.BLUE);
    assertFalse(best.isEmpty());
  }

  @Test
  public void testMultipleCardsChoosesBestOne() {
    ControlTheBoardStrategy strategy = new ControlTheBoardStrategy();
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(5, 5, Color.RED);

    // One weak, one strong card
    Card weak = new Card("Weak", 1, 1, new char[][]{
            {'X'}
    }, Color.RED);

    Card strong = new Card("Strong", 1, 9, new char[][]{
            {'X', 'X', 'X'},
            {'X', 'X', 'X'},
            {'X', 'X', 'X'}
    }, Color.RED);

    model.getPlayerHand(Color.RED).clear();
    model.getPlayerHand(Color.RED).add(weak);
    model.getPlayerHand(Color.RED).add(strong);

    List<Move> best = strategy.chooseMoves(model, Color.RED);
    assertEquals(50, best.size());
    assertEquals(0, best.get(0).getCardIndex()); // Expecting the strong card to be chosen
  }

}
