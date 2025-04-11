package cs3500.pawnsboard.provider.model;

/**
 * The PlayerInt interface defines the operations for a game player.
 * A player has a color, a deck (queue of cards), and a hand (list of cards). It also
 * supports drawing a card and removing a card from the hand.
 */
public interface PlayerInt {

  /**
   * The PlayerColor enum indicates the color of a player in the game.
   */
  enum PlayerColor {

    /**
     * Red color for a player.
     */
    RED,

    /**
     * Blue color for a player.
     */
    BLUE
  }

  /**
   * Returns the player's color.
   *
   * @return the player's color.
   */
  PlayerColor getColor();

  /**
   * Returns the player's deck.
   *
   * @return a queue of cards representing the deck.
   */
  java.util.Queue<Card> getDeck();

  /**
   * Returns the player's hand.
   *
   * @return a list of cards representing the hand.
   */
  java.util.List<Card> getHand();

  /**
   * Draws a card from the deck and adds it to the hand.
   */
  void drawCard();

  /**
   * Removes the specified card from the player's hand.
   *
   * @param card the card to remove.
   */
  void removeCardFromHand(Card card);
}
