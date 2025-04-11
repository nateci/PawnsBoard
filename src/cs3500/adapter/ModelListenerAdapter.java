package cs3500.adapter;

import cs3500.controller.ModelFeatures;
import cs3500.pawnsboard.provider.controller.ModelStatusListener;
import cs3500.pawnsboard.provider.model.PlayerInt;

import java.awt.Color;

/**
 * Adapter that forwards our ModelFeatures events to provider's ModelStatusListener.
 */
public class ModelListenerAdapter implements ModelFeatures {
  private final ModelStatusListener listener;

  public ModelListenerAdapter(ModelStatusListener listener) {
        this.listener = listener;
  }

  @Override
  public void notifyPlayerTurn(Color playerColor) {
    PlayerInt.PlayerColor color = (playerColor == Color.RED) ?
              PlayerInt.PlayerColor.RED : PlayerInt.PlayerColor.BLUE;
    listener.turnChanged(color);
    }

  @Override
  public void notifyGameOver(Color winner, int redScore, int blueScore) {
    int[] scores = new int[] { redScore, blueScore };
    String winnerText;

    if (winner == null) {
      winnerText = "It's a tie!";
    } else if (winner == Color.RED) {
      winnerText = "Red wins!";
    } else {
      winnerText = "Blue wins!";
    }

    listener.gameOver(scores, winnerText);
  }

  @Override
  public void notifyInvalidMove(String message) {
    // The provider might not have a direct equivalent
    // We could show a dialog or message in the future if needed
  }
}
