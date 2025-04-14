package cs3500;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.adapter.ControllerAdapter;
import cs3500.adapter.GameModelAdapter;
import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;
import cs3500.pawnsboard.Game;
import cs3500.pawnsboard.Player;
import cs3500.controller.MachinePlayerController;
import cs3500.controller.PlayerController;
import cs3500.controller.PawnsBoardViewControllerImpl;
import cs3500.strategy.FillFirstStrategy;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.strategy.MinimaxStrategy;
import cs3500.strategy.ControlTheBoardStrategy;
import cs3500.strategy.ChainedStrategy;

import cs3500.view.PawnsBoardViewImpl;

/**
 * The main entry point for the Pawns Board game.
 * This class configures the game based on command-line arguments
 * and starts the game using appropriate player strategies or GUI views.
 * EX: java -jar pawnsboard.jar deckFile redPlayerType bluePlayerType
 */
public class PawnsBoard {

  /**
   * Launches the Pawns Board game.
   *
   * @param args the command-line arguments:
   *             [0] = deck file path,
   *             [1] = red player type,
   *             [2] = blue player type
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: java -jar pawnsboard.jar"
              + " <deckFile> <redPlayerType> <bluePlayerType>");
      System.err.println("Player types: human | fillfirst |"
              + " maximizerowscore | controlboard | minimax | chain");
      return;
    }

    String deckFile = args[0];
    String redType = args[1].toLowerCase();
    String blueType = args[2].toLowerCase();

    int deckSize = 15;
    int rows = 3;
    int cols = 5;
    int totalCells = rows * cols;

    // Validate deck file
    if (!new File(deckFile).exists()) {
      System.err.println("Deck config file not found: " + deckFile);
      return;
    }

    try {
      // Generate random decks for both players from the shared file
      List<Card> redDeck = DeckReader.getRandomDeckForPlayer(deckFile, Color.RED, deckSize);
      List<Card> blueDeck = DeckReader.getRandomDeckForPlayer(deckFile, Color.BLUE, deckSize);

      // Warn if there aren't enough cards to fill the board
      if (redDeck.size() + blueDeck.size() < totalCells) {
        System.out.printf("Warning: Not enough cards to fill the board (%d needed, %d available)%n",
                totalCells, redDeck.size() + blueDeck.size());
      }

      if (redDeck.isEmpty() || blueDeck.isEmpty()) {
        System.err.println("Cannot start game: one or both decks are empty.");
        return;
      }

      // Initialize game components
      Board board = new Board(rows, cols);
      int handSize = 5;

      Player redPlayer = new Player(Color.RED, redDeck, handSize);
      Player bluePlayer = new Player(Color.BLUE, blueDeck, handSize);

      ReadOnlyBoardWrapper modelWrapper = new ReadOnlyBoardWrapper(board,
              redPlayer, bluePlayer, redPlayer);

      // Create player controllers based on command-line input
      PlayerController redController = createController(redType, modelWrapper, Color.RED);
      PlayerController blueController = createController(blueType, modelWrapper, Color.BLUE);

      // Initialize game
      Game game = new Game(board, redPlayer, bluePlayer, redController, blueController,
              modelWrapper);

      if (redController instanceof ControllerAdapter) {
        ControllerAdapter adapter = (ControllerAdapter) redController;
        adapter.setGame(game);
        adapter.getModelAdapter().setGame(game);
      }

      if (blueController instanceof ControllerAdapter) {
        ControllerAdapter adapter = (ControllerAdapter) blueController;
        adapter.setGame(game);
        adapter.getModelAdapter().setGame(game);
      }



      // Provide game instance to controllers that need it
      if (redController instanceof MachinePlayerController) {
        ((MachinePlayerController) redController).setGame(game);
      } else if (redController instanceof PawnsBoardViewControllerImpl) {
        ((PawnsBoardViewControllerImpl) redController).setGame(game);
      }

      if (blueController instanceof MachinePlayerController) {
        ((MachinePlayerController) blueController).setGame(game);
      } else if (blueController instanceof PawnsBoardViewControllerImpl) {
        ((PawnsBoardViewControllerImpl) blueController).setGame(game);
      }

      // Start game
      game.play();

    } catch (IOException e) {
      System.err.println("Error loading deck: " + e.getMessage());
    }
  }

  /**
   * Creates a PlayerController based on the type provided.
   *
   * @param type          the player type string ("human", "fillfirst", etc.)
   * @param modelWrapper  the model wrapper for read-only access
   * @param color         the color of the player (RED or BLUE)
   * @return a PlayerController for the specified strategy or GUI
   * @throws IllegalArgumentException if the type is unknown
   */
  private static PlayerController createController(String type,
                                                   ReadOnlyBoardWrapper modelWrapper, Color color) {
    switch (type) {
      case "human":
        PawnsBoardViewImpl view = new PawnsBoardViewImpl(modelWrapper);
        PawnsBoardViewControllerImpl controller = new PawnsBoardViewControllerImpl(view, color);
        view.setController(controller);
        view.setLocation(color == Color.RED ? 100 : 800, 100);
        view.makeVisible();
        return controller;

      case "fillfirst":
        return new MachinePlayerController(null,
                modelWrapper, color, new FillFirstStrategy());

      case "maximizerowscore":
        return new MachinePlayerController(null,
                modelWrapper, color, new MaximizeRowScoreStrategy());

      case "controlboard":
        return new MachinePlayerController(null,
                modelWrapper, color, new ControlTheBoardStrategy());

      case "minimax":
        return new MachinePlayerController(null,
                modelWrapper, color, new MinimaxStrategy());

      case "chain":
        return new MachinePlayerController(null,
                modelWrapper, color,
                new ChainedStrategy(List.of(
                        new MaximizeRowScoreStrategy(),
                        new FillFirstStrategy()
                )));

      case "provider-human":
        GameModelAdapter providerModel = new GameModelAdapter(null, modelWrapper);
        cs3500.pawnsboard.provider.view.PawnsBoardView providerView =
                new cs3500.pawnsboard.provider.view.PawnsBoardView(providerModel);

        ControllerAdapter controllerAdapter =
                new ControllerAdapter(null, providerView, color, providerModel);

        providerModel.addModelStatusListener(controllerAdapter);

        return controllerAdapter;


      default:
        throw new IllegalArgumentException("Unknown player type: " + type);
    }
  }
}
