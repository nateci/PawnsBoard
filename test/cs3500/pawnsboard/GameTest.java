package cs3500.pawnsboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.view.PawnsBoardViewControllerImpl;
import cs3500.view.PawnsBoardViewImpl;

import static java.awt.Color.blue;
import static java.awt.Color.red;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the game. Ensures the game ends properly.
 */
public class GameTest {
  private InputStream originalIn;

  @BeforeEach
  public void setUpInput() {
    // Save the original input stream before each test
    originalIn = System.in;
  }

  @AfterEach
  public void restoreInput() {
    // Restore the original input stream after each test
    System.setIn(originalIn);
  }

  @Test
  public void testGameEndsAfterConsecutivePasses() {
    // Set up the board and players
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, java.util.List.of(
            new Card("Security", 1, 2, new char[][]{
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'I', 'C', 'I', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'}
            }, Color.RED)), 5);

    Player bluePlayer = new Player(Color.BLUE, java.util.List.of(
            new Card("Security", 1, 2, new char[][]{
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'I', 'C', 'I', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'}
            }, Color.BLUE)), 5);

    // Create input with "pass" command for both players
    String simulatedInput = "pass\npass\n";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(testIn);
    MockReadOnlyPawnsBoardModel mockModel = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    PawnsBoardViewImpl view = new PawnsBoardViewImpl(mockModel);
    PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view);
    ReadOnlyBoardWrapper wrapper = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer); // or currentPlayer
    Game game = new Game(board, redPlayer, bluePlayer, view, controller, wrapper);
    // Play the game (process both "pass" commands and end game)
    game.play();
    // Verify the game is over
    assertTrue(game.isGameOver());
  }
}
