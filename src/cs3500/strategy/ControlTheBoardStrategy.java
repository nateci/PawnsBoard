package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ControlTheBoardStrategy implements Strategy {

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    List<Move> bestMoves = new ArrayList<>();
    int maxControlled = -1;

    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(playerColor);

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int row = 0; row < model.getBoardRows(); row++) {
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (!model.isValidMove(cardIndex, row, col)) continue;

          // Estimate how many cells would be owned (mocked or real model needed)
          int owned = estimateOwnedCellsAfterMove(model, playerColor, hand.get(cardIndex), row, col);

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

  private int estimateOwnedCellsAfterMove(ReadOnlyPawnsBoardModel model, Color color,
                                          ReadOnlyPawnsBoardCard card, int row, int col) {
    // simplif estimate: base it on influence grid size or just +1 .
    return card.getValue(); // crude placeholder
  }
}
