package cs3500.adapter;

import cs3500.pawnsboard.Game;
import cs3500.pawnsboard.GameBoard;
import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.provider.controller.ModelStatusListener;
import cs3500.pawnsboard.provider.model.GameModel;
import cs3500.pawnsboard.provider.model.PlayerInt;
import cs3500.strategy.Move;
import cs3500.ReadOnlyBoardWrapper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that connects our Game with the provider's GameModel.
 */
public class GameModelAdapter extends ModelAdapter implements GameModel {

  private final Game game;
  private final List<ModelStatusListener> listeners = new ArrayList<>();

  public GameModelAdapter(Game game, ReadOnlyBoardWrapper model) {
    super(model);
    this.game = game;
  }

  /**
   * Add a listener to be notified of model status changes.
   */
  public void addModelStatusListener(ModelStatusListener listener) {
    if (listener != null && !listeners.contains(listener)) {
      listeners.add(listener);
    }
  }

  @Override
  public void applyMove(Move move) {
    if (game != null) {
      game.handlePlayCard(move.getCardIndex(), move.getRow(), move.getCol());
    }
  }

  @Override
  public boolean checkGameEnd() {
    return game != null && game.isGameOver();
  }

  @Override
  public void switchTurn() {
    // This is handled internally by the game
  }

  @Override
  public GameBoard getBoard() {
    // We don't have direct access to the board - stub implementation
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    // We don't have direct access to the current player - stub implementation
    Color playerColor = super.getCurrentPlayerColor() == PlayerInt.PlayerColor.RED ?
        Color.RED : Color.BLUE;
    return new PlayerAdapter(playerColor);
  }

  @Override
  public void quit() {
    // Handle quitting the game - the provider might show a winners screen
    if (game != null && !game.isGameOver()) {
      game.determineWinner(); // Force game to end
    }
  }

  /**
   * Notify all listeners that the turn has changed.
   */
  public void notifyTurnChanged(Color newTurnColor) {
    PlayerInt.PlayerColor adapterColor = (newTurnColor == Color.RED) ?
         PlayerInt.PlayerColor.RED : PlayerInt.PlayerColor.BLUE;

    for (ModelStatusListener listener : listeners) {
      listener.turnChanged(adapterColor);
    }
  }

  /**
   * Notify all listeners that the game is over.
   */
  public void notifyGameOver() {
    int[] scores = calculateScores();
    String winner;

    if (scores[0] > scores[1]) {
      winner = "Red wins!";
    } else if (scores[1] > scores[0]) {
      winner = "Blue wins!";
    } else {
      winner = "It's a tie!";
    }

    for (ModelStatusListener listener : listeners) {
      listener.gameOver(scores, winner);
    }
  }
}
