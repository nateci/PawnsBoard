package cs3500.controller;

import cs3500.pawnsboard.Game;
import cs3500.view.PawnsBoardView;

import javax.swing.JOptionPane;
import java.awt.Color;


/**
 * Controller that handles GUI interactions for Pawns Board.
 */
public class PawnsBoardViewControllerImpl implements PawnsBoardViewController,
        ModelFeatures, PlayerController {

  private final PawnsBoardView view;
  private Game game;
  private final Color playerColor;
  private boolean hasHandledGameOver = false;
  private boolean isPlayerTurn = false;
  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Private constructor for view implementation.
   * @param view represents the view for the model.
   * @param playerColor which player are we referencing.
   */

  public PawnsBoardViewControllerImpl(PawnsBoardView view, Color playerColor) {
    this.view = view;
    this.playerColor = playerColor;
  }

  @Override
  public void setGame(Game game) {
    this.game = game;
    game.addModelListener(this);
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (!isPlayerTurn) {
      view.setStatus("It's not your turn.");
      return;
    }
    this.selectedRow = row;
    this.selectedCol = col;
    view.setStatus("Selected cell at (" + row + ", " + col + ").");
    view.refresh();
  }

  @Override
  public void handleCardClick(int cardIndex) {
    if (!isPlayerTurn) {
      view.setStatus("It's not your turn.");
      return;
    }
    this.selectedCardIndex = cardIndex;
    view.setStatus("Card " + cardIndex + " selected.");
    view.refresh();
  }

  @Override
  public void confirmMove() {
    if (!isPlayerTurn) {
      view.setStatus("It's not your turn.");
      return;
    }

    if (selectedCardIndex == -1 || selectedRow == -1 || selectedCol == -1) {
      view.setStatus("Please select both a card and a cell before confirming.");
      return;
    }

    try {
      game.handlePlayCard(selectedCardIndex, selectedRow, selectedCol);
      view.setStatus("Move played! Waiting for opponent...");
      clearSelection();
      view.refresh();
    } catch (IllegalArgumentException e) {
      view.setStatus(e.getMessage());  // Show error in status bar, not popup
    } catch (Exception e) {
      // Unexpected failure – still use popup
      showError("Unexpected error: " + e.getMessage());
    }
  }


  @Override
  public void passTurn() {
    if (!isPlayerTurn) {
      view.setStatus("It's not your turn.");
      return;
    }
    clearSelection();
    game.handlePass();
    view.setStatus("Turn passed. Waiting for opponent...");
    view.refresh();
  }

  /**
   * clear the selection of the board.
   */
  private void clearSelection() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
  }

  /**
   * Just for the popup error message.
   * @param message represents an error message. (invalid move)
   */
  private void showError(String message) {
    JOptionPane.showMessageDialog(
            null,
            message,
            "Invalid Move",
            JOptionPane.ERROR_MESSAGE
    );
  }

  /**
   * Just represents a gameOver message popup.
   * @param message represents an popup with a game over.
   */

  private void showGameOver(String message) {
    JOptionPane.showMessageDialog(
            null,
            message,
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
    );
  }


  @Override
  public void notifyPlayerTurn(Color currentPlayerColor) {
    isPlayerTurn = (currentPlayerColor == playerColor);
    clearSelection();
    view.refresh();

    if (isPlayerTurn) {
      view.setTitle(getPlayerName() + " - Your Turn");
      view.setStatus("Your turn! Select a card and a board cell.");
    } else {
      view.setTitle(getPlayerName() + " - Waiting...");
      view.setStatus("Waiting for the other player...");
    }
  }

  @Override
  public void notifyGameOver(Color winner, int redScore, int blueScore) {
    //not needed here
  }

  @Override
  public void notifyInvalidMove(String message) {
    showError(message);
  }

  @Override
  public void startTurn() {
    // Not needed for human controller — handled via notifyPlayerTurn
  }

  @Override
  public void gameOver(Color winner, int redScore, int blueScore) {
    isPlayerTurn = false;
    clearSelection();
    view.refresh();

    String result = (winner == null)
            ? "It's a tie!"
            : (winner == Color.RED ? "Red wins!" : "Blue wins!");

    showGameOver("Game Over!\nRed: " + redScore + ", Blue: " + blueScore + "\n" + result);
    view.setStatus("Game over.");
    view.setTitle("Game Over");
  }

  @Override
  public Color getPlayerColor() {
    return playerColor;
  }

  /**
   * Method to get the players name.
   * @return a playerName based on the color.
   */
  private String getPlayerName() {
    return (playerColor == Color.RED) ? "Red Player" : "Blue Player";
  }
}
