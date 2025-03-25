package cs3500.pawnsboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * A mock implementation of {@link ReadOnlyPawnsBoardModel} for testing purposes.
 * This model provides stubbed behavior for a board of fixed dimensions, including
 * pre-filled hands, fixed row scores, and valid moves.
 */
public class MockReadOnlyPawnsBoardModel implements ReadOnlyPawnsBoardModel {
  private final int rows;
  private final int cols;
  private final ReadOnlyPawnsBoardCell[][] cells;
  private final List<ReadOnlyPawnsBoardCard> redHand;
  private final List<ReadOnlyPawnsBoardCard> blueHand;
  private Color currentPlayer;
  public List<String> strategyTranscript = new ArrayList<>();

  /**
   * Just a constructor for the class.
   * @param rows the amount of rows that we are taking.
   * @param cols the amount of cols that we are taking.
   * @param currentPlayer the player whose turn it is.
   */
  public MockReadOnlyPawnsBoardModel(int rows, int cols, Color currentPlayer) {
    this.rows = rows;
    this.cols = cols;
    this.currentPlayer = currentPlayer;
    this.cells = new ReadOnlyPawnsBoardCell[rows][cols];

    // Initialize blank cells with MockCell
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        cells[r][c] = new MockCell();
      }
    }

    // Add some test cards
    Card card = new Card("TestCard", 1, 5, new char[][]{
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', 'C', '_', '_'},
            {'_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_'}
    }, currentPlayer);

    redHand = new ArrayList<>(List.of(card));
    blueHand = new ArrayList<>(List.of(card));
  }

  // Capture moves checked during strategy decisions
  @Override
  public boolean isValidMove(int cardIndex, int row, int col) {
    strategyTranscript.add("Checked: card " + cardIndex + " at (" + row + "," + col + ")");
    return true;  // Assume all moves are valid for simplicity
  }

  @Override
  public int[] calculateRowScores(int row) {
    strategyTranscript.add("Checked row score for row " + row);
    return new int[]{5, 5}; // Example row score for both players
  }

  @Override
  public int calculateTotalScore(Color playerColor) {
    strategyTranscript.add("Checked total score for " + playerColor);
    return playerColor == Color.RED ? 15 : 10; // Simple scores
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

  // Additional methods as needed
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
}
