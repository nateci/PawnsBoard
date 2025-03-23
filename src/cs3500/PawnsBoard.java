package cs3500;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;
import cs3500.pawnsboard.Game;
import cs3500.view.PawnsBoardViewImpl;
import cs3500.view.PawnsBoardViewControllerImpl;

public class PawnsBoard {

  public static void main(String[] args) {
    try {
      // Load decks
      List<Card> redDeck = DeckReader.loadDeck("docs/deck.config", Color.RED);
      List<Card> blueDeck = DeckReader.loadDeck("docs/deck.config", Color.BLUE);

      // Define board dimensions
      int rows = 3;
      int cols = 5;
      int totalCells = rows * cols;
      int totalCards = redDeck.size() + blueDeck.size();

      if (totalCards < totalCells) {
        System.out.println("Warning: Not enough cards to fill the board");
        System.out.println("Need at least " + totalCells + " cards, but only have " + totalCards);
      }

      Board board = new Board(rows, cols);
      Game game = getGame(redDeck, blueDeck, board);

      game.play();


    } catch (IOException e) {
      System.err.println("Error loading deck: " + e.getMessage());
    }
  }

  private static Game getGame(List<Card> redDeck, List<Card> blueDeck, Board board) {
    Player redPlayer = new Player(Color.RED, redDeck);
    Player bluePlayer = new Player(Color.BLUE, blueDeck);

    // Pass redPlayer initially — Game will update the model wrapper later if needed
    ReadOnlyBoardWrapper readOnlyModel = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);
    PawnsBoardViewImpl view = new PawnsBoardViewImpl(readOnlyModel);
    PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view);

    Game game = new Game(board, redPlayer, bluePlayer, view, controller, readOnlyModel);
    return game;
  }
}
