package cs3500.view;

import cs3500.controller.PawnsBoardViewController;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.ReadOnlyPawnsBoardCard;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.RenderingHints;

import java.util.List;

/**
 * Panel that displays the current player's hand.
 * This panel is responsible for rendering the player's hand and handling
 * card-related user interactions.
 */
public class HandPanel extends JPanel {
  private final ReadOnlyPawnsBoardModel model;
  private PawnsBoardViewController controller;

  // Constants for display sizing
  private static final int DEFAULT_CARD_WIDTH = 120;
  private static final int DEFAULT_CARD_HEIGHT = 160;
  private static final int INFLUENCE_GRID_SIZE = 5;
  private static final int INFLUENCE_CELL_SIZE = 20;

  // Tracking selected card
  private int selectedCardIndex = -1;
  private ColorScheme colorScheme = new DefaultColorScheme();

  /**
   * Creates a new hand panel with the specified model.
   *
   * @param model The read-only model to display
   */
  public HandPanel(ReadOnlyPawnsBoardModel model) {
    this.model = model;
    setBackground(new Color(230, 230, 230));

    // Add mouse listener for card selection
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
    setBackground(colorScheme.getCellColor(model.getCurrentPlayerColor(), false));
  }

  /**
   * Sets the controller for this panel.
   *
   * @param controller The controller to set
   */
  public void setController(PawnsBoardViewController controller) {
    this.controller = controller;
  }

  @Override
  public Dimension getPreferredSize() {
    // Set preferred size based on card dimensions
    int height = DEFAULT_CARD_HEIGHT + 50;
    return new Dimension(getWidth(), height);
  }

  public void setColorScheme(ColorScheme scheme) {
    this.colorScheme = scheme;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Enable anti-aliasing for smoother rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw player indicator
    drawPlayerIndicator(g2d);

    // Draw cards in hand
    drawPlayerHand(g2d);
  }

  /**
   * Draws the player indicator text.
   *
   * @param g2d The graphics context
   */
  private void drawPlayerIndicator(Graphics2D g2d) {
    g2d.setFont(new Font("Arial", Font.BOLD, 18));
    String playerText = "Player: " + (model.getCurrentPlayerColor() == Color.RED ? "RED" : "BLUE");
    g2d.drawString(playerText, 20, 25);
    g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), false));
  }

  /**
   * Draws the player's hand with all cards.
   *
   * @param g2d The graphics context
   */
  private void drawPlayerHand(Graphics2D g2d) {
    List<ReadOnlyPawnsBoardCard> hand = model.getCurrentPlayerHand();
    if (hand.isEmpty()) {
      return;
    }

    int cardWidth = getCardWidth();
    int cardHeight = getCardHeight();
    int totalWidth = cardWidth * hand.size();
    int startX = (getWidth() - totalWidth) / 2;
    int startY = 40;

    for (int i = 0; i < hand.size(); i++) {
      ReadOnlyPawnsBoardCard card = hand.get(i);
      int x = startX + i * cardWidth;

      // Draw card
      drawCard(g2d, (Card) card, x, startY, cardWidth, cardHeight, i == selectedCardIndex);
    }
  }

  /**
   * Draws a single card.
   *
   * @param g2d The graphics context
   * @param card The card to draw
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param width The card width
   * @param height The card height
   * @param isSelected Whether the card is selected
   */
  private void drawCard(Graphics2D g2d, Card card, int x, int y,
                        int width, int height, boolean isSelected) {
    // Draw card background
    if (isSelected) {
      g2d.setColor(Color.CYAN); // Highlight selected card
    } else {
      g2d.setColor(colorScheme.getCellColor(model.getCurrentPlayerColor(), isSelected));
    }
    g2d.fillRect(x, y, width, height);

    // Draw card border
    g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), isSelected));
    g2d.drawRect(x, y, width, height);

    // Draw card name
    g2d.setFont(new Font("Arial", Font.BOLD, 14));
    g2d.drawString(card.getName(), x + 5, y + 20);

    // Draw card cost and value
    g2d.setFont(new Font("Arial", Font.PLAIN, 12));
    g2d.drawString("Cost: " + card.getCost(), x + 5, y + 40);
    g2d.drawString("Value: " + card.getValue(), x + 5, y + 60);

    // Draw influence grid
    drawInfluenceGrid(g2d, x + 10, y + 70, card);
  }

  /**
   * Draws the influence grid for a card.
   *
   * @param g2d The graphics context
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param card The card containing the influence grid
   */
  private void drawInfluenceGrid(Graphics2D g2d, int x, int y, Card card) {
    char[][] grid = card.getInfluenceGrid();
    Color currentPlayerColor = model.getCurrentPlayerColor();

    // Use regular or mirrored grid based on player color
    char[][] displayGrid = currentPlayerColor == Color.RED
            ? grid : card.getMirroredInfluenceGrid();

    for (int r = 0; r < INFLUENCE_GRID_SIZE; r++) {
      for (int c = 0; c < INFLUENCE_GRID_SIZE; c++) {
        int cellX = x + c * INFLUENCE_CELL_SIZE;
        int cellY = y + r * INFLUENCE_CELL_SIZE;

        // Draw cell background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);

        // Draw cell border
        g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), false));
        g2d.drawRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);

        // Color the cell based on influence type
        char influenceType = displayGrid[r][c];

        switch (influenceType) {
          case 'I':
            g2d.setColor(Color.YELLOW); // Increase
            break;
          case 'C':
            g2d.setColor(Color.GRAY); // Center
            break;
          case 'U':
            g2d.setColor(Color.GREEN.darker()); // Upgrade
            break;
          case 'D':
            g2d.setColor(Color.MAGENTA); // Devalue
            break;
          default:
            g2d.setColor(Color.DARK_GRAY); // Default filler
            break;
        }
        g2d.fillRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);

      }
    }
  }

  /**
   * Calculates the card width based on the current panel dimensions and hand size.
   *
   * @return The width of each card
   */
  private int getCardWidth() {
    int handSize = Math.max(1, model.getCurrentPlayerHand().size());
    return Math.min(DEFAULT_CARD_WIDTH, getWidth() / handSize);
  }

  /**
   * Calculates the card height based on the current panel dimensions.
   *
   * @return The height of each card
   */
  private int getCardHeight() {
    return Math.min(DEFAULT_CARD_HEIGHT, getHeight() - 50);
  }

  /**
   * Handles a mouse click on the hand panel.
   *
   * @param e The mouse event
   */
  private void handleMouseClick(MouseEvent e) {
    List<ReadOnlyPawnsBoardCard> hand = model.getCurrentPlayerHand();
    if (hand.isEmpty()) {
      return;
    }

    int cardWidth = getCardWidth();
    int totalWidth = cardWidth * hand.size();
    int startX = (getWidth() - totalWidth) / 2;

    // Calculate which card was clicked
    int cardIndex = (e.getX() - startX) / cardWidth;

    // Check if click is within hand boundaries
    if (cardIndex >= 0 && cardIndex < hand.size()) {
      if (selectedCardIndex == cardIndex) {
        selectedCardIndex = -1;
      } else {
        selectedCardIndex = cardIndex;
      }

      // Notify controller
      if (controller != null) {
        controller.handleCardClick(cardIndex);
      }

      repaint();
    }
  }
}
