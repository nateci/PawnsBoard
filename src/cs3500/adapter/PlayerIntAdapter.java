package cs3500.adapter;

import cs3500.pawnsboard.provider.model.PlayerInt;
import cs3500.pawnsboard.provider.model.Card;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Adapter that converts our Player color to the provider's PlayerInt.
 */
public class PlayerIntAdapter implements PlayerInt {
  private final Color color;

  public PlayerIntAdapter(Color color) {
    this.color = color;
  }

  @Override
  public PlayerColor getColor() {
    return (color == Color.RED) ? PlayerColor.RED : PlayerColor.BLUE;
  }

  @Override
  public Queue<Card> getDeck() {
    // Stub implementation - this is only used for the interface
    return new LinkedList<>();
  }

  @Override
  public List<Card> getHand() {
    // Stub implementation - this is only used for the interface
    return new ArrayList<>();
  }

  @Override
  public void drawCard() {
    // No-op for adapter
  }

  @Override
  public void removeCardFromHand(Card card) {
    // No-op for adapter
  }
}
