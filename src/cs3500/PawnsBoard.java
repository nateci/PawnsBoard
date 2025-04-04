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
import cs3500.controller.PlayerController;
import cs3500.controller.PawnsBoardViewControllerImpl;
import cs3500.strategy.ChainedStrategy;
import cs3500.strategy.FillFirstStrategy;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.view.PawnsBoardViewImpl;

/**
 * The main entry point for the Pawns Board game.
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
      int handSize = 5;

      Player redPlayer = new Player(Color.RED, redDeck, handSize);
      Player bluePlayer = new Player(Color.BLUE, blueDeck, handSize);

      ReadOnlyBoardWrapper modelWrapper = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);

      // Config: Set true/false here to change game mode
      boolean redIsHuman = true;
      boolean blueIsHuman = false;

      PlayerController redController;
      PlayerController blueController;

      // Red player setup
      if (redIsHuman) {
        PawnsBoardViewImpl redView = new PawnsBoardViewImpl(modelWrapper);
        PawnsBoardViewControllerImpl redCtrl = new PawnsBoardViewControllerImpl(redView, Color.RED);
        redView.setController(redCtrl);
        redView.setLocation(100, 100);
        redView.makeVisible();
        redController = redCtrl;


      } else {
        ChainedStrategy redStrategy = new ChainedStrategy(List.of(
                new MaximizeRowScoreStrategy(),
                new FillFirstStrategy()
        ));
        redController = new MachinePlayerController(null, modelWrapper, Color.RED, redStrategy);
      }

      // Blue player setup
      if (blueIsHuman) {
        PawnsBoardViewImpl blueView = new PawnsBoardViewImpl(modelWrapper);
        PawnsBoardViewControllerImpl blueCtrl = new PawnsBoardViewControllerImpl(blueView, Color.BLUE);
        blueView.setController(blueCtrl);
        blueView.setLocation(800, 100);
        blueView.makeVisible();

        blueController = blueCtrl;
      } else {
        ChainedStrategy blueStrategy = new ChainedStrategy(List.of(
                new MaximizeRowScoreStrategy(),
                new FillFirstStrategy()
        ));
        blueController = new MachinePlayerController(null, modelWrapper, Color.BLUE, blueStrategy);
      }


      // Create and assign game to controllers
      Game game = new Game(board, redPlayer, bluePlayer, redController, blueController, modelWrapper);

      if (!redIsHuman) {
        ((MachinePlayerController) redController).setGame(game);
      } else {
        ((PawnsBoardViewControllerImpl) redController).setGame(game);
      }

      if (!blueIsHuman) {
        ((MachinePlayerController) blueController).setGame(game);
      } else {
        ((PawnsBoardViewControllerImpl) blueController).setGame(game);
      }

      // Start the game
      game.play();

    } catch (IOException e) {
      System.err.println("Error loading deck: " + e.getMessage());
    }
  }
}
