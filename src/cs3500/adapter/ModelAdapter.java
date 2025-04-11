package cs3500.adapter;

import cs3500.pawnsboard.provider.model.ReadOnlyGameModel;
import cs3500.pawnsboard.provider.model.Cell;
import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.provider.model.PlayerInt;
import cs3500.strategy.Move;
import cs3500.ReadOnlyBoardWrapper;

import java.awt.Color;
import java.util.List;

/**
 * Adapter that converts our ReadOnlyBoardWrapper to the provider's ReadOnlyGameModel.
 */
public class ModelAdapter implements ReadOnlyGameModel {
  private final ReadOnlyBoardWrapper model;

  public ModelAdapter(ReadOnlyBoardWrapper model) {
    this.model = model;
  }

  @Override
  public int getBoardRows() {
    return model.getBoardRows();
  }

  @Override
  public int getBoardCols() {
    return model.getBoardCols();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public PlayerInt.PlayerColor getCurrentPlayerColor() {
    Color currentColor = model.getCurrentPlayerColor();
    return (currentColor == Color.RED) ?
        PlayerInt.PlayerColor.RED : PlayerInt.PlayerColor.BLUE;
  }

  @Override
  public List<Card> getCurrentPlayerHand() {
    return CardAdapter.adaptCards(model.getCurrentPlayerHand());
  }

  @Override
  public int[] calculateScores() {
    return new int[] {
      model.calculateTotalScore(Color.RED),
      model.calculateTotalScore(Color.BLUE)
    };
  }

  @Override
  public Cell getCellAt(int row, int col) {
    try {
      return new CellAdapter(model.getCell(row, col));
    } catch (IllegalArgumentException e) {
      return null; // Out of bounds
    }
  }

  @Override
  public Cell.CellContent getCellContent(int row, int col) {
    try {
      cs3500.pawnsboard.ReadOnlyPawnsBoardCell cell = model.getCell(row, col);
      if (cell.hasCard()) {
        return Cell.CellContent.CARD;
      } else if (cell.getPawnCount() > 0) {
        return Cell.CellContent.PAWNS;
      } else {
        return Cell.CellContent.EMPTY;
      }
      } catch (IllegalArgumentException e) {
        return null; // Out of bounds
      }
  }

  @Override
  public int getPawnCount(int row, int col) {
    try {
      return model.getCell(row, col).getPawnCount();
    } catch (IllegalArgumentException e) {
      return 0; // Out of bounds
    }
  }

  @Override
  public Card getCellCard(int row, int col) {
    try {
      cs3500.pawnsboard.ReadOnlyPawnsBoardCell cell = model.getCell(row, col);
        if (cell.hasCard()) {
          return new CardAdapter(cell.getCard());
        }
       return null;
    } catch (IllegalArgumentException e) {
      return null; // Out of bounds
    }
  }

  @Override
  public PlayerInt.PlayerColor getCellOwner(int row, int col) {
    try {
      cs3500.pawnsboard.ReadOnlyPawnsBoardCell cell = model.getCell(row, col);
      Color owner = cell.getOwner();
        if (owner == null) {
          return null;
        }
      return (owner == Color.RED) ?
               PlayerInt.PlayerColor.RED : PlayerInt.PlayerColor.BLUE;
    } catch (IllegalArgumentException e) {
      return null; // Out of bounds
    }
  }

  @Override
  public Cell getCell(int row, int col) {
    try {
      return new CellAdapter(model.getCell(row, col));
    } catch (IllegalArgumentException e) {
      return null; // Out of bounds
    }
  }

  @Override
  public int getRowScore(int row, PlayerInt.PlayerColor color) {
    try {
      int[] rowScores = model.calculateRowScores(row);
      return (color == PlayerInt.PlayerColor.RED) ? rowScores[0] : rowScores[1];
    } catch (IllegalArgumentException e) {
      return 0; // Out of bounds
    }
  }

  @Override
  public void applyMove(Move move) {
    // This is a read-only model, but we'll need this in the full adapter
    throw new UnsupportedOperationException("Cannot modify read-only model");
  }

  @Override
  public PlayerInt getRedPlayer() {
    return new PlayerIntAdapter(Color.RED);
  }

  @Override
  public PlayerInt getBluePlayer() {
    return new PlayerIntAdapter(Color.BLUE);
  }
}
