package cs3500.pawnsboard;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

/**
 * Representation for an actual game and its functions.
 */
public class Game implements PawnsBoardGame {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private Player currentPlayer;
  private boolean consecutivePasses = false;
  private boolean gameOver = false;

  /**
   * An instance of an actual game
   *
   * @param board the board
   * @param redPlayer the red player
   * @param bluePlayer the blue player
   */
  public Game(Board board, Player redPlayer, Player bluePlayer) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer;
  }

  @Override
  public void play() {
    Scanner scanner = new Scanner(System.in);
    gameOver = false;

    while (!gameOver) {
      // Display current board state
      board.printTextView();
      // Prompt current player for their move
      System.out.println((currentPlayer.getColor() == Color.RED ? "Red" : "Blue")
              + "'s turn. Enter 'pass' or card placement (row col cardIndex): ");
      // Display the player's available cards
      System.out.println("Your current hand: ");
      displayHand(currentPlayer);

      String input = scanner.nextLine();
      // Handle pass option
      if (input.equalsIgnoreCase("pass")) {
        // Check if this is the second consecutive pass, which ends the game
        if (consecutivePasses) {
          gameOver = true;
          break;
        }
        // Reset consecutive passes counter since player didn't pass
        consecutivePasses = true;
        switchTurn();
        continue;
      }
      // Parse player input for card placement
      String[] parts = input.split(" ");
      if (parts.length != 3) {
        System.out.println("Invalid move format. Try again.");
        continue;
      }

      try {
        // Parse the three input values: row, column, and card index
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int cardIndex = Integer.parseInt(parts[2]);
        // Attempt to play the card and switch turns if successful
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

  /**
   * Displays the current player's hand of cards. Cards are listed with an index number
   * that can be used when playing a card.
   *
   * @param player The player whose hand should be displayed
   */
  private void displayHand(Player player) {
    List<Card> hand = player.getHand();

    if (hand.isEmpty()) {
      System.out.println("Your hand is empty.");
      return;
    }
    // Display each card with its index and relevant details
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      System.out.println(i + ": " + card.getName() + " (Cost: " + card.getCost() + ", Value: " + card.getValue() + ")");
    }
  }

  @Override
  public boolean isGameOver() {
    return gameOver || (!redPlayer.hasValidMoves(board) && !bluePlayer.hasValidMoves(board));
  }

  /**
   * Switches the active player from red to blue or blue to red.
   * This method is called after a player completes their turn.
   */
  private void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }

  @Override
  public void determineWinner() {
    // Calculate total scores for both players
    int redScore = board.calculateTotalScore(Color.RED);
    int blueScore = board.calculateTotalScore(Color.BLUE);

    // Display the results
    System.out.println("Game Over!");
    System.out.println("Red Score: " + redScore);
    System.out.println("Blue Score: " + blueScore);

    // Determine the winner
    if (redScore > blueScore) {
      System.out.println("Red Wins!");
    } else if (blueScore > redScore) {
      System.out.println("Blue Wins!");
    } else {
      System.out.println("It's a tie!");
    }
  }
}
