package cs3500;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCell;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

/**
 * A read-only wrapper for a game board and players.
 */
public class ReadOnlyBoardWrapper implements ReadOnlyPawnsBoardModel {
  private final Board board;
  private final Player redPlayer;
  private final Player bluePlayer;
  private Player currentPlayer;

  /**
   * Constructor for ReadOnlyBoardWrapper
   * @param board takes in a Board.
   * @param redPlayer takes in the red player.
   * @param bluePlayer takes in the blue player.
   * @param currentPlayer takes in whom the action is on.
   */
  public ReadOnlyBoardWrapper(Board board, Player redPlayer, Player bluePlayer, Player currentPlayer) {
    this.board = board;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = currentPlayer;
  }

  @Override
  public int getBoardRows() {
    return board.getRows();
  }

  @Override
  public int getBoardCols() {
    return board.getCols();
  }

  @Override
  public ReadOnlyPawnsBoardCell getCell(int row, int col) {
    return board.getCell(row, col);
  }

  @Override
  public Color getCurrentPlayerColor() {
    return currentPlayer.getColor();
  }

  @Override
  public List<ReadOnlyPawnsBoardCard> getCurrentPlayerHand() {
    return currentPlayer.getHand()
            .stream()
            .map(c -> (ReadOnlyPawnsBoardCard) c)
            .collect(Collectors.toList());
  }

  @Override
  public List<ReadOnlyPawnsBoardCard> getPlayerHand(Color playerColor) {
    if (playerColor.equals(Color.RED)) {
      return redPlayer.getHand()
              .stream()
              .map(c -> (ReadOnlyPawnsBoardCard) c)
              .collect(Collectors.toList());
    } else {
      return bluePlayer.getHand()
              .stream()
              .map(c -> (ReadOnlyPawnsBoardCard) c)
              .collect(Collectors.toList());
    }
  }

  @Override
  public int[] calculateRowScores(int row) {
    return board.calculateRowScores(row);
  }

  @Override
  public int calculateTotalScore(Color playerColor) {
    return board.calculateTotalScore(playerColor);
  }

  @Override
  public boolean isValidMove(int cardIndex, int row, int col) {
    List<Card> hand = currentPlayer.getHand();
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      return false;
    }
    Card card = hand.get(cardIndex);
    return board.isValidMove(currentPlayer, card, row, col);
  }

  @Override
  public boolean isGameOver() {
    return !redPlayer.hasValidMoves(board) && !bluePlayer.hasValidMoves(board);
  }

  @Override
  public Color getWinner() {
    int redScore = board.calculateTotalScore(Color.RED);
    int blueScore = board.calculateTotalScore(Color.BLUE);
    if (redScore > blueScore) return Color.RED;
    if (blueScore > redScore) return Color.BLUE;
    return null; // tie
  }

  @Override
  public boolean hasValidMoves(Color playerColor) {
    return playerColor.equals(Color.RED)
            ? redPlayer.hasValidMoves(board)
            : bluePlayer.hasValidMoves(board);
  }

  /**
   * Updates the current player reference (used by the GUI view to display correct hand/turn).
   * @param player the new current player
   */
  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }
}
