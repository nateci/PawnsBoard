package cs3500.view;

import cs3500.controller.PawnsBoardViewController;

/**
 * Interface for the Pawns Board game view frame.
 * Defines the methods required for the main view component.
 */
public interface PawnsBoardView {

  /**
   * Refreshes the view to reflect current model state.
   */
  void refresh();

  /**
   * Sets the controller for this view.
   *
   * @param controller The controller to set
   */
  void setController(PawnsBoardViewController controller);

  /**
   * Makes the view visible to the user.
   */
  void makeVisible();

  /**
   * Sets the status via a message.
   *
   * @param message sends a message to the user.
   */
  void setStatus(String message);

  /**
   * Sets the title/header.
   * @param title adds a title, or a status for the user to see.
   */
  void setTitle(String title);

}
