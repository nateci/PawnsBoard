package cs3500.pawnsboard;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

  @Test
  public void testValidAndInvalidCardPlacement() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, List.of(new Card("Test", 1, 2, new char[][]{
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'I', 'C', 'I', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'}
    }, Color.RED)));
    assertFalse(redPlayer.playCard(board, 0, 0, 4));
    assertTrue(redPlayer.playCard(board, 0, 0, 0));
    assertNotNull(board.getCell(0, 0).getCard());
    assertEquals("Test", board.getCell(0, 0).getCard().getName());
  }
}
