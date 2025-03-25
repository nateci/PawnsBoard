package cs3500.pawnsboard.model;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import cs3500.pawnsboard.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the Board class.
 * This test ensures that the board is correctly initialized with pawns and empty spaces.
 */
public class BoardTest {

  @Test
  public void testBoardInitialization() {
    // Create a 3x5 board
    Board board = new Board(3, 5);
    // Verify initial pawn placement for both players
    for (int r = 0; r < 3; r++) {
      // The first column (0) should have 1 red pawn
      assertEquals(1, board.getCell(r, 0).getPawnCount(),
              "Cell (" + r + ",0) should have 1 red pawn.");
      assertEquals(Color.RED, board.getCell(r, 0).getOwner(),
              "Cell (" + r + ",0) should be owned by Red.");
      // The last column (4) should have 1 blue pawn
      assertEquals(1, board.getCell(r, 4).getPawnCount(),
              "Cell (" + r + ",4) should have 1 blue pawn.");
      assertEquals(Color.BLUE, board.getCell(r, 4).getOwner(),
              "Cell (" + r + ",4) should be owned by Blue.");
    }
    // Verify that all middle cells (columns 1-3) are empty
    for (int r = 0; r < 3; r++) {
      for (int c = 1; c < 4; c++) {
        assertEquals(0, board.getCell(r, c).getPawnCount(),
                "Cell (" + r + "," + c + ") should be empty.");
        assertNull(board.getCell(r, c).getCard(),
                "Cell (" + r + "," + c + ") should not contain a card.");
      }
    }
  }
}
