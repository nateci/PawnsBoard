package cs3500.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.pawnsboard.Game;
import cs3500.strategy.Move;
import cs3500.strategy.Strategy;

/**
 * A controller for machine players that uses strategies to select moves.
 */
public class MachinePlayerController implements PlayerController, ModelFeatures {

  private Game game;
  private final Color playerColor;
  private final Strategy strategy;
  private final ReadOnlyBoardWrapper model;
  private final List<PlayerActionListener> actionListeners = new ArrayList<>();

  /**
   * Constructs a machine player controller.
   *
   * @param game The game being played
   * @param model The read-only model of the game
   * @param playerColor The color of the player
   * @param strategy The strategy to use for choosing moves
   */
  public MachinePlayerController(Game game, ReadOnlyBoardWrapper model,
                                 Color playerColor, Strategy strategy) {
    this.game = game;
    this.model = model;
    this.playerColor = playerColor;
    this.strategy = strategy;
  }

  /**
   * Adds a listener for player actions.
   *
   * @param listener The listener to add
   */
  public void addActionListener(PlayerActionListener listener) {
    actionListeners.add(listener);
  }

  @Override
  public void startTurn() {
    // Slight delay to make the machine player's moves more visible
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // Use strategy to choose a move
    List<Move> moves = strategy.chooseMoves(model, playerColor);

    if (moves.isEmpty()) {
      // No valid moves, so pass
      game.handlePass();
    } else {
      // Take the first move (strategies already return the best moves)
      Move move = moves.get(0);
      game.handlePlayCard(move.getCardIndex(), move.getRow(), move.getCol());
    }
  }

  @Override
  public void gameOver(Color winner, int redScore, int blueScore) {
    // Machine player doesn't need to do anything when the game is over
  }

  @Override
  public Color getPlayerColor() {
    return playerColor;
  }

  @Override
  public void notifyPlayerTurn(Color currentPlayerColor) {
    if (currentPlayerColor == playerColor) {
      startTurn();
    }
  }

  @Override
  public void notifyGameOver(Color winner, int redScore, int blueScore) {
    gameOver(winner, redScore, blueScore);
  }

  @Override
  public void notifyInvalidMove(String message) {
    // Machine players should never make invalid moves
    System.err.println("Machine player attempted invalid move: " + message);
  }


  public void setGame(Game game) {
    this.game = game;
    game.addModelListener(this);
  }

}
