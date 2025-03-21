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
    return true;
  }

  private void applyInfluence(Player player, Card card, int row, int col) {
    char[][] influence = player.getColor() == RED
            // if the player is red use the regular influence grid, mirrored for blue
            ? card.getInfluenceGrid() : card.getMirroredInfluenceGrid();
    int center = 2;  // the center of the influence grid
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        int targetRow = row + (r - center); // sub. center from original  results in influenced row
        int targetCol = col + (c - center); // sub. center from original  results in influenced col

        if (isValidCell(targetRow, targetCol)) {
          grid[targetRow][targetCol].influenceBoard(player.getColor(), influence[r][c]);  // applies
          // influence to the board
        }
      }
    }
  }

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
      System.out.print(scores[0] + " ");  // prints the red row scores to the board

      for (int c = 0; c < cols; c++) {
        System.out.print(grid[r][c].toTextualView()); // prints the actual card to the board
      }

      System.out.println(" " + scores[1]); // prints the blue row scores to the board
    }
  }

  @Override
  public int[] calculateRowScores(int row) {
    int redScore = 0;
    int blueScore = 0;
    for (int c = 0; c < cols; c++) {
      Cell cell = grid[row][c];
      if (cell.hasCard()) { // checks if a cell has a card
        Card card = cell.getCard();
        if (cell.getOwner() == Color.RED) {
          redScore += card.getValue(); // if the card belongs to red add value of card to red score
        } else {
          blueScore += card.getValue(); // if the card belongs to blue add value of card to blue
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
}
