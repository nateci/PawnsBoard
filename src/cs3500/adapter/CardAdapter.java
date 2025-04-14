package cs3500.adapter;

import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that converts our ReadOnlyPawnsBoardCard to the provider's Card.
 */
public class CardAdapter implements Card {
  private final ReadOnlyPawnsBoardCard card;

  public CardAdapter(ReadOnlyPawnsBoardCard card) {
    this.card = card;
  }

  /**
   * Helper method to convert a list of our cards to a list of adapter cards.
   *
   * @param cards the list of our cards to adapt
   * @return a list of adapted cards
   */
  public static List<Card> adaptCards(List<ReadOnlyPawnsBoardCard> cards) {
    List<Card> adaptedCards = new ArrayList<>();
    for (ReadOnlyPawnsBoardCard card : cards) {
      adaptedCards.add(new CardAdapter(card));
    }
    return adaptedCards;
  }

  @Override
  public String getName() {
    return card.getName();
  }

  @Override
  public int getCost() {
    return card.getCost();
  }

  @Override
  public int getValue() {
    return card.getValue();
  }

  @Override
  public char[][] getInfluenceGrid() {
    return card.getInfluenceGrid();
  }

  @Override
  public List<int[]> getInfluencedCoordinates(int boardRow, int boardCol, boolean isBlue) {
    List<int[]> coordinates = new ArrayList<>();
    char[][] grid = card.getInfluenceGrid();

    // Center of influence grid (usually 2,2 for a 5x5 grid)
    int center = grid.length / 2;

    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[r].length; c++) {
        // Skip cells that don't have influence
        if (grid[r][c] != 'I' && grid[r][c] != 'C') {
          continue;
        }

        // Translate grid coordinates to board coordinates
        int targetRow = boardRow + (r - center);
        int targetCol;

        if (isBlue) {
          // Mirror horizontally for blue player
          targetCol = boardCol - (c - center);
        } else {
          targetCol = boardCol + (c - center);
        }

        // Add valid coordinates
        coordinates.add(new int[]{targetRow, targetCol});
      }
    }

    return coordinates;
  }
}
