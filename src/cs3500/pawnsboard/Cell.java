package cs3500.pawnsboard;

import java.awt.Color;

/**
 * Representation for a cell on the board.
 */
public class Cell implements PawnsBoardCell, ReadOnlyPawnsBoardCell {
  private int pawns = 0;
  private Color owner = null;
  private Card card = null;
  private int valueModifier = 0;
  private boolean modifierFrozen = false;

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
    System.out.printf("canPlaceCard: pawns=%d, cost=%d, owner=%s, playerColor=%s%n",
            this.pawns, card.getCost(), this.owner, player.getColor());
    return this.pawns >= card.getCost() && this.owner == player.getColor();
  }


  /**
   * Applies influence to this cell based on the given influence type and player color.
   * Influence types:
   * - 'I' (Increase): Adds or updates pawn ownership. Only applies if no card is on the cell.
   * - 'U' (Upgrade): Increases the value modifier of the cell by 1 (if not frozen).
   * - 'D' (Devalue): Decreases the value modifier of the cell by 1 (if not frozen).
   *
   * @param playerColor   The color of the player applying the influence.
   * @param influenceType The type of influence ('I', 'U', or 'D').
   */
  public void influenceBoard(Color playerColor, char influenceType) {
    if (card == null) {
      // === Influence Type: 'I' (Pawn Increase) ===
      if (influenceType == 'I') {
        if (pawns == 0) {
          // Empty cell: claim it with one pawn
          this.pawns = 1;
          this.owner = playerColor;
        } else if (this.owner == playerColor && pawns < 3) {
          // Already owned by this player and not maxed: add a pawn
          this.pawns++;
        } else if (this.owner != playerColor) {
          // Owned by opponent: overwrite ownership with 1 pawn
          this.owner = playerColor;
        }
      }
    }

    // === Influence Type: 'U' or 'D' (Upgrade or Devalue) ===
    // Only modify value if the modifier is not frozen
    if (!modifierFrozen) {
      if (influenceType == 'U') {
        valueModifier++;
      } else if (influenceType == 'D') {
        valueModifier--;
      }
    }
  }

  /**
   * Returns the total value modifier applied to this cell.
   * This value is the net effect of upgrade ('U') and devalue ('D') influences.
   *
   * @return the current value modifier.
   */
  public int getValueModifier() {
    return valueModifier;
  }

  /**
   * Converts a card currently on this cell into pawns if its value drops to 0 or less.
   * This is triggered as part of post-influence cleanup when cards are devalued.
   *
   * - The card is removed from the cell.
   * - The cell is populated with pawns equal to the card's cost.
   * - Ownership is set to the original card's owner.
   * - All value modifiers on the cell are reset and frozen.
   */
  public void removeCardAndConvertToPawns() {
    if (card != null) {
      this.pawns = card.getCost();        // Add pawns equal to the cost of the card
      this.owner = card.getOwner();       // Set ownership to the card's owner
      this.card = null;                   // Remove the card
      this.valueModifier = 0;             // Reset modifiers
      this.modifierFrozen = true;         // Freeze modifiers to prevent future changes
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
