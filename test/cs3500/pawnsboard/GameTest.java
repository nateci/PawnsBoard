package cs3500.pawnsboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

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
            }, Color.RED)));

    Player bluePlayer = new Player(Color.BLUE, java.util.List.of(
            new Card("Security", 1, 2, new char[][]{
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'I', 'C', 'I', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'}
            }, Color.BLUE)));

    // Create input with "pass" command for both players
    String simulatedInput = "pass\npass\n";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(testIn);

    Game game = new Game(board, redPlayer, bluePlayer);

    // Play the game (process both "pass" commands and end game)
    game.play();

    // Verify the game is over
    assertTrue(game.isGameOver());
  }
}
