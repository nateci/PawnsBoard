package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A strategy that aims to maximize control of the board.
 * This strategy selects moves that result in the highest estimated number
 * of cells being controlled after playing a card. It evaluates all possible
 * valid moves in the current game state and returns the list of best-scoring moves.
 */
public class ControlTheBoardStrategy implements Strategy {

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    List<Move> bestMoves = new ArrayList<>();
    int maxControlled = -1;

    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(playerColor);

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int row = 0; row < model.getBoardRows(); row++) {
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (!model.isValidMove(cardIndex, row, col)) {
            continue;
          }

          int owned = estimateOwnedCellsAfterMove(model,
                  playerColor, hand.get(cardIndex), row, col);

          if (owned > maxControlled) {
            bestMoves.clear();
            bestMoves.add(new Move(row, col, cardIndex));
            maxControlled = owned;
          } else if (owned == maxControlled) {
            bestMoves.add(new Move(row, col, cardIndex));
          }
        }
      }
    }

    return bestMoves;
  }

  /**
   * Estimates the number of cells that would be controlled by the player after placing
   * the specified card at the given row and column.
   * This uses the card's influence grid and assumes that each 'X' in the grid represents
   * a cell that would be affected by the card. A cell is considered "gained" if it is
   * within bounds and not already owned by the player.
   *
   * @param model the current read-only board model
   * @param color the player's color
   * @param card  the card being placed
   * @param row   the target row to place the card
   * @param col   the target column to place the card
   * @return the number of additional cells that would be influenced (i.e. gained)
   */
  private int estimateOwnedCellsAfterMove(ReadOnlyPawnsBoardModel model, Color color,
                                          ReadOnlyPawnsBoardCard card, int row, int col) {
    int controlled = 0;
    char[][] influence = card.getInfluenceGrid();
    int gridSize = influence.length;
    int center = gridSize / 2;

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        if (influence[i][j] == 'X') {
          int targetRow = row + (i - center);
          int targetCol = col + (j - center);

          if (targetRow >= 0 && targetRow < model.getBoardRows()
                  && targetCol >= 0 && targetCol < model.getBoardCols()) {

            Color cellOwner = model.getCell(targetRow, targetCol).getOwner();

            // Count the cell if it's unowned or owned by the opponent
            if (cellOwner == null || !cellOwner.equals(color)) {
              controlled++;
            }
          }
        }
      }
    }

    return controlled;
  }
}
