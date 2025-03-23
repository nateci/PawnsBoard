package cs3500.view;

import cs3500.pawnsboard.Game;

/**
 * Controller that handles GUI interactions for Pawns Board.
 */
public class PawnsBoardViewControllerImpl implements PawnsBoardViewController {

  private final PawnsBoardView view;
  private Game game;

  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Controller constructor that takes in a view.
   * @param view define view.
   */
  public PawnsBoardViewControllerImpl(PawnsBoardView view) {
    this.view = view;
  }

  @Override
  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public void handleCellClick(int row, int col) {
    this.selectedRow = row;
    this.selectedCol = col;
  }

  @Override
  public void handleCardClick(int cardIndex) {
    this.selectedCardIndex = cardIndex;
  }

  @Override
  public void confirmMove() {
    if (selectedCardIndex == -1 || selectedRow == -1 || selectedCol == -1) {
      showError("Select both a card and a board cell before confirming.");
      return;
    }

    boolean success = game.handlePlayCard(selectedCardIndex, selectedRow, selectedCol);
    if (!success) {
      showError("Invalid move. Try again.");
    }

    clearSelection();
    view.refresh();
  }

  @Override
  public void passTurn() {
    game.handlePass();
    clearSelection();
    view.refresh();
  }


  /**
   * Clears the current selection state in the controller.
   */
  private void clearSelection() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
  }

  /**
   * Displays an error message to the user in a dialog box.
   * @param message the error message to display
   */
  private void showError(String message) {
    javax.swing.JOptionPane.showMessageDialog(
            null,
            message,
            "Invalid Move",
            javax.swing.JOptionPane.ERROR_MESSAGE
    );
  }
}
