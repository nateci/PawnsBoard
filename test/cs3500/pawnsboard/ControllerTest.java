package cs3500.pawnsboard;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.controller.PawnsBoardViewControllerImpl;
import cs3500.controller.PlayerController;
import cs3500.view.PawnsBoardViewImpl;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {
  private InputStream originalIn;

  @BeforeEach
  public void setUpInput() {
    this.originalIn = System.in;
  }

  @AfterEach
  public void restoreInput() {
    System.setIn(this.originalIn);
  }

  @Test
  public void testGameEndsAfterConsecutivePasses() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, List.of(new Card("Security", 1, 2, new char[][]{{'X', 'X', 'I', 'X', 'X'}, {'X', 'X', 'I', 'X', 'X'}, {'X', 'I', 'C', 'I', 'X'}, {'X', 'X', 'I', 'X', 'X'}, {'X', 'X', 'I', 'X', 'X'}}, Color.RED)), 5);
    Player bluePlayer = new Player(Color.BLUE, List.of(new Card("Security", 1, 2, new char[][]{{'X', 'X', 'I', 'X', 'X'}, {'X', 'X', 'I', 'X', 'X'}, {'X', 'I', 'C', 'I', 'X'}, {'X', 'X', 'I', 'X', 'X'}, {'X', 'X', 'I', 'X', 'X'}}, Color.BLUE)), 5);
    String simulatedInput = "pass\npass\n";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(testIn);
    MockReadOnlyPawnsBoardModel mockModel = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    PawnsBoardViewImpl view = new PawnsBoardViewImpl(mockModel);
    PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view, Color.RED);
    ReadOnlyBoardWrapper wrapper = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);
    Game game = new Game(board, redPlayer, bluePlayer, (PlayerController) view, controller, wrapper);
    game.play();
    Assertions.assertTrue(game.isGameOver());
  }
}
