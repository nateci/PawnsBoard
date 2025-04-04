package cs3500;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;
import cs3500.pawnsboard.Game;
import cs3500.pawnsboard.Player;
import cs3500.controller.MachinePlayerController;
import cs3500.pawnsboard.Player;
import cs3500.controller.PlayerController;
import cs3500.strategy.ChainedStrategy;
import cs3500.strategy.FillFirstStrategy;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.controller.PawnsBoardViewControllerImpl;
import cs3500.view.PawnsBoardViewImpl;

/**
 * The main entry point for the Pawns Board game.
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
    String sharedDeckFile = "deck.config";
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

      // Create players
      int handSize = 5;
      Player redPlayer = new Player(Color.RED, redDeck, handSize);
      Player bluePlayer = new Player(Color.BLUE, blueDeck, handSize);

      // Create model wrapper
      ReadOnlyBoardWrapper modelWrapper = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);

      // Set up the game
      Game game = new Game(board, redPlayer, bluePlayer, null, null, modelWrapper);

      // Create views for each player
      PawnsBoardViewImpl redView = new PawnsBoardViewImpl(modelWrapper);
      PawnsBoardViewImpl blueView = new PawnsBoardViewImpl(modelWrapper);

      // Create controllers for each player
      PlayerController redController;
      PlayerController blueController;

      // Decide which players are human or machine
      boolean redIsHuman = true;  // Change to false for machine vs machine
      boolean blueIsHuman = false; // Change to true for human vs human

      if (redIsHuman) {
        PawnsBoardViewControllerImpl redViewCtrl = new PawnsBoardViewControllerImpl(redView, Color.RED);
        redView.setController(redViewCtrl);
        redController = redViewCtrl;
        redViewCtrl.setGame(game);
      } else {
        // Create a machine player for Red with a strategy
        ChainedStrategy redStrategy = new ChainedStrategy(List.of(
                new MaximizeRowScoreStrategy(),
                new FillFirstStrategy()
        ));
        redController = new MachinePlayerController(game, modelWrapper, Color.RED, redStrategy);
      }

      if (blueIsHuman) {
        PawnsBoardViewControllerImpl blueViewCtrl = new PawnsBoardViewControllerImpl(blueView, Color.BLUE);
        blueView.setController(blueViewCtrl);
        blueController = blueViewCtrl;
        blueViewCtrl.setGame(game);
      } else {
        // Create a machine player for Blue with a strategy
        ChainedStrategy blueStrategy = new ChainedStrategy(List.of(
                new MaximizeRowScoreStrategy(),
                new FillFirstStrategy()
        ));
        blueController = new MachinePlayerController(game, modelWrapper, Color.BLUE, blueStrategy);
      }

      // Make the views visible
      if (redIsHuman) {
        redView.makeVisible();
      }
      if (blueIsHuman) {
        blueView.makeVisible();
      }

      // Start the game
      game.play();

    } catch (IOException e) {
      System.err.println("Error loading deck: " + e.getMessage());
    }
  }
}
