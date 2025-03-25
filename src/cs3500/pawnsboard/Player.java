package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a player in the game who can play cards on the board.
 */
public class Player implements PawnsBoardPlayer {
  private final Color color;
  private final List<Card> hand;

  private final Queue<Card> drawPile;


  /**
   * An instance of a new player with the specified color and deck.
   *
   * @param color The player's color (RED or BLUE)
   * @param initialHandSize The player's deck of cards
   */
  public Player(Color color, List<Card> deck, int initialHandSize) {
    if (initialHandSize > deck.size() / 3) {
      throw new IllegalArgumentException("Hand size cannot exceed a third of the deck size.");
    }

    this.color = color;
    this.hand = new ArrayList<>(deck.subList(0, initialHandSize));
    this.drawPile = new LinkedList<>(deck.subList(initialHandSize, deck.size()));
  }

  /**
   * If card is not available, make sure that we are drawing.
   */
  public void drawCardIfAvailable() {
    if (!drawPile.isEmpty()) {
      hand.add(drawPile.poll());
    }
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public List<Card> getHand() {
    return hand;
  }

  @Override
  public boolean playCard(Board board, int cardIndex, int row, int col) {
    // Validate that cardIndex is within the bounds of the hand
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      System.out.println("Invalid card selection.");
      return false;
    }
    // Get the selected card
    Card card = hand.get(cardIndex);
    // Attempt to place the card on the board
    if (board.placeCard(this, card, row, col)) {
      // If successful, remove the card from the player's hand
      hand.remove(cardIndex);
      return true;
    } else {
      // If placement failed, return false without modifying the hand
      return false;
    }
  }

  @Override
  public boolean hasValidMoves(Board board) {
    // Check each card in the player's hand
    for (Card card : hand) {
      // Try every position on the board
      for (int r = 0; r < board.getRows(); r++) {
        for (int c = 0; c < board.getCols(); c++) {
          // If any valid move is found, return true immediately
          if (board.isValidMove(this, card, r, c)) {
            return true;
          }
        }
      }
    }
    // No valid moves were found
    return false;
  }

}

