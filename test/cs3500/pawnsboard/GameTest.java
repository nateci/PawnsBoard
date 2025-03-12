package cs3500.pawnsboard;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  @Test
  public void testGameEndsAfterConsecutivePasses() {
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, java.util.
            List.of(new Card("Security", 1, 2, new char[][]{
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'I', 'C', 'I', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'}
    }, Color.RED)));

    Player bluePlayer = new Player(Color.BLUE, java.util.
            List.of(new Card("Security", 1, 2, new char[][]{
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'I', 'C', 'I', 'X'},
            {'X', 'X', 'I', 'X', 'X'},
            {'X', 'X', 'I', 'X', 'X'}
    }, Color.BLUE)));

    Game game = new Game(board, redPlayer, bluePlayer);
    game.play(); //im so lazy can you plz make a reader that takes an input of "pass" so it ends
    game.play();
    game.determineWinner();
    assertTrue(game.isGameOver());
  }
}
