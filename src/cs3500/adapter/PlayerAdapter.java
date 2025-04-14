package cs3500.adapter;

import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that provides a minimal implementation of our Player class.
 * This adapter is used for read-only operations where the provider's view
 * only needs to know the player's color.
 */
public class PlayerAdapter extends Player {
  private final Color color;

  /**
   * Constructor for Player Adapter that takes in a color.
   * @param color for the player.
   */
  public PlayerAdapter(Color color) {
    // Minimal initialization with empty deck
    super(color, new ArrayList<>(), 0);
    this.color = color;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public List<Card> getHand() {
    // Return empty hand - provider view shouldn't use this method directly
    return new ArrayList<>();
  }

  @Override
  public boolean playCard(Board board, int cardIndex, int row, int col) {
    throw new UnsupportedOperationException("This adapter is read-only");
  }

  @Override
  public boolean hasValidMoves(Board board) {
    throw new UnsupportedOperationException("This adapter is read-only");
  }
}
