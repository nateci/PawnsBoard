package cs3500.pawnsboard;

import java.awt.Color;
import java.util.List;

/**
 * Player interface representing all functions of a player within the Pawns Board game.
 */
public interface PawnsBoardPlayer {

  /**
   * Getter method for the color of the player (red or blue).
   *
   * @return the color of the player
   */
  Color getColor();

  /**
   * Getter method for the hand of the player.
   *
   * @return the hand of the player
   */
  List<Card> getHand();

  /**
   * Attempts to play a card from the player's hand onto the board.
   *
   * @param board The game board
   * @param cardIndex The index of the card in the player's hand
   * @param row The row position where the card should be placed
   * @param col The column position where the card should be placed
   * @return if the card was successfully placed or not
   */
  boolean playCard(Board board, int cardIndex, int row, int col);

  /**
   * Checks whether the player has any valid moves available.
   *
   * @param board The game board
   * @return if the player has at least 1 valid move or not
   */
  boolean hasValidMoves(Board board);
}
