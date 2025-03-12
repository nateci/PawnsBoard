package cs3500.pawnsboard;
import java.awt.*;

import static java.awt.Color.RED;

/**
 * Representation for a Board for Pawns Board.
 */
public class Board {
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

  /**
   * method for placing a card onto the board.
   *
   * @param player The player placing the card (red or blue)
   * @param card the card the player is placing
   * @param row the row of the placed card
   * @param col the column of the placed card
   * @return if the card has been placed at the designated position
   */
  public boolean placeCard(Player player, Card card, int row, int col) {
    if (!isValidMove(player, card, row, col)) {
      return false;
    }
    grid[row][col].setCard(card);
    applyInfluence(player, card, row, col);
    return true;
  }

  private void applyInfluence(Player player, Card card, int row, int col) {
    char[][] influence = player.getColor() == RED ?
            card.getInfluenceGrid() : card.getMirroredInfluenceGrid(); // if the player is blue, use the
    int center = 2;   // the center of the influence grid              // use the mirrored influence grid
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        int targetRow = row + (r - center); // sub. center from original position results in influenced row
        int targetCol = col + (c - center); // sub. center from original position results in influenced col

        if (isValidCell(targetRow, targetCol)) {
          grid[targetRow][targetCol].influenceBoard(player.getColor(), influence[r][c]);  // applies influence grid
                                                                                          // to the board
        }
      }
    }
  }

  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  boolean isValidMove(Player player, Card card, int row, int col) {
    return grid[row][col].canPlaceCard(player, card);
  }

  /**
   * Prints the textual view of the game.
   */
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

  /**
   * Calculates the score of a designated row for both players.
   *
   * @param row the row that's currently being used to calculate the score
   * @return the score of both players for the designated row
   */
  public int[] calculateRowScores(int row) {
    int redScore = 0, blueScore = 0;
    for (int c = 0; c < cols; c++) {
      Cell cell = grid[row][c];
      if (cell.hasCard()) { // checks if a cell has a card
        Card card = cell.getCard();
        if (cell.getOwner() == Color.RED) {
          redScore += card.getValue(); // if the card belongs to red add value of card to red score
        } else {
          blueScore += card.getValue(); // if the card belongs to blue add value of card to blue score
        }
      }
    }

    return new int[]{redScore, blueScore}; // returns both the blue and red scores
  }

  /**
   * Calculates the total score of the game.
   *
   * @param playerColor the player's color (red or blue)
   * @return the total score of the winning player
   */
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

  /**
   * Getter method for the rows in the game board.
   *
   * @return the number of rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * Getter method for the columns in the game board.
   *
   * @return the number of columns
   */
  public int getCols() {
    return cols;
  }

  /**
   * Getter method for the cells of the game board.
   *
   * @param row the current row of the game board
   * @param col the current column of the game board
   * @return the designated cell of the game board
   */
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid board position: (" + row + ", " + col + ")");
    }
    return grid[row][col];
  }
}
