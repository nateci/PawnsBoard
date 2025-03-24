package cs3500.strategy;

import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy that tries to win a row by making the row-score ≥ opponent's.
 * Visits rows top to bottom, and returns all equal-quality moves.
 */
public class MaximizeRowScoreStrategy implements Strategy {

  @Override
  public List<Move> chooseMoves(ReadOnlyPawnsBoardModel model, Color playerColor) {
    Color opponentColor = (playerColor == Color.RED) ? Color.BLUE : Color.RED;
    List<ReadOnlyPawnsBoardCard> hand = model.getPlayerHand(playerColor);

    for (int row = 0; row < model.getBoardRows(); row++) {
      int[] rowScores = model.calculateRowScores(row);
      int playerScore = (playerColor == Color.RED) ? rowScores[0] : rowScores[1];
      int opponentScore = (playerColor == Color.RED) ? rowScores[1] : rowScores[0];

      // If player already wins this row, skip
      if (playerScore > opponentScore) continue;

      List<Move> viableMoves = new ArrayList<>();

      for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
        ReadOnlyPawnsBoardCard card = hand.get(cardIndex);
        for (int col = 0; col < model.getBoardCols(); col++) {
          if (!model.isValidMove(cardIndex, row, col)) continue;

          int simulatedScore = playerScore + card.getValue();
          if (simulatedScore >= opponentScore) {
            viableMoves.add(new Move(row, col, cardIndex));
          }
        }
      }

      if (!viableMoves.isEmpty()) {
        return viableMoves; // only consider the first row that has a valid improvement
      }
    }

    return new ArrayList<>(); // no valid move
  }
}
