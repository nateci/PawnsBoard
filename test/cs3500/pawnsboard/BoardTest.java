package cs3500.pawnsboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class BoardTest {

  @Test
  public void testBoardInitialization() {
    Board board = new Board(3, 5);
    for (int r = 0; r < 3; r++) {
      Assertions.assertEquals(1, board.getCell(r, 0).getPawnCount());
      Assertions.assertEquals(Color.RED, board.getCell(r, 0).getOwner());

      Assertions.assertEquals(1, board.getCell(r, 4).getPawnCount());
      Assertions.assertEquals(Color.BLUE, board.getCell(r, 4).getOwner());
    }for (int r = 0; r < 3; r++) {
      for (int c = 1; c < 4; c++) {
        Assertions.assertEquals(0, board.getCell(r, c).getPawnCount());
        Assertions.assertNull(board.getCell(r, c).getCard());
      }
    }
  }
}
