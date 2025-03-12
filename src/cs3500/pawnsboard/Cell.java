package cs3500.pawnsboard;

import java.awt.*;

/**
 * Representation for a cell on the board.
 */
public class Cell {
  private int pawns = 0;
  private Color owner = null;
  private Card card = null;

  /**
   * Setter method for the pawns of the game.
   *
   * @param count the number of pawns
   * @param owner the owner (player) that the pawns belong to.
   */
  public void setPawns(int count, Color owner) {
    this.pawns = count;
    this.owner = owner;
  }

  /**
   * Sets a card within a cell.
   *
   * @param card the card being used
   */
  public void setCard(Card card) {
    this.card = card;
    this.pawns = 0;
    this.owner = null;
  }

  /**
   * Checks if a cell has a card.
   *
   * @return if the cell has a card or not
   */
  public boolean hasCard() {
    return card != null;
  }

  /**
   * Gets the card at a cell.
   *
   * @return the card at the cell
   */
  public Card getCard() {
    return card;
  }

  /**
   * Getter method for the owner of a card at a cell.
   *
   * @return the owner of the card
   */
  public Color getOwner() {
    return (card != null) ? card.getOwner() : owner;
  }

  /**
   * Checks if you can place a card in a cell or not.
   *
   * @param player the player
   * @param card the card
   * @return if you can place a card or not
   */
  public boolean canPlaceCard(Player player, Card card) {
    return this.pawns >= card.getCost() && this.owner == player.getColor();
  }

  /**
   * Actual logic for influencing the board accroding to the influence grid.
   *
   * @param playerColor the color of the player (red or blue)
   * @param influenceType the influence type
   */
  public void influenceBoard(Color playerColor, char influenceType) {
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

  /**
   * Getter method for the number of pawns.
   *
   * @return the number of pawns
   */
  public int getPawnCount() {
    return pawns;
  }

  /**
   * The textual view of the game.
   *
   * @return a String of the textual view of the game
   */
  public String toTextualView() {
    if (card != null) return card.getOwner() == Color.RED ? "R" : "B";
    if (pawns > 0) return String.valueOf(pawns);
    return "_";
  }
}
