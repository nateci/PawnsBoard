package cs3500.pawnsboard;
import java.awt.*;

import static java.awt.Color.RED;

public class Board {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

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
            card.getInfluenceGrid() : card.getMirroredInfluenceGrid();
    int center = 2;
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        int targetRow = row + (r - center);
        int targetCol = col + (c - center);

        if (isValidCell(targetRow, targetCol)) {
          grid[targetRow][targetCol].applyInfluence(player.getColor(), influence[r][c]);
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

  public void printTextView() {
    for (int r = 0; r < rows; r++) {
      int[] scores = calculateRowScores(r);
      System.out.print(scores[0] + " ");

      for (int c = 0; c < cols; c++) {
        System.out.print(grid[r][c].toTextualView());
      }

      System.out.println(" " + scores[1]);
    }
  }

  public int[] calculateRowScores(int row) {
    int redScore = 0, blueScore = 0;
    for (int c = 0; c < cols; c++) {
      Cell cell = grid[row][c];
      if (cell.hasCard()) {
        Card card = cell.getCard();
        if (cell.getOwner() == Color.RED) {
          redScore += card.getValue();
        } else {
          blueScore += card.getValue();
        }
      }
    }

    return new int[]{redScore, blueScore};
  }



  public int calculateTotalScore(Color playerColor) {
    int totalScore = 0;

    for (int r = 0; r < rows; r++) {
      int[] rowScores = calculateRowScores(r);
      int redRowScore = rowScores[0];
      int blueRowScore = rowScores[1];
      if (redRowScore > blueRowScore && playerColor == Color.RED) {
        totalScore += redRowScore;
      } else if (blueRowScore > redRowScore && playerColor == Color.BLUE) {
        totalScore += blueRowScore;
      }
    }

    return totalScore;
  }


  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  //for testing
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid board position: (" + row + ", " + col + ")");
    }
    return grid[row][col];
  } //
}
