package cs3500.strategy;

/**
 * Represents a move in the Pawns Board game.
 * <p>
 * A move is defined by the row and column on the board where a card is to be placed,
 * and the index of the card in the player's hand.
 * </p>
 * This class is immutable and provides proper implementations of
 * {@code equals}, {@code hashCode}, and {@code toString}.
 */
public class Move {
  private final int row;
  private final int col;
  private final int cardIndex;

  /**
   * Constructs a move with the given board coordinates and card index.
   *
   * @param row        the row on the board where the card will be placed
   * @param col        the column on the board where the card will be placed
   * @param cardIndex  the index of the card in the player's hand
   */
  public Move(int row, int col, int cardIndex) {
    this.row = row;
    this.col = col;
    this.cardIndex = cardIndex;
  }

  /**
   * Gets the row where the card will be placed.
   *
   * @return the row index
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column where the card will be placed.
   *
   * @return the column index
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the index of the card in the player's hand.
   *
   * @return the card index
   */
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
