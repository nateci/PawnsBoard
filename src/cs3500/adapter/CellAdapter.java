package cs3500.adapter;

import cs3500.pawnsboard.provider.model.Cell;
import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.Player;

import java.awt.Color;

/**
 * Adapter that converts our ReadOnlyPawnsBoardCell to the provider's Cell.
 */
public class CellAdapter implements Cell {
  private final cs3500.pawnsboard.ReadOnlyPawnsBoardCell cell;

  public CellAdapter(cs3500.pawnsboard.ReadOnlyPawnsBoardCell cell) {
    this.cell = cell;
  }

  @Override
  public void addPawn(Player player) {
    throw new UnsupportedOperationException("Cannot modify read-only cell");
  }

  @Override
  public void convertPawnOwnership(Player newOwner) {
    throw new UnsupportedOperationException("Cannot modify read-only cell");
  }

  @Override
  public void placeCard(Card card, Player player) {
    throw new UnsupportedOperationException("Cannot modify read-only cell");
  }

  @Override
  public CellContent getContent() {
    if (cell.hasCard()) {
      return CellContent.CARD;
    } else if (cell.getPawnCount() > 0) {
      return CellContent.PAWNS;
    } else {
      return CellContent.EMPTY;
    }
  }

  @Override
  public int getPawnCount() {
    return cell.getPawnCount();
  }

  @Override
  public Player getOwner() {
    Color owner = cell.getOwner();
    if (owner == null) {
      return null;
    }

    // Since this is a read-only adapter, we just need a minimal Player implementation
    // that reports the correct color. The provider's view shouldn't be modifying this Player.
    return new PlayerAdapter(owner);
  }

  @Override
  public Card getPlacedCard() {
    if (!cell.hasCard()) {
      return null;
    }
    return new CardAdapter(cell.getCard());
  }

  @Override
  public String toString() {
    return cell.toTextualView();
  }
}
