package cs3500.pawnsboard.view;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cs3500.pawnsboard.Board;
import cs3500.view.PawnsBoardTextualView;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the View class.
 * Ensures proper rendering of the board, player hands, and game messages.
 */
public class ViewTest {
  @Test
  public void testPrintTextView() {
    // Create a sample 3x5 board
    Board board = new Board(3, 5);
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    // Print the board using the view
    PawnsBoardTextualView view = new PawnsBoardTextualView();
    view.printTextView(board);
    // Restore normal output
    System.setOut(System.out);
    String newline = System.lineSeparator();
    String expectedOutput =
            "[0] 1___1 [0]" + newline
                    + "[0] 1___1 [0]" + newline
                    + "[0] 1___1 [0]" + newline;

    // Assert output matches expected board layout
    assertEquals(expectedOutput, outputStream.toString(),
            "Board rendering should match expected output.");
  }

  @Test
  public void testDisplayWinner() {
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    // Display game results
    PawnsBoardTextualView view = new PawnsBoardTextualView();
    view.displayWinner(5, 3);  // Red wins
    // Restore normal output
    System.setOut(System.out);
    // Expected winner message
    String newline = System.lineSeparator();
    String expectedOutput =
            "Game Over!" + newline
                    + "Red Score: 5" + newline
                    + "Blue Score: 3" + newline
                    + "Red Wins!" + newline;

    // Assert output matches expected winner message
    assertEquals(expectedOutput, outputStream.toString(),
            "Winner announcement should be correct.");
  }

  @Test
  public void testGetUserInput() {
    // Simulate user entering "pass"
    ByteArrayInputStream inputStream = new ByteArrayInputStream("pass\n".getBytes());
    System.setIn(inputStream);
    // Create view and get user input
    PawnsBoardTextualView view = new PawnsBoardTextualView();
    String userInput = view.getUserInput();
    // Restore normal input
    System.setIn(System.in);
    // Assert that the correct input was read
    assertEquals("pass", userInput, "User input should be correctly read.");
  }
}
