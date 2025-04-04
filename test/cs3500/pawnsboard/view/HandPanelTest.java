package cs3500.pawnsboard.view;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.view.HandPanel;
import cs3500.controller.PawnsBoardViewController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for HandPanel using a manual mock model.
 */
public class HandPanelTest {

  private HandPanel panel;
  private MockReadOnlyPawnsBoardModel mockModel;
  private int clickedCardIndex = -1;

  /**
   * Before each for setting up methods for testing.
   */
  @BeforeEach
  public void setup() {
    mockModel = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    panel = new HandPanel(mockModel);

    // Simulate real panel size to allow layout math
    panel.setSize(600, 200);

    // Attach a test controller to capture card click events
    panel.setController(new PawnsBoardViewController() {
      @Override
      public void handleCardClick(int index) {
        clickedCardIndex = index;
      }

      @Override
      public void handleCellClick(int row, int col) {
        // dummy for testing
      }

      @Override
      public void confirmMove() {
        // dummy for testing
      }

      @Override
      public void passTurn() {
        // dummy for testing
      }

      @Override
      public void setGame(cs3500.pawnsboard.Game game) {
        // dummy for testing
      }
    });
  }

  @Test
  public void testInitialHandNotEmpty() {
    List<ReadOnlyPawnsBoardCard> hand = mockModel.getCurrentPlayerHand();
    assertFalse(hand.isEmpty(), "Mock hand should not be empty for testing.");
  }

  @Test
  public void testCardClickTriggersController() {
    // Simulate clicking on the first card
    int cardWidth = panel.getWidth() / mockModel.getCurrentPlayerHand().size();
    int x = cardWidth / 2;
    int y = 100;

    MouseEvent click = new MouseEvent(panel, MouseEvent.MOUSE_CLICKED,
            System.currentTimeMillis(), 0, x, y, 1, false);
    panel.dispatchEvent(click);

    assertEquals(0, clickedCardIndex,
            "Expected card index 0 to be selected on click.");
  }

  @Test
  public void testGetPreferredSizeHeight() {
    Dimension preferred = panel.getPreferredSize();
    assertTrue(preferred.height >= 160,
            "Preferred height should account for card layout.");
  }
}
