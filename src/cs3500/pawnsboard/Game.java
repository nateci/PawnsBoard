package cs3500.pawnsboard;

import java.awt.Color;

import cs3500.view.PawnsBoardTextualView;

/**
 * Representation for an actual game and its functions.
 */
public class Game implements PawnsBoardGame {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private final PawnsBoardTextualView view;
  private Player currentPlayer;
  private boolean consecutivePasses = false;
  private boolean gameOver = false;

  /**
   * An instance of an actual game.
   *
   * @param board     the board
   * @param redPlayer the red player
   * @param bluePlayer the blue player
   */
  public Game(Board board, Player redPlayer, Player bluePlayer) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer;
    this.view = new PawnsBoardTextualView();
  }

  @Override
  public void play() {
    gameOver = false;
    while (!gameOver) {
      // Display current board state
      view.printTextView(board);
      // Prompt current player for their move
      view.displayHand(currentPlayer); // Show current hand
      String input = view.getUserInput();
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
    determineWinner();
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
    view.displayWinner(redScore, blueScore);
  }
}
