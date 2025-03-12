package cs3500.pawnsboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game who can play cards on the board.
 */
public class Player {
  private final Color color;
  private final List<Card> deck;
  private List<Card> hand;

  /**
   * An instance of a new player with the specified color and deck.
   *
   * @param color The player's color (RED or BLUE)
   * @param deck The player's deck of cards
   */
  public Player(Color color, List<Card> deck) {
    this.color = color;
    this.deck = new ArrayList<>(deck); // ✅ Copy deck to avoid modifying original
    this.hand = new ArrayList<>(deck.subList(0, Math.min(5, deck.size()))); // ✅ Create a new list for hand
  }

  /**
   * Getter method for the color of the player (red or blue).
   *
   * @return the color of the player
   */
  public Color getColor() {
    return color;
  }

  /**
   * Getter method for the hand of the player
   *
   * @return the hand of the player
   */
  public List<Card> getHand() {
    return hand;
  }

  /**
   * Attempts to play a card from the player's hand onto the board.
   *
   * @param board The game board
   * @param cardIndex The index of the card in the player's hand
   * @param row The row position where the card should be placed
   * @param col The column position where the card should be placed
   * @return if the card was successfully placed or not
   */
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

  /**
   * Checks whether the player has any valid moves available.
   *
   * @param board The game board
   * @return if the player has at least 1 valid move or not
   */
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

