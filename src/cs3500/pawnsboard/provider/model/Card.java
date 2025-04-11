package cs3500.pawnsboard.provider.model;

/**
 * The Card interface defines the behavior of cards used in the Pawns Board game.
 * Each card has a name, a cost, a value,and an influence grid that
 * specifies the cells it affects on the board.
 * Additionally, a card can compute the board coordinates it influences
 * when placed at a specified location,
 * taking into account the current player's perspective (with a mirrored grid for Blue players).
 */
public interface Card {

  /**
   * Returns the name of the card.
   *
   * @return the card's name.
   */
  String getName();

  /**
   * Returns the cost of the card.
   *
   * @return the cost as an integer.
   */
  int getCost();

  /**
   * Returns the value score of the card.
   *
   * @return the card's value score.
   */
  int getValue();

  /**
   * Returns the 5x5 influence grid of the card.
   * The grid defines the relative cells that will be affected when the card is played.
   *
   * @return a 2D char array representing the card's influence grid.
   */
  char[][] getInfluenceGrid();

  /**
   * Computes the board coordinates influenced by this card when placed at (boardRow, boardCol)
   * from the current player's perspective. For Blue, the grid is mirrored horizontally.
   *
   * @param boardRow row on board where the card is placed
   * @param boardCol column on board where the card is placed
   * @param isBlue whether the current player is Blue (true) or Red (false)
   * @return a list of int[] pairs representing the influenced board coordinates.
   */
  java.util.List<int[]> getInfluencedCoordinates(int boardRow, int boardCol, boolean isBlue);
}
