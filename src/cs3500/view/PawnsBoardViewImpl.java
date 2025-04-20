package cs3500.view;

import cs3500.controller.PawnsBoardViewController;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Implementation of the PawnsBoardView interface using Swing.
 * Creates and manages the GUI for the Pawns Board game.
 */
public class PawnsBoardViewImpl extends JFrame implements PawnsBoardView {

  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private final JLabel statusLabel;
  private PawnsBoardViewController controller;

  private boolean highContrastEnabled = false;


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
    handPanel.setColorScheme(new DefaultColorScheme());
    statusLabel = new JLabel("Welcome to Pawns Board!");
    statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    // Add components to the frame
    add(boardPanel, BorderLayout.CENTER);
    add(handPanel, BorderLayout.SOUTH);
    add(statusLabel, BorderLayout.NORTH);

    JPanel legendPanel = createLegendPanel();
    addContrastToggle(boardPanel, legendPanel);
    add(legendPanel, BorderLayout.EAST);

    // Set up key listener for confirming moves or passing
    setupKeyBindings();

    // Set initial size
    setSize(800, 600);
  }

  /**
   * Adds a toggle checkbox to enable or disable high contrast mode.
   * Updates both the board and hand panels with the appropriate ColorScheme.
   *
   * @param boardPanel The BoardPanel whose theme should be updated.
   * @param container  The panel to which the toggle checkbox should be added.
   */
  private void addContrastToggle(BoardPanel boardPanel, JPanel container) {
    JCheckBox toggle = new JCheckBox("High Contrast");

    // Handle toggle behavior
    toggle.addActionListener(e -> {
      highContrastEnabled = toggle.isSelected();

      // Choose the color scheme based on toggle state
      ColorScheme scheme = highContrastEnabled
              ? new HighContrastColorScheme()
              : new DefaultColorScheme();

      // Apply the scheme to both board and hand panels
      boardPanel.setColorScheme(scheme);
      handPanel.setColorScheme(scheme);
    });

    // Add the checkbox to the container (e.g. legend panel)
    container.add(toggle);
  }

  /**
   * Creates a legend panel that visually explains the meaning of different influence colors.
   * Includes visual boxes with labels for Upgrade (U), Devalue (D), Increase (I),
   * and Card Center (C).
   *
   * @return A JPanel containing the influence legend.
   */
  private JPanel createLegendPanel() {
    JPanel legend = new JPanel();
    legend.setLayout(new BoxLayout(legend, BoxLayout.X_AXIS));

    // Add labeled color boxes for each influence type
    legend.add(makeLegendItem(Color.GREEN.darker(), "Upgrade (U)"));
    legend.add(makeLegendItem(Color.MAGENTA, "Devalue (D)"));
    legend.add(makeLegendItem(Color.ORANGE, "Increase (I)"));
    legend.add(makeLegendItem(Color.GRAY, "Card Center (C)"));

    return legend;
  }

  /**
   * Creates an individual item in the legend with a colored box and description label.
   *
   * @param color The background color representing the influence type.
   * @param text  The label text describing the influence.
   * @return A JPanel with the visual legend item.
   */
  private JPanel makeLegendItem(Color color, String text) {
    JPanel item = new JPanel();
    item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));

    // Colored box representing the influence type
    JPanel colorBox = new JPanel();
    colorBox.setBackground(color);
    colorBox.setPreferredSize(new java.awt.Dimension(20, 20));

    item.add(colorBox);
    item.add(new JLabel(text));
    return item;
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
