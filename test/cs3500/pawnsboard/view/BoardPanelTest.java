package cs3500.pawnsboard.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.MouseEvent;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.view.BoardPanel;
import cs3500.view.PawnsBoardViewController;

public class BoardPanelTest {

  @Test
  public void testInitialRenderAndMouseClick() {
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3, 3, java.awt.Color.RED);
    BoardPanel panel = new BoardPanel(model);

    // Simulate layout
    panel.setSize(300, 300);

    // Create a fake controller
    PawnsBoardViewController controller = new PawnsBoardViewController() {
      @Override
      public void handleCellClick(int row, int col) {
        assertTrue(row >= 0 && col >= 0);
      }

      @Override
      public void handleCardClick(int index) {}

      @Override
      public void confirmMove() {}

      @Override
      public void passTurn() {}

      @Override
      public void setGame(cs3500.pawnsboard.Game game) {}
    };
    panel.setController(controller);

    // Simulate click at center cell
    MouseEvent click = new MouseEvent(panel, 0, 0, 0, 150, 150, 1, false);
    panel.dispatchEvent(click);

    assertNotNull(panel.getPreferredSize());
  }
}
