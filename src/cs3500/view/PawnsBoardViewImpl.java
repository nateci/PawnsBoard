package cs3500.view;

import cs3500.controller.PawnsBoardViewController;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import javax.swing.*;
import java.awt.*;

/**
 * Implementation of the PawnsBoardView interface using Swing.
 * Creates and manages the GUI for the Pawns Board game.
 */
public class PawnsBoardViewImpl extends JFrame implements PawnsBoardView {

  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private final JLabel statusLabel;
  private PawnsBoardViewController controller;

  /**
   * Creates a new view with the specified model.
   *
   * @param model The read-only model to display
   */
  public PawnsBoardViewImpl(ReadOnlyPawnsBoardModel model) {
    // Set up the frame
    setTitle("Pawns Board Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create the main components
    boardPanel = new BoardPanel(model);
    handPanel = new HandPanel(model);
    statusLabel = new JLabel("Welcome to Pawns Board!");
    statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    // Add components to the frame
    add(boardPanel, BorderLayout.CENTER);
    add(handPanel, BorderLayout.SOUTH);
    add(statusLabel, BorderLayout.NORTH);

    // Set up key listener for confirming moves or passing
    setupKeyBindings();

    // Set initial size
    setSize(800, 600);
  }

  @Override
  public void refresh() {
    boardPanel.repaint();
    handPanel.repaint();
    revalidate();
  }

  @Override
  public void setController(PawnsBoardViewController controller) {
    this.controller = controller;
    boardPanel.setController(controller);
    handPanel.setController(controller);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title); // call JFrame's method
  }

  public void setStatus(String message) {
    statusLabel.setText(message);
  }

  /**
   * Sets up key bindings for the frame.
   */
  private void setupKeyBindings() {
    setFocusable(true);
    JRootPane rootPane = getRootPane();
    InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = rootPane.getActionMap();

    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "confirmMove");
    actionMap.put("confirmMove", new AbstractAction() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if (controller != null) {
          controller.confirmMove();
        }
      }
    });

    inputMap.put(KeyStroke.getKeyStroke("SPACE"), "passTurn");
    actionMap.put("passTurn", new AbstractAction() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if (controller != null) {
          controller.passTurn();
        }
      }
    });
  }
}
