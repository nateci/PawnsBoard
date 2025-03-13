package cs3500;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;
import cs3500.pawnsboard.Game;

/**
 * The class that runs the pawns board game.
 */
public class PawnsBoard {

  /**
   * Allows you to actually run the Pawns Board game.
   */
  public static void main(String[] args) {
    try {
      List<Card> redDeck = DeckReader.loadDeck("docs/deck.config", Color.RED);
      List<Card> blueDeck = DeckReader.loadDeck("docs/deck.config", Color.BLUE);

      int rows = 3;
      int cols = 5;
      int totalCells = rows * cols;
      int totalCards = redDeck.size() + blueDeck.size();

      if (totalCards < totalCells) {
        System.out.println("Warning: Not enough cards to fill the board");
        System.out.println("Need at least " + totalCells + " cards, but only have " + totalCards);
      }

      Player redPlayer = new Player(Color.RED, redDeck);
      Player bluePlayer = new Player(Color.BLUE, blueDeck);
      Board board = new Board(rows, cols);

      Game game = new Game(board, redPlayer, bluePlayer);
      game.play();
    } catch (IOException e) {
      System.out.println("Error loading deck: " + e.getMessage());
    }
  }
}


