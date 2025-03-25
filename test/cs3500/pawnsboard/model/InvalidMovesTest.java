package cs3500.pawnsboard.model;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.util.List;

import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.Player;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for verifying invalid move conditions in the game.
 * Ensures that incorrect card placements are properly rejected and game rules are enforced.
 */
public class InvalidMovesTest {
  @Test
  public void testInvalidCardPlacements() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED,
              List.of(new Card("TestCard", 1, 3, new char[][]{
                {'X', 'X', 'I', 'X', 'X'},
                {'X', 'X', 'I', 'X', 'X'},
                {'X', 'I', 'C', 'I', 'X'},
                {'X', 'X', 'I', 'X', 'X'},
                {'X', 'X', 'I', 'X', 'X'}
              }, Color.RED)), 5);

    // Out of bounds placement (row -1, column 0)
    assertFalse(redPlayer.playCard(board, 0, -1, 0),
            "Card should not be placed out of bounds (negative row).");
    // Out of bounds placement (row 3, column 0)
    assertFalse(redPlayer.playCard(board, 0, 3, 0),
            "Card should not be placed out of bounds (row exceeds board).");
    // Opponent's column (Blue's starting side)
    assertFalse(redPlayer.playCard(board, 0, 0, 4),
            "Card should not be placed on opponent's starting territory.");
    // Place a valid card, then attempt to place another one on the same spot
    assertTrue(redPlayer.playCard(board, 0, 0, 0),
            "Card should be successfully placed on a valid cell.");
    assertFalse(redPlayer.playCard(board, 0, 0, 0),
            "Card should not be placed on an occupied cell.");
    // Attempt to place a card that costs 3 pawns on a cell with only 1 pawn
    Player bluePlayer = new Player(Color.BLUE, List.of(new Card("ExpensiveCard",
            3, 5, new char[][]{
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'C', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'}
            }, Color.BLUE)), 5);
    assertFalse(bluePlayer.playCard(board, 0, 0, 4),
            "Card should not be placed when there are insufficient pawns.");
  }

  @Test
  public void testIsValidMove() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED,
            List.of(new Card("CheckMove", 1, 1, new char[][]{
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'C', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'}
            }, Color.RED)), 5);

    Card testCard = redPlayer.getHand().get(0);

    // Valid move on Red's side
    assertTrue(board.isValidMove(redPlayer, testCard, 0, 0),
            "Move should be valid for Red on its own side.");

    // Invalid move on Blue's side
    assertFalse(board.isValidMove(redPlayer, testCard, 0, 4),
            "Move should be invalid for Red on Blue's side.");

    // Invalid move out of bounds
    assertFalse(board.isValidMove(redPlayer, testCard, -1, 0),
            "Move should be invalid when out of bounds.");
    assertFalse(board.isValidMove(redPlayer, testCard, 3, 0),
            "Move should be invalid when row is out of bounds.");
  }

  @Test
  public void testPlaceCardUpdatesBoard() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, List.of(new Card("Placer",
            1, 1, new char[][]{
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'C', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'},
              {'X', 'X', 'X', 'X', 'X'}
            }, Color.RED)), 5);
    // Place a card and verify the board state
    assertTrue(redPlayer.playCard(board, 0, 0, 0),
            "Card should be placed successfully.");
    assertNotNull(board.getCell(0, 0).getCard(),
            "Cell should contain the placed card.");
    assertEquals("Placer", board.getCell(0, 0).getCard().getName(),
            "Card name should match.");
  }
}
