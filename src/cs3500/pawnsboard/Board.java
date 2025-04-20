package cs3500.pawnsboard;


import java.awt.Color;

import static java.awt.Color.RED;

/**
 * Representation for a Board for Pawns Board.
 */
public class Board implements GameBoard {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  /**
   * An instance of how a board should be represented.
   *
   * @param rows the number of rows the board has
   * @param cols the number of columns the board has
   */
  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        grid[r][c] = new Cell();
      }
    }

    for (int r = 0; r < rows; r++) {
      grid[r][0].setPawns(1, RED);
      grid[r][cols - 1].setPawns(1, Color.BLUE);
    }
  }

  @Override
  public boolean placeCard(Player player, Card card, int row, int col) {
    if (!isValidMove(player, card, row, col)) {
      return false;
    }
    grid[row][col].setCard(card);
    applyInfluence(player, card, row, col);
    applyPostInfluenceCleanup();
    return true;
  }


  /**
   * Applies the influence of a card to the surrounding cells on the board.
   *
   * The card's 5x5 influence grid determines how nearby cells are affected.
   * If the player is RED, the original influence grid is used.
   * If the player is BLUE, a mirrored version is used (to reflect board symmetry).
   * The center of the influence grid ('C') aligns with the card's placement location.
   * Influence types can include:
   *   - 'I' (Increase pawn count or convert ownership)
   *   - 'U' (Upgrade: +1 modifier)
   *   - 'D' (Devalue: -1 modifier)
   *
   * @param player the player placing the card
   * @param card   the card being placed
   * @param row    the row of the placed card
   * @param col    the column of the placed card
   */
  private void applyInfluence(Player player, Card card, int row, int col) {
    // Use the regular or mirrored influence grid depending on the player's color
    char[][] influence = player.getColor() == RED
            ? card.getInfluenceGrid()
            : card.getMirroredInfluenceGrid();

    int center = 2; // Center of a 5x5 grid (used to align influence around the placed card)

    // Loop through the influence grid
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        // Convert local grid coordinates to board coordinates
        int targetRow = row + (r - center);
        int targetCol = col + (c - center);

        // If the cell is within bounds, apply influence
        if (isValidCell(targetRow, targetCol)) {
          grid[targetRow][targetCol].influenceBoard(player.getColor(), influence[r][c]);
        }
      }
    }
  }

  /**
   * Checks if a given board position is within the valid bounds.
   *
   * @param row the row index to check
   * @param col the column index to check
   * @return true if the position is valid, false otherwise
   */
  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }


  @Override
  public boolean isValidMove(Player player, Card card, int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      System.out.println("Invalid move: Out of bounds at (" + row + "," + col + ")");
      return false;
    }
    else {
      return grid[row][col].canPlaceCard(player, card);
    }
  }

  @Override
  public void printTextView() {
    for (int r = 0; r < rows; r++) {
      int[] scores = calculateRowScores(r);
      System.out.print(scores[0] + " ");
      for (int c = 0; c < cols; c++) {
        Cell cell = grid[r][c];
        String base = cell.toTextualView();
        int mod = cell.getValueModifier();
        if (mod != 0) {
          base += "(" + (mod > 0 ? "+" : "") + mod + ")";
        }
        System.out.print(base + " ");
      }
      System.out.println(" " + scores[1]);
    }
  }

  @Override
  public int[] calculateRowScores(int row) {
    int redScore = 0;
    int blueScore = 0;
    for (int c = 0; c < cols; c++) {
      Card card = grid[row][c].getCard();
      if (card != null) {
        int baseValue = card.getValue();
        int mod = grid[row][c].getValueModifier();
        int modifiedValue = baseValue + mod;

        if (modifiedValue <= 0) {
          grid[row][c].removeCardAndConvertToPawns(); //For valid row scores for ex. credit
          continue;
        }

        if (card.getOwner() == Color.RED) {
          redScore += modifiedValue;
        } else {
          blueScore += modifiedValue;
        }
      }
    }
    return new int[]{redScore, blueScore}; // returns both the blue and red scores
  }

  @Override
  public int calculateTotalScore(Color playerColor) {
    int totalScore = 0;

    for (int r = 0; r < rows; r++) {
      int[] rowScores = calculateRowScores(r);
      int redRowScore = rowScores[0];
      int blueRowScore = rowScores[1];
      if (redRowScore > blueRowScore && playerColor == Color.RED) {
        totalScore += redRowScore;  // only count red row score if it's greater than blue
      } else if (blueRowScore > redRowScore && playerColor == Color.BLUE) {
        totalScore += blueRowScore; // only count blue row score if it's greater than red
      }
    }

    return totalScore;
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid board position: (" + row + ", " + col + ")");
    }
    return grid[row][col];
  }

  @Override
  public Board copy() {
    // Create a new board with the same dimensions
    Board copiedBoard = new Board(this.rows, this.cols);

    // Create a copy of the board
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Cell originalCell = this.grid[r][c];
        Cell copiedCell = copiedBoard.grid[r][c];

        // Set pawns and owner for copy
        if (originalCell.getPawnCount() > 0) {
          copiedCell.setPawns(originalCell.getPawnCount(), originalCell.getOwner());
        }

        // Copy card if present
        if (originalCell.hasCard()) {
          Card originalCard = originalCell.getCard();

          // Deep copy the influence grid
          char[][] originalInfluence = originalCard.getInfluenceGrid();
          char[][] copiedInfluence = new char[5][5];
          for (int i = 0; i < 5; i++) {
            System.arraycopy(originalInfluence[i], 0, copiedInfluence[i], 0, 5);
          }

          // Create new card with copied data
          Card copiedCard = new Card(
                  originalCard.getName(),
                  originalCard.getCost(),
                  originalCard.getValue(),
                  copiedInfluence,
                  originalCard.getOwner()
          );

          // Place copied card into the new cell
          copiedCell.setCard(copiedCard);
        }
      }
    }
    return copiedBoard;
  }

  /**
   * Scans all cells on the board and removes any cards whose effective value is 0 or less.
   *
   * The effective value of a card is its base value plus any influence-based modifiers
   * applied by upgrade ('U') or devalue ('D') effects. If the value is 0 or below:
   * - The card is removed from the board.
   * - The cell is populated with pawns equal to the card's cost.
   * - Ownership is set to the original card's owner.
   * - The value modifier is reset and frozen to prevent further changes.
   *
   * This is called after new influence is applied to ensure all invalid cards are removed.
   */
  private void applyPostInfluenceCleanup() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Cell cell = grid[r][c];
        if (cell.hasCard()) {
          // Calculate effective value of the card after all modifiers
          int effectiveValue = cell.getCard().getValue() + cell.getValueModifier();

          // If value is zero or negative, remove the card and convert it to pawns
          if (effectiveValue <= 0) {
            cell.removeCardAndConvertToPawns();
          }
        }
      }
    }
  }
}
