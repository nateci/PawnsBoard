package cs3500.pawnsboard.controller;

import cs3500.ReadOnlyBoardWrapper;
import cs3500.controller.MockPawnsBoardView;
import cs3500.controller.PawnsBoardViewControllerImpl;
import cs3500.pawnsboard.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnsBoardViewControllerImplTest {

  private Board board;
  private Player redPlayer;
  private Player bluePlayer;
  private Game game;
  private MockPawnsBoardView view;
  private PawnsBoardViewControllerImpl controller;

  @BeforeEach
  void setup() {
    board = new Board(3, 5);
    Card sampleCard = new Card("Test", 1, 1, new char[][] {
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'I', 'C', 'I', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'}
    }, Color.RED);

    List<Card> deck = List.of(sampleCard, sampleCard, sampleCard);
    Player redPlayer = new Player(Color.RED, deck, 1);
    Player bluePlayer = new Player(Color.BLUE, deck, 1);
    ReadOnlyBoardWrapper modelWrapper = new ReadOnlyBoardWrapper(board, redPlayer, bluePlayer, redPlayer);

    view = new MockPawnsBoardView();
    controller = new PawnsBoardViewControllerImpl(view, Color.RED);
    view.setController(controller);
    game = new Game(board, redPlayer, bluePlayer, null, null, modelWrapper);
    controller.setGame(game);
  }

  @Test
  void testSelectCardAndCell() {
    controller.notifyPlayerTurn(Color.RED);
    controller.handleCardClick(0);
    controller.handleCellClick(1, 1);

    assertEquals("Selected cell at (1, 1).", view.lastStatus); // sequence
  }

  @Test
  void testConfirmMoveWithoutSelection() {
    controller.notifyPlayerTurn(Color.RED);
    controller.confirmMove();
    assertEquals("", view.lastError);
  }

  @Test
  void testInvalidTurn() {
    controller.notifyPlayerTurn(Color.BLUE); // not red’s turn
    controller.handleCardClick(0);
    assertEquals("It's not your turn.", view.lastStatus);
  }
}
