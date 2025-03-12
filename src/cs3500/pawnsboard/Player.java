package cs3500.pawnsboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
  private final Color color;
  private final List<Card> deck;
  private List<Card> hand;

  public Player(Color color, List<Card> deck) {
    this.color = color;
    this.deck = new ArrayList<>(deck); // ✅ Copy deck to avoid modifying original
    this.hand = new ArrayList<>(deck.subList(0, Math.min(5, deck.size()))); // ✅ Create a new list for hand
  }

  public Color getColor() {
    return color;
  }

  public List<Card> getHand() {
    return hand;
  }


  public boolean playCard(Board board, int cardIndex, int row, int col) {
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      System.out.println("Invalid card selection.");
      return false;
    }

    Card card = hand.get(cardIndex);
    if (board.placeCard(this, card, row, col)) {
      hand.remove(cardIndex);
      return true;
    } else {
      return false;
    }
  }

  public boolean hasValidMoves(Board board) {
    for (Card card : hand) {
      for (int r = 0; r < board.getRows(); r++) {
        for (int c = 0; c < board.getCols(); c++) {
          if (board.isValidMove(this, card, r, c)) {
            return true;
          }
        }
      }
    }
    return false;
  }

}

