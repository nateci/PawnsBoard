package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy that picks the first valid move (card, row, col).
 */
public class FillFirstStrategy implements Strategy {

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(playerColor);
    List<Move> result = new ArrayList<>();

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int row = 0; row < model.getBoardRows(); row++) {
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (model.isValidMove(cardIndex, row, col)) {
            result.add(new Move(row, col, cardIndex));
            return result; // only the first valid move
          }
        }
      }
    }

    return result; // empty = no move (pass)
  }
}
