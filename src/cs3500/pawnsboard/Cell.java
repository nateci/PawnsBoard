package cs3500.pawnsboard;

import java.awt.*;

public class Cell {
  private int pawns = 0;
  private Color owner = null;
  private Card card = null;

  public void setPawns(int count, Color owner) {
    this.pawns = count;
    this.owner = owner;
  }

  public void setCard(Card card) {
    this.card = card;
    this.pawns = 0;
    this.owner = null;
  }

  public boolean hasCard() {
    return card != null;
  }

  public Card getCard() {
    return card;
  }

  public Color getOwner() {
    return (card != null) ? card.getOwner() : owner;
  }

  public boolean canPlaceCard(Player player, Card card) {
    return this.pawns >= card.getCost() && this.owner == player.getColor();
  }

  public void applyInfluence(Color playerColor, char influenceType) {
    if (card != null) return;

    if (influenceType == 'I') {
      if (pawns == 0) {
        this.pawns = 1;
        this.owner = playerColor;
      } else if (this.owner == playerColor && pawns < 3) {
        this.pawns++;
      } else if (this.owner != playerColor) {
        this.owner = playerColor;
      }
    }
  }

  //FOR TESTING
  public int getPawnCount() {
    return pawns;
  }

  public String toTextualView() {
    if (card != null) return card.getOwner() == Color.RED ? "R" : "B";
    if (pawns > 0) return String.valueOf(pawns);
    return "_";
  }
}
