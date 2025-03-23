package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockReadOnlyPawnsBoardModel implements ReadOnlyPawnsBoardModel {
  private final int rows;
  private final int cols;
  private final ReadOnlyPawnsBoardCell[][] cells;
  private final List<ReadOnlyPawnsBoardCard> redHand;
  private final List<ReadOnlyPawnsBoardCard> blueHand;
  private Color currentPlayer;

  public MockReadOnlyPawnsBoardModel(int rows, int cols, Color currentPlayer) {
    this.rows = rows;
    this.cols = cols;
    this.currentPlayer = currentPlayer;
    this.cells = new ReadOnlyPawnsBoardCell[rows][cols];

    // Initialize blank cells
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        cells[r][c] = new MockCell();
      }
    }

    // Dummy card for testing hand display
    Card card = new Card("TestCard", 1, 5, new char[][]{
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', 'C', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'}
    }, currentPlayer);

    redHand = new ArrayList<>(Collections.singletonList(card));
    blueHand = new ArrayList<>(Collections.singletonList(card));
  }

  @Override
  public int getBoardRows() {
    return rows;
  }

  @Override
  public int getBoardCols() {
    return cols;
  }

  @Override
  public ReadOnlyPawnsBoardCell getCell(int row, int col) {
    return cells[row][col];
  }

  @Override
  public Color getCurrentPlayerColor() {
    return currentPlayer;
  }

  @Override
  public List<ReadOnlyPawnsBoardCard> getCurrentPlayerHand() {
    return currentPlayer == Color.RED ? redHand : blueHand;
  }

  @Override
  public List<ReadOnlyPawnsBoardCard> getPlayerHand(Color playerColor) {
    return playerColor == Color.RED ? redHand : blueHand;
  }

  @Override
  public int[] calculateRowScores(int row) {
    return new int[]{5, 3};
  }

  @Override
  public int calculateTotalScore(Color playerColor) {
    return playerColor == Color.RED ? 15 : 10;
  }

  @Override
  public boolean isValidMove(int cardIndex, int row, int col) {
    return true;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Color getWinner() {
    return null;
  }

  @Override
  public boolean hasValidMoves(Color playerColor) {
    return true;
  }
}
