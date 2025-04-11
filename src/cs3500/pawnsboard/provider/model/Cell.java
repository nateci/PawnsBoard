package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.Player;

/**
 * Represents a cell in the Pawns Board game. A cell may be empty, contain pawns,
 * or hold a card. It provides methods to modify its content and query its state.
 */
public interface Cell {

  /**
   * Enumerates the types of content that a cell may contain.
   */
  enum CellContent { EMPTY, PAWNS, CARD }

  /**
   * Adds a pawn to this cell for the specified player. The effect depends on the
   * current cell content.
   *
   * @param player the player whose pawn is added.
   */
  void addPawn(Player player);

  /**
   * Converts ownership of all pawns in this cell to the specified new owner.
   *
   * @param newOwner the player to become the owner of the pawns.
   */
  void convertPawnOwnership(Player newOwner);

  /**
   * Places a card in this cell for the specified player, replacing any existing
   * pawn data.
   *
   * @param card   the card to be placed.
   * @param player the player placing the card.
   */
  void placeCard(Card card, Player player);

  /**
   * Returns the type of content present in this cell.
   *
   * @return the cell's content type.
   */
  CellContent getContent();

  /**
   * Returns the number of pawns currently in this cell.
   *
   * @return the pawn count.
   */
  int getPawnCount();

  /**
   * Returns the player who owns the content (pawns or card) in this cell.
   *
   * @return the owning player, or null if the cell is empty.
   */
  Player getOwner();

  /**
   * Returns the card placed in this cell.
   *
   * @return the placed card, or null if no card is present.
   */
  Card getPlacedCard();

  /**
   * Returns a string representation of this cell's state.
   *
   * @return a string describing the cell.
   */
  String toString();
}
