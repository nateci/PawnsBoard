package cs3500.view;

import java.util.List;
import java.util.Scanner;

import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.Player;

/**
 * Textual view class for the Pawns Board game.
 */
public class PawnsBoardTextualView {
  private final Scanner scanner;

  public PawnsBoardTextualView() {
    this.scanner = new Scanner(System.in);
  }

  /** Prints the board with row scores. */
  public void printTextView(Board board) {
    for (int r = 0; r < board.getRows(); r++) {
      int[] scores = board.calculateRowScores(r);
      System.out.print("[" + scores[0] + "] "); // Red's row score

      for (int c = 0; c < board.getCols(); c++) {
        System.out.print(board.getCell(r, c).toTextualView()); // Print cell
      }

      System.out.println(" [" + scores[1] + "]"); // Blue's row score
    }
  }

  /** Displays the player's hand. */
  public void displayHand(Player player) {
    List<Card> hand = player.getHand();

    System.out.println(player.getColor() == java.awt.Color.RED ? "Red's Turn." : "Blue's Turn.");
    System.out.println("Your current hand:");

    if (hand.isEmpty()) {
      System.out.println("Your hand is empty.");
      return;
    }

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      System.out.println(i + ": " + card.getName()
              + " (Cost: " + card.getCost() + ", Value: " + card.getValue() + ")");
    }
  }

  /** Prompts the user for input. */
  public String getUserInput() {
    System.out.println("Enter 'pass' or card placement (row col cardIndex): ");
    return scanner.nextLine();
  }

  /** Displays the final winner. */
  public void displayWinner(int redScore, int blueScore) {
    System.out.println("Game Over!");
    System.out.println("Red Score: " + redScore);
    System.out.println("Blue Score: " + blueScore);

    if (redScore > blueScore) {
      System.out.println("Red Wins!");
    } else if (blueScore > redScore) {
      System.out.println("Blue Wins!");
    } else {
      System.out.println("It's a tie!");
    }
  }
}
