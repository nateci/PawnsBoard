package cs3500.pawnsboard;

import java.awt.*;
import java.util.Scanner;

public class Game {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private Player currentPlayer;
  private boolean consecutivePasses = false;

  private boolean gameOver = false;


  public Game(Board board, Player redPlayer, Player bluePlayer) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer; //oh lawd jesus sit that thang on me
  }

  public void play() {
    Scanner scanner = new Scanner(System.in);
    boolean gameOver = false;

    while (!gameOver) { //isnt this not always true i initializedthis as false
      board.printTextView();
      System.out.println((currentPlayer.getColor() == Color.RED ? "Red" : "Blue")
              + "'s turn. Enter 'pass' or card placement (row col cardIndex): ");
      String input = scanner.nextLine();

      if (input.equalsIgnoreCase("pass")) {
        if (consecutivePasses) {
          gameOver = true;
          break;
        }
        consecutivePasses = true;
        switchTurn();
        continue;
      }
      String[] parts = input.split(" ");
      if (parts.length != 3) {
        System.out.println("Invalid move format. Try again.");
        continue;
      }

      try {
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int cardIndex = Integer.parseInt(parts[2]);

        if (currentPlayer.playCard(board, cardIndex, row, col)) {
          consecutivePasses = false;
          switchTurn();
        } else {
          System.out.println("Invalid move. Try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Use numbers for row, col, and card index.");
      }
    }

    scanner.close();
    determineWinner();
  }

  //FOR TESTING
  public boolean isGameOver() {
    return gameOver || (!redPlayer.hasValidMoves(board) && !bluePlayer.hasValidMoves(board));
  }


  private void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }

  void determineWinner() {
    int redScore = board.calculateTotalScore(Color.RED);
    int blueScore = board.calculateTotalScore(Color.BLUE);

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
  } //3/5 rule run it back
}
