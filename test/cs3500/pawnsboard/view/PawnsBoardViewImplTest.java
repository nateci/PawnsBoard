package cs3500.pawnsboard.view;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.controller.PawnsBoardViewController;
import cs3500.view.PawnsBoardViewImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Dimension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the PawnsBoardViewImpl class.
 */
public class PawnsBoardViewImplTest {

  private PawnsBoardViewImpl view;
  private boolean controllerCalled = false;

  @BeforeEach
  public void setup() {
    MockReadOnlyPawnsBoardModel mockModel = new MockReadOnlyPawnsBoardModel(3, 3, Color.RED);
    view = new PawnsBoardViewImpl(mockModel);
  }

  @Test
  public void testSetControllerAssignsToPanels() {
    // Assign dummy controller and ensure no crash
    PawnsBoardViewController dummyController = new PawnsBoardViewController() {
      @Override
      public void handleCellClick(int row, int col) {
        controllerCalled = true;
      }

      @Override
      public void handleCardClick(int index) {
        controllerCalled = true;
      }

      @Override
      public void confirmMove() {
        controllerCalled = true;
      }

      @Override
      public void passTurn() {
        controllerCalled = true;
      }

      @Override
      public void setGame(cs3500.pawnsboard.Game game) {
        // dummy holder
      }
    };

    assertDoesNotThrow(() -> view.setController(dummyController));
  }

  @Test
  public void testRefreshTriggersRepaint() {
    assertDoesNotThrow(() -> view.refresh());
  }

  @Test
  public void testMakeVisibleSetsVisibility() {
    assertFalse(view.isVisible(), "View should not be visible initially.");
    view.makeVisible();
    assertTrue(view.isVisible(), "View should be visible after makeVisible() is called.");
  }

  @Test
  public void testInitialWindowSizeIsSet() {
    Dimension size = view.getSize();
    assertTrue(size.width >= 600, "Expected width to be at least 600.");
    assertTrue(size.height >= 400, "Expected height to be at least 400.");
  }
}
