package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.controller.ModelFeatures;
import cs3500.view.PawnsBoardView;
import cs3500.controller.PawnsBoardViewController;

/**
 * Game class using GUI view and controller.
 */
public class Game implements PawnsBoardGame {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private final PawnsBoardView view;
  private final PawnsBoardViewController controller;
  private final ReadOnlyBoardWrapper modelWrapper;
  private Player currentPlayer;
  private boolean consecutivePasses = false;
  private boolean gameOver = false;
  private final List<ModelFeatures> modelListeners = new ArrayList<>();


  /**
   * Constructs a game with GUI support.
   *
   * @param board      the board
   * @param redPlayer  red player
   * @param bluePlayer blue player
   * @param view       GUI view
   * @param controller controller to handle user interaction
   */
  public Game(Board board,
              Player redPlayer,
              Player bluePlayer,
              PawnsBoardView view,
              PawnsBoardViewController controller,
              ReadOnlyBoardWrapper modelWrapper) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer;
    this.view = view;
    this.controller = controller;

    this.view.setController(controller);
    this.view.makeVisible();
    controller.setGame(this); // so controller can call methods like playCard()
    this.modelWrapper = modelWrapper;

  }

  /**
   * Adds a listener for model features/events.
   *
   * @param listener The listener to add
   */
  public void addModelListener(ModelFeatures listener) {
    modelListeners.add(listener);
  }

  /**
   * Starts the game.
   */
  @Override
  public void play() {
    gameOver = false;
    view.refresh(); // initial display
  }

  /**
   * Called by controller when a player chooses to play a card.
   */
  public boolean handlePlayCard(int cardIndex, int row, int col) {
    if (gameOver) {
      return false;
    }

    if (currentPlayer.playCard(board, cardIndex, row, col)) {
      consecutivePasses = false;
      currentPlayer.drawCardIfAvailable();
      switchTurn();
      view.refresh();

      if (isGameOver()) {
        determineWinner();
      }

      return true;
    }

    try {
      if (currentPlayer.playCard(board, cardIndex, row, col)) {
        consecutivePasses = false;
        currentPlayer.drawCardIfAvailable();
        switchTurn();
        view.refresh();

        if (isGameOver()) {
          determineWinner();
        }

        return true;
      } else {
        // Notify of invalid move
        for (ModelFeatures listener : modelListeners) {
          listener.notifyInvalidMove("Invalid move. Try again.");
        }
        return false;
      }
    } catch (IllegalArgumentException e) {
      // Handle exceptions from the model
      for (ModelFeatures listener : modelListeners) {
        listener.notifyInvalidMove(e.getMessage());
      }
      return false;
    }
  }

  /**
   * Called by controller when a player chooses to pass.
   */
  public void handlePass() {
    if (gameOver) {
      return;
    }

    if (consecutivePasses) {
      gameOver = true;
      determineWinner();
    } else {
      consecutivePasses = true;
      currentPlayer.drawCardIfAvailable();
      switchTurn();
      view.refresh();
    }
  }

  private void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
    modelWrapper.setCurrentPlayer(currentPlayer);

    // Notify it's the new player's turn
    notifyPlayerTurn();
  }

  /**
   * Notifies listeners that it's a player's turn.
   */
  private void notifyPlayerTurn() {
    for (ModelFeatures listener : modelListeners) {
      listener.notifyPlayerTurn(currentPlayer.getColor());
    }
  }


  @Override
  public void determineWinner() {
    int redScore = board.calculateTotalScore(Color.RED);
    int blueScore = board.calculateTotalScore(Color.BLUE);

    Color winner = null;
    if (redScore > blueScore) {
      winner = Color.RED;
    } else if (blueScore > redScore) {
      winner = Color.BLUE;
    }

    // Notify listeners of game over
    for (ModelFeatures listener : modelListeners) {
      listener.notifyGameOver(winner, redScore, blueScore);
    }

    // Display in GUI: modal popup or status label update
    JOptionPane.showMessageDialog(null,
            "Game Over!\nRed: " + redScore + " | Blue: " + blueScore + "\n"
                    + (redScore > blueScore ? "Red Wins!" :
                            blueScore > redScore ? "Blue Wins!" : "It's a tie!"));
  }

  @Override
  public boolean isGameOver() {
    return gameOver || (!redPlayer.hasValidMoves(board) && !bluePlayer.hasValidMoves(board));
  }
}
