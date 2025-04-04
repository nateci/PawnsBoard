package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.controller.ModelFeatures;
import cs3500.controller.PlayerController;

/**
 * Game class that coordinates gameplay between two players (GUI or AI).
 */
public class Game implements PawnsBoardGame {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private final PlayerController redController;
  private final PlayerController blueController;
  private final ReadOnlyBoardWrapper modelWrapper;

  private Player currentPlayer;
  private boolean consecutivePasses = false;
  private boolean gameOver = false;
  private final List<ModelFeatures> modelListeners = new ArrayList<>();

  /**
   * Constructs a new game with specified players and controllers.
   *
   * @param board           The game board
   * @param redPlayer       Red player
   * @param bluePlayer      Blue player
   * @param redController   Controller for red
   * @param blueController  Controller for blue
   * @param modelWrapper    Read-only model for strategies and views
   */
  public Game(Board board,
              Player redPlayer,
              Player bluePlayer,
              PlayerController redController,
              PlayerController blueController,
              ReadOnlyBoardWrapper modelWrapper) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.redController = redController;
    this.blueController = blueController;
    this.currentPlayer = redPlayer;
    this.modelWrapper = modelWrapper;

    this.modelWrapper.setCurrentPlayer(currentPlayer);
  }

  /**
   * Adds a listener for model features/events.
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
    notifyPlayerTurn();
  }

  /**
   * Called by controller when a player chooses to play a card.
   */
  public boolean handlePlayCard(int cardIndex, int row, int col) {
    if (gameOver) {
      return false;
    }

    try {
      if (currentPlayer.playCard(board, cardIndex, row, col)) {
        consecutivePasses = false;
        currentPlayer.drawCardIfAvailable();
        switchTurn();
        if (isGameOver()) {
          determineWinner();
        }
        return true;
      } else {
        notifyInvalidMove("Invalid move. Try again.");
        return false;
      }
    } catch (IllegalArgumentException e) {
      notifyInvalidMove(e.getMessage());
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
    }
  }

  private void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
    modelWrapper.setCurrentPlayer(currentPlayer);
    notifyPlayerTurn();
  }

  /**
   * Notifies all model listeners which player's turn it is.
   */
  private void notifyPlayerTurn() {
    for (ModelFeatures listener : modelListeners) {
      listener.notifyPlayerTurn(currentPlayer.getColor());
    }

    // Notify the appropriate controller
    if (currentPlayer == redPlayer) {
      redController.startTurn();
    } else {
      blueController.startTurn();
    }
  }

  /**
   * Notifies all model listeners that the game is over.
   */
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

    for (ModelFeatures listener : modelListeners) {
      listener.notifyGameOver(winner, redScore, blueScore);
    }

    redController.gameOver(winner, redScore, blueScore);
    blueController.gameOver(winner, redScore, blueScore);
  }

  private void notifyInvalidMove(String message) {
    for (ModelFeatures listener : modelListeners) {
      listener.notifyInvalidMove(message);
    }
  }

  @Override
  public boolean isGameOver() {
    return gameOver || (!redPlayer.hasValidMoves(board) && !bluePlayer.hasValidMoves(board));
  }
}
