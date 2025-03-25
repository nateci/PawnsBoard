package cs3500;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.*;
import cs3500.view.PawnsBoardViewImpl;
import cs3500.view.PawnsBoardViewControllerImpl;

/**
 * The main entry point for the Pawns Board game.
 * <p>
 * This class initializes the game by loading decks from a configuration file,
 * setting up players and the board, and launching the game loop.
 */
public class PawnsBoard {

  /**
   * Launches the Pawns Board game.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    String sharedDeckFile = "docs/deck.config";
    int deckSize = 15;
    int rows = 3;
    int cols = 5;
    int totalCells = rows * cols;

    if (!new File(sharedDeckFile).exists()) {
      System.err.println("Deck config file not found: " + sharedDeckFile);
      return;
    }

    try {
      List<Card> redDeck = DeckReader.getRandomDeckForPlayer(sharedDeckFile, Color.RED, deckSize);
      List<Card> blueDeck = DeckReader.getRandomDeckForPlayer(sharedDeckFile, Color.BLUE, deckSize);

      int totalCards = redDeck.size() + blueDeck.size();

      if (totalCards < totalCells) {
        System.out.println("Warning: Not enough cards to fill the board.");
        System.out.printf("Required: %d cards, but only have: %d%n", totalCells, totalCards);
        System.out.println("The game will still run, but may end early.");
      }

      if (redDeck.isEmpty() || blueDeck.isEmpty()) {
        System.err.println("Cannot start game: one or both decks are empty.");
        return;
      }

      Board board = new Board(rows, cols);
      Game game = getGame(redDeck, blueDeck, board);
      game.play();

    } catch (IOException e) {
      System.err.println("Error loading deck: " + e.getMessage());
    }
  }

  /**
   * Constructs a new {@link Game} instance using the given decks and board.
   *
   * @param redDeck   the deck for the red player
   * @param blueDeck  the deck for the blue player
   * @param board     the game board
   * @return a fully initialized {@link Game} ready to be played
   */
  private static Game getGame(List<Card> redDeck, List<Card> blueDeck, Board board) {
    int handSize = 5;
    Player redPlayer = new Player(Color.RED, redDeck, handSize);
    Player bluePlayer = new Player(Color.BLUE, blueDeck, handSize);

    ReadOnlyBoardWrapper readOnlyModel = new ReadOnlyBoardWrapper(board, redPlayer,
            bluePlayer, redPlayer);
    PawnsBoardViewImpl view = new PawnsBoardViewImpl(readOnlyModel);
    PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view);

    return new Game(board, redPlayer, bluePlayer, view, controller, readOnlyModel);
  }
}
