package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy that minimizes the number of valid moves the opponent can make
 * after the current player plays a card.
 */
public class MinimaxStrategy implements Strategy {

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    Color opponent = (playerColor == Color.RED) ? Color.BLUE : Color.RED;
    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(playerColor);
    List<Move> bestMoves = new ArrayList<>();
    int minOpponentMoves = Integer.MAX_VALUE;

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int row = 0; row < model.getBoardRows(); row++) {
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (!model.isValidMove(cardIndex, row, col)) {
            continue;
          }

          int opponentOptions = countOpponentMovesAfter(model, opponent);
          if (opponentOptions < minOpponentMoves) {
            bestMoves.clear();
            bestMoves.add(new Move(row, col, cardIndex));
            minOpponentMoves = opponentOptions;
          } else if (opponentOptions == minOpponentMoves) {
            bestMoves.add(new Move(row, col, cardIndex));
          }
        }
      }
    }

    return bestMoves;
  }

  /**
   * An estimate of the opponent's valid moves on the current board.
   */
  private int countOpponentMovesAfter(ReadOnlyPawnsBoardModel model, Color opponentColor) {
    int count = 0;
    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(opponentColor);
    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int row = 0; row < model.getBoardRows(); row++) {
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (model.isValidMove(cardIndex, row, col)) {
            count++;
          }
        }
      }
    }
    return count;
  }
}
