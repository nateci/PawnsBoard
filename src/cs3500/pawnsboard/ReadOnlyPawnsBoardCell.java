package cs3500.pawnsboard;

import java.awt.*;

/**
 * A read-only interface for a cell within the Pawns Board game.
 */
public interface ReadOnlyPawnsBoardCell {

  /**
   * Checks if the cell has a card.
   *
   * @return true if the cell has a card, false otherwise
   */
  boolean hasCard();

  /**
   * Gets a read-only representation of the card in this cell, if any.
   *
   * @return the card in this cell, or null if no card
   */
  ReadOnlyPawnsBoardCard getCard();

  /**
   * Gets the owner of this cell (based on the card or pawns).
   *
   * @return the color of the owner, or null if unowned
   */
  Color getOwner();

  /**
   * Gets the number of pawns in this cell.
   *
   * @return the number of pawns
   */
  int getPawnCount();

  /**
   * Gets the textual representation of this cell.
   *
   * @return a string representation of this cell
   */
  String toTextualView();
}
