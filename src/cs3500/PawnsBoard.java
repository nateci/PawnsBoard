package cs3500;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    String sharedDeckFile = "docs/deck.config";
    int deckSize = 5;
    int rows = 3;
    int cols = 5;
    int totalCells = rows * cols;

    List<Card> redDeck, blueDeck;

    try {
      File file = new File(sharedDeckFile);
      if (file.exists()) {
        redDeck = DeckReader.getRandomDeckForPlayer(sharedDeckFile, Color.RED, deckSize);
        blueDeck = DeckReader.getRandomDeckForPlayer(sharedDeckFile, Color.BLUE, deckSize);
      } else {
        System.out.println("📦 No deck file found — using in-memory fallback deck");
        redDeck = generateDefaultDeck(Color.RED, deckSize);
        blueDeck = generateDefaultDeck(Color.BLUE, deckSize);
      }

      int totalCards = redDeck.size() + blueDeck.size();
      if (totalCards < totalCells) {
        System.out.printf("⚠️ Not enough cards to fill the board (%d required, %d available)%n", totalCells, totalCards);
      }

      if (redDeck.isEmpty() || blueDeck.isEmpty()) {
        System.err.println("❌ Cannot start game: one or both decks are empty.");
        return;
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

    ReadOnlyBoardWrapper readOnlyModel = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);
    PawnsBoardViewImpl view = new PawnsBoardViewImpl(readOnlyModel);
    PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view);

    return new Game(board, redPlayer, bluePlayer, view, controller, readOnlyModel);
  }

  private static List<Card> generateDefaultDeck(Color owner, int count) {
    String[] names = {"Guardian", "Archer", "Cleric", "Knight", "Mage", "Warden", "Rogue", "Lancer"};
    List<Card> deck = new java.util.ArrayList<>();

    for (int i = 0; i < count; i++) {
      String name = names[i % names.length];
      int cost = (i % 3) + 1;
      int value = (i % 4) + 1;
      char[][] grid = new char[5][5];
      for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
          grid[r][c] = (r == 2 && c == 2) ? 'C' : (Math.random() < 0.15 ? 'I' : 'X');
        }
      }
      deck.add(new Card(name, cost, value, grid, owner));
    }
    return deck;
  }
}
