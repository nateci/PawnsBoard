package cs3500.pawnsboard;

import java.awt.*;
import java.util.List;

public class Player {
  private final Color color;
  private final List<Card> deck;
  private List<Card> hand;

  public Player(Color color, List<Card> deck) {
    this.color = color;
    this.deck = deck;
    this.hand = deck.subList(0, Math.min(5, deck.size()));
  }

  public Color getColor() {
    return color;
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

