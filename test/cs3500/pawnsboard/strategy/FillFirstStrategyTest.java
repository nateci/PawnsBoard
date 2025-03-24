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
  public void testChoosesFirstAvailableMove() {
    FillFirstStrategy strategy = new FillFirstStrategy();

    // Set up mock board with one card and 3x3 board
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    Card testCard = new Card("CardA", 1, 1, new char[][]{
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', 'C', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'}
    }, Color.RED);

    model.getPlayerHand(Color.RED).clear();
    model.getPlayerHand(Color.RED).add(testCard);

    List<Move> moves = strategy.chooseMoves(model, Color.RED);

    assertEquals(1, moves.size());
    Move chosen = moves.get(0);
    assertEquals(0, chosen.getRow());
    assertEquals(0, chosen.getCol());
    assertEquals(0, chosen.getCardIndex());
  }

  @Test
  public void testReturnsEmptyIfNoValidMoves() {
    FillFirstStrategy strategy = new FillFirstStrategy();

    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, Color.BLUE);
    model.getPlayerHand(Color.BLUE).clear(); // no cards

    List<Move> moves = strategy.chooseMoves(model, Color.BLUE);
    assertTrue(moves.isEmpty());
  }
}
