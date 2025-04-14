package cs3500.adapter;

import cs3500.pawnsboard.Game;
import cs3500.pawnsboard.provider.controller.PlayerActionListener;
import cs3500.pawnsboard.provider.controller.ModelStatusListener;
import cs3500.pawnsboard.provider.model.PlayerInt;
import cs3500.pawnsboard.provider.view.PawnsBoardView;
import cs3500.controller.PlayerController;

import java.awt.Color;

/**
 * Adapter that connects our Game and PlayerController with the provider's view and listeners.
 */
public class ControllerAdapter implements PlayerActionListener, ModelStatusListener, PlayerController {
  private final Game game;
  private final Color playerColor;
  private final PawnsBoardView view;
  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  public ControllerAdapter(Game game, PawnsBoardView view, Color playerColor) {
    this.game = game;
    this.view = view;
    this.playerColor = playerColor;

    // Register this adapter as a listener for the view
    view.addPlayerActionListener(this);
  }

  @Override
  public void cardSelected(int cardIndex) {
    this.selectedCardIndex = cardIndex;
    view.refresh();
  }

  @Override
  public void cellSelected(int row, int col) {
    this.selectedRow = row;
    this.selectedCol = col;
    view.refresh();
  }

  @Override
  public void confirmMove() {
    if (game != null && selectedCardIndex != -1 && selectedRow != -1 && selectedCol != -1) {
      game.handlePlayCard(selectedCardIndex, selectedRow, selectedCol);
      resetSelections();
    }
  }

  @Override
  public void passMove() {
    if (game != null) {
      game.handlePass();
      resetSelections();
    }
  }

  private void resetSelections() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
    if (view != null) {
      view.clearSelections();
      view.refresh();
    }
  }

  // ModelStatusListener implementation
  @Override
  public void turnChanged(PlayerInt.PlayerColor newTurn) {
    boolean isMyTurn = (newTurn == PlayerInt.PlayerColor.RED && playerColor == Color.RED) ||
                (newTurn == PlayerInt.PlayerColor.BLUE && playerColor == Color.BLUE);

    if (!isMyTurn) {
      resetSelections();
    }

    if (view != null) {
      view.refresh();
    }
  }

  @Override
  public void gameOver(int[] finalScores, String winner) {
    if (view != null) {
      view.showWinnersScreen();
    }
  }

  // PlayerController implementation
  @Override
  public void startTurn() {
    // The view will handle this through refresh
    if (view != null) {
      view.refresh();
    }
  }

  @Override
  public void gameOver(Color winner, int redScore, int blueScore) {
    if (view != null) {
      view.showWinnersScreen();
    }
  }

  @Override
  public Color getPlayerColor() {
    return playerColor;
  }
}
