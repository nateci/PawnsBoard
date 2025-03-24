package cs3500.strategy;

import java.util.List;

/**
 * Represents a move in Pawns Board (row, col, cardIndex).
 */
public class Move {
  private final int row;
  private final int col;
  private final int cardIndex;

  public Move(int row, int col, int cardIndex) {
    this.row = row;
    this.col = col;
    this.cardIndex = cardIndex;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getCardIndex() {
    return cardIndex;
  }

  @Override
  public String toString() {
    return "Move[row=" + row + ", col=" + col + ", cardIndex=" + cardIndex + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Move other = (Move) obj;
    return row == other.row && col == other.col && cardIndex == other.cardIndex;
  }

  @Override
  public int hashCode() {
    return 31 * row + 17 * col + cardIndex;
  }
}
