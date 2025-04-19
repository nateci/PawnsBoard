package cs3500.view;

import cs3500.controller.PawnsBoardViewController;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.Cell;
import cs3500.pawnsboard.ReadOnlyPawnsBoardModel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel that displays the game board grid, pawns, cards, and scores.
 * This panel is responsible for rendering the board state and handling
 * board-related user interactions.
 */
public class BoardPanel extends JPanel {

  private final ReadOnlyPawnsBoardModel model;
  private PawnsBoardViewController controller;

  // Tracking selected cell
  private int selectedRow = -1;
  private int selectedCol = -1;

  private ColorScheme colorScheme = new DefaultColorScheme();

  /**
   * Creates a new board panel with the specified model.
   *
   * @param model The read-only model to display
   */
  public BoardPanel(ReadOnlyPawnsBoardModel model) {
    this.model = model;
    setBackground(Color.WHITE);

    // Add mouse listener for cell selection
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
  }

  public void setColorScheme(ColorScheme scheme) {
    this.colorScheme = scheme;
    repaint();
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
    // Set preferred size based on board dimensions
    int width = 600;
    int height = 400;
    return new Dimension(width, height);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Enable anti-aliasing for smoother rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int cellSize = getCellSize();
    int boardWidth = cellSize * model.getBoardCols();
    int boardHeight = cellSize * model.getBoardRows();

    // Center the board
    int offsetX = (getWidth() - boardWidth) / 2;
    int offsetY = (getHeight() - boardHeight) / 2;

    // Draw the board grid and contents
    drawBoard(g2d, offsetX, offsetY, cellSize);

    // Draw row scores
    drawRowScores(g2d, offsetX, offsetY, cellSize);
  }

  /**
   * Calculates the cell size based on the current panel dimensions.
   *
   * @return The size of each cell in pixels
   */
  private int getCellSize() {
    int maxCellWidth = (getWidth() - 100) / model.getBoardCols();
    int maxCellHeight = (getHeight() - 80) / model.getBoardRows();
    return Math.min(maxCellWidth, maxCellHeight);
  }

  /**
   * Draws the board grid, pawns, and cards.
   *
   * @param g2d The graphics context
   * @param offsetX The x-offset for centering
   * @param offsetY The y-offset for centering
   * @param cellSize The size of each cell
   */
  private void drawBoard(Graphics2D g2d, int offsetX, int offsetY, int cellSize) {
    for (int row = 0; row < model.getBoardRows(); row++) {
      for (int col = 0; col < model.getBoardCols(); col++) {
        int x = offsetX + col * cellSize;
        int y = offsetY + row * cellSize;

        // Draw cell background
        boolean isHighlighted = (row == selectedRow && col == selectedCol);
        g2d.setColor(colorScheme.getCellColor(model.getCurrentPlayerColor(), isHighlighted));
        g2d.fillRect(x, y, cellSize, cellSize);

        // Draw cell border
        g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), false));
        g2d.drawRect(x, y, cellSize, cellSize);

        // Draw cell contents
        Cell cell = (Cell) model.getCell(row, col);
        drawCellContents(g2d, cell, x, y, cellSize);
      }
    }
  }

  /**
   * Draws the contents of a cell (pawns or card).
   *
   * @param g2d The graphics context
   * @param cell The cell to draw
   * @param x The x-coordinate of the cell
   * @param y The y-coordinate of the cell
   * @param cellSize The size of the cell
   */
  private void drawCellContents(Graphics2D g2d, Cell cell, int x, int y, int cellSize) {
    if (cell.hasCard()) {
      // Draw card
      Card card = cell.getCard();
      Color playerColor = card.getOwner() == Color.RED
              ? new Color(255, 200, 200) : new Color(200, 200, 255);
      g2d.setColor(playerColor);
      g2d.fillRect(x, y, cellSize, cellSize);

      // Draw base card value
      g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), false));
      g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 3));
      String valueText = String.valueOf(card.getValue());
      FontMetrics fm = g2d.getFontMetrics();
      int textX = x + (cellSize - fm.stringWidth(valueText)) / 2;
      int textY = y + (cellSize + fm.getAscent()) / 2;
      g2d.drawString(valueText, textX, textY);

      // Draw modifier overlay if present
      int mod = cell.getValueModifier();
      if (mod != 0) {
        g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 4));
        g2d.setColor(mod > 0 ? Color.GREEN.darker() : Color.MAGENTA);
        String modText = (mod > 0 ? "+" : "") + mod;
        FontMetrics modFm = g2d.getFontMetrics();
        int modX = x + cellSize - modFm.stringWidth(modText) - 4;
        int modY = y + cellSize - 4;
        g2d.drawString(modText, modX, modY);
      }

    } else if (cell.getPawnCount() > 0) {
      // Draw pawn
      Color pawnColor = cell.getOwner().equals(Color.RED)
              ? colorScheme.getScoreCircleColor(Color.RED)
              : colorScheme.getScoreCircleColor(Color.BLUE);
      g2d.setColor(pawnColor);
      int pawnSize = cellSize / 2;
      int pawnX = x + (cellSize - pawnSize) / 2;
      int pawnY = y + (cellSize - pawnSize) / 2;
      g2d.fillOval(pawnX, pawnY, pawnSize, pawnSize);

      // If more than one pawn, draw the count
      if (cell.getPawnCount() > 1) {
        g2d.setColor(colorScheme.getTextColor(model.getCurrentPlayerColor(), false));
        g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 4));
        String countText = String.valueOf(cell.getPawnCount());
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (cellSize - fm.stringWidth(countText)) / 2;
        int textY = y + (cellSize + fm.getAscent()) / 2;
        g2d.drawString(countText, textX, textY);
      }
      // === Modifier Overlay ===
      int mod = cell.getValueModifier();
      if (mod != 0) {
        g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 4));
        g2d.setColor(mod > 0 ? Color.GREEN.darker() : Color.MAGENTA);
        String modText = (mod > 0 ? "+" : "") + mod;
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + cellSize - fm.stringWidth(modText) - 4;
        int textY = y + cellSize - 4;
        g2d.drawString(modText, textX, textY);
      }
    }
  }

  /**
   * Draws the row scores for each player.
   *
   * @param g2d The graphics context
   * @param offsetX The x-offset for centering
   * @param offsetY The y-offset for centering
   * @param cellSize The size of each cell
   */
  private void drawRowScores(Graphics2D g2d, int offsetX, int offsetY, int cellSize) {
    g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 3));
    g2d.setColor(Color.BLACK); // Always black text (since it's over white background)

    for (int row = 0; row < model.getBoardRows(); row++) {
      int[] scores = model.calculateRowScores(row);
      int redScore = scores[0];
      int blueScore = scores[1];

      int rowY = offsetY + row * cellSize + cellSize / 2;

      // Red score (left)
      String redText = String.valueOf(redScore);
      int redX = offsetX - cellSize / 2;
      g2d.drawString(redText, redX, rowY);

      // Blue score (right)
      String blueText = String.valueOf(blueScore);
      int blueX = offsetX + model.getBoardCols() * cellSize + cellSize / 4;
      g2d.drawString(blueText, blueX, rowY);
    }
  }

  /**
   * Handles a mouse click on the board.
   *
   * @param e The mouse event
   */
  private void handleMouseClick(MouseEvent e) {
    int cellSize = getCellSize();
    int offsetX = (getWidth() - cellSize * model.getBoardCols()) / 2;
    int offsetY = (getHeight() - cellSize * model.getBoardRows()) / 2;

    // Calculate which cell was clicked
    int col = (e.getX() - offsetX) / cellSize;
    int row = (e.getY() - offsetY) / cellSize;

    // Check if click is within board boundaries
    if (row >= 0 && row < model.getBoardRows()
            && col >= 0 && col < model.getBoardCols()) {

      // Toggle selection if clicking the same cell
      if (selectedRow == row && selectedCol == col) {
        selectedRow = -1;
        selectedCol = -1;
      } else {
        selectedRow = row;
        selectedCol = col;
      }

      // Notify controller
      if (controller != null) {
        controller.handleCellClick(row, col);
      }

      repaint();
    }
  }
}
