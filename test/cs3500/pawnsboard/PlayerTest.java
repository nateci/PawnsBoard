package cs3500.pawnsboard;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class.
 * This test ensures that players can correctly place cards on the board
 * and that invalid placements are properly rejected.
 */
public class PlayerTest {
  @Test
  public void testValidAndInvalidCardPlacement() {
    // Create a 3x5 board
    Board board = new Board(3, 5);
    // Create a red player with a single test card
    Player redPlayer = new Player(Color.RED, List.of(new Card("Test", 1, 2, new char[][]{
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'I', 'C', 'I', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'}
    }, Color.RED)));
    // Attempt to place the card in an invalid location (column 4, which belongs to Blue)
    assertFalse(redPlayer.playCard(board, 0, 0, 4),
            "Card should not be placed in an opponent's column.");
    // Attempt to place the card in a valid location (column 0, which belongs to Red)
    assertTrue(redPlayer.playCard(board, 0, 0, 0),
            "Card should be successfully placed in Red's starting column.");
    // Ensure the card is now present on the board
    assertNotNull(board.getCell(0, 0).getCard(),
            "Cell (0,0) should contain the placed card.");
    // Ensure the correct card has been placed
    assertEquals("Test", board.getCell(0, 0).getCard().getName(),
            "Card placed at (0,0) should be 'Test'.");
  }
}