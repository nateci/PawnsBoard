package cs3500.pawnsboard;

import java.awt.Color;

/**
 * Representation for a cell on the board.
 */
public class Cell implements PawnsBoardCell, ReadOnlyPawnsBoardCell {
  private int pawns = 0;
  private Color owner = null;
  private Card card = null;

  @Override
  public void setPawns(int count, Color owner) {
    this.pawns = count;
    this.owner = owner;
  }

  @Override
  public void setCard(Card card) {
    this.card = card;
    this.pawns = 0;
    this.owner = null;
  }

  @Override
  public boolean hasCard() {
    return card != null;
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public Color getOwner() {
    return (card != null) ? card.getOwner() : owner;
  }

  @Override
  public boolean canPlaceCard(Player player, Card card) {
    return this.pawns >= card.getCost() && this.owner == player.getColor();
  }

  @Override
  public void influenceBoard(Color playerColor, char influenceType) {
    if (card != null) {
      return;
    }

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

  @Override
  public int getPawnCount() {
    return pawns;
  }

  @Override
  public String toTextualView() {
    if (card != null) {
      return card.getOwner() == Color.RED ? "R" : "B";
    }
    if (pawns > 0) {
      return String.valueOf(pawns);
    }
    return "_";
  }
}
