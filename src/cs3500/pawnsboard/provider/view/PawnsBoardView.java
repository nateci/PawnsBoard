package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.provider.controller.PlayerActionListener;
import cs3500.pawnsboard.provider.model.PlayerInt;
import cs3500.pawnsboard.provider.model.ReadOnlyGameModel;
import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.provider.model.Cell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * A Swing-based view for the Pawns Board game.
 * Displays the board, the current player's hand, and row-scores.
 * Moves are possible:
 * - Click a card to select it.
 * - Click a board cell to select the target.
 * - Press 'c' to confirm and place the card.
 * - Press 'p' to trigger a pass move.
 * - Press 'q' to quit the game.
 */
public class PawnsBoardView extends JFrame {
  private static List<PawnsBoardView> activeViews = new ArrayList<PawnsBoardView>();
  private final ReadOnlyGameModel model;
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private final JLabel currentPlayerLabel;
  // List of registered player-action listeners.
  private List<PlayerActionListener> actionListeners = new ArrayList<PlayerActionListener>();
  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Constructs a new PawnsBoardView with the given game model.
   *
   * @param model the game model used by the view
   */
  public PawnsBoardView(ReadOnlyGameModel model) {
    super("Pawns Board");
    this.model = model;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    activeViews.add(this);

    // Key listener to notify action listeners for pass, confirm, and quit actions.
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (keyChar == 'p') {
          for (PlayerActionListener listener : actionListeners) {
            listener.passMove();
          }
        } else if (keyChar == 'c') {
          for (PlayerActionListener listener : actionListeners) {
            listener.confirmMove();
          }
        } else if (keyChar == 'q') {
          System.out.println("Quitting game.");
          shutdownGame();
        }
      }
    });
    this.setFocusable(true);
    this.requestFocusInWindow();

    currentPlayerLabel = new JLabel("Player: " + model.getCurrentPlayerColor());
    currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(currentPlayerLabel, BorderLayout.NORTH);

    boardPanel = new BoardPanel();
    this.add(boardPanel, BorderLayout.CENTER);

    handPanel = new HandPanel(model);
    this.add(handPanel, BorderLayout.SOUTH);

    this.pack();
    this.setVisible(true);
  }

  /**
   * Registers a new PlayerActionListener to receive player-action events.
   *
   * @param listener the listener to add.
   */
  public void addPlayerActionListener(PlayerActionListener listener) {
    actionListeners.add(listener);
  }

  /**
   * Returns the index of the currently selected card.
   *
   * @return the selected card index, or -1 if none.
   */
  public int getSelectedCardIndex() {
    return selectedCardIndex;
  }

  /**
   * Returns the row index of the currently selected board cell.
   *
   * @return the selected row, or -1 if none.
   */
  public int getSelectedRow() {
    return selectedRow;
  }

  /**
   * Returns the column index of the currently selected board cell.
   *
   * @return the selected column, or -1 if none.
   */
  public int getSelectedCol() {
    return selectedCol;
  }

  /**
   * Clears the current selections (both card and board cell) and repaints the view.
   */
  public void clearSelections() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
    handPanel.clearSelection();
    boardPanel.repaint();
    handPanel.repaint();
  }

  /**
   * Refreshes the view by updating the current player's label and
   * repainting the board and hand panels.
   */
  public void refresh() {
    currentPlayerLabel.setText("Player: " + model.getCurrentPlayerColor());
    boardPanel.repaint();
    handPanel.repaint();
  }

  /**
   * Displays a winners screen showing final scores and the winner.
   * The winners screen is shown in a separate JFrame.
   */
  public void showWinnersScreen() {
    int[] scores = model.calculateScores();
    String winner;
    if (scores[0] > scores[1]) {
      winner = "Red wins!";
    } else if (scores[1] > scores[0]) {
      winner = "Blue wins!";
    } else {
      winner = "The game is a tie!";
    }
    JFrame winnersFrame = new JFrame("Winners Screen");
    winnersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    winnersFrame.setLayout(new BorderLayout());
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    StringBuilder sb = new StringBuilder();
    sb.append("Final Scores:\n");
    sb.append("Red = ").append(scores[0]).append(", Blue = ")
            .append(scores[1]).append("\n");
    sb.append(winner).append("\n");
    sb.append("Press q to exit.");
    textArea.setText(sb.toString());
    winnersFrame.add(textArea, BorderLayout.CENTER);
    winnersFrame.setSize(new Dimension(400, 300));
    winnersFrame.setLocationRelativeTo(null);
    winnersFrame.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
          System.exit(0);
        }
      }
    });
    winnersFrame.setFocusable(true);
    winnersFrame.requestFocusInWindow();
    winnersFrame.setVisible(true);
  }

  /**
   * Shuts down the game by disposing all active views and showing the winners screen.
   */
  public static void shutdownGame() {
    if (!activeViews.isEmpty()) {
      activeViews.get(0).showWinnersScreen();
    }
    for (PawnsBoardView v : new ArrayList<PawnsBoardView>(activeViews)) {
      v.dispose();
    }
  }

  @Override
  public void dispose() {
    activeViews.remove(this);
    super.dispose();
  }

  // --- Inner class for drawing the board ---
  private class BoardPanel extends JPanel {
    private final int LEFT_MARGIN = 50;
    private final int RIGHT_MARGIN = 50;

    /**
     * Constructs a new BoardPanel.
     */
    public BoardPanel() {
      this.setPreferredSize(new Dimension(700, 400));
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          PawnsBoardView.this.requestFocusInWindow();
          handleBoardClick(e.getX(), e.getY());
        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      int totalWidth = getWidth();
      int totalHeight = getHeight();
      int rows = model.getBoardRows();
      int cols = model.getBoardCols();
      if (rows <= 0 || cols <= 0 || totalWidth <= (LEFT_MARGIN + RIGHT_MARGIN)) {
        return;
      }
      int usableWidth = totalWidth - LEFT_MARGIN - RIGHT_MARGIN;
      int cellWidth = usableWidth / cols;
      int cellHeight = totalHeight / rows;

      // Draw row scores on left and right margins.
      for (int r = 0; r < rows; r++) {
        int redScore = model.getRowScore(r, PlayerInt.PlayerColor.RED);
        String redScoreStr = "R: " + redScore;
        int textY = r * cellHeight + (cellHeight / 2);
        g.setColor(Color.BLACK);
        g.drawString(redScoreStr, 5, textY);
      }
      for (int r = 0; r < rows; r++) {
        int blueScore = model.getRowScore(r, PlayerInt.PlayerColor.BLUE);
        String blueScoreStr = "B: " + blueScore;
        int textY = r * cellHeight + (cellHeight / 2);
        int textX = totalWidth - RIGHT_MARGIN + 5;
        g.setColor(Color.BLACK);
        g.drawString(blueScoreStr, textX, textY);
      }

      // Draw board cells.
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          int x = LEFT_MARGIN + c * cellWidth;
          int y = r * cellHeight;
          PlayerInt.PlayerColor cellOwner = model.getCellOwner(r, c);
          Color bgColor;
          if (r == selectedRow && c == selectedCol) {
            bgColor = Color.CYAN;
          } else if (cellOwner == PlayerInt.PlayerColor.RED) {
            bgColor = new Color(255, 200, 200);
          } else if (cellOwner == PlayerInt.PlayerColor.BLUE) {
            bgColor = new Color(200, 200, 255);
          } else {
            bgColor = Color.LIGHT_GRAY;
          }
          g.setColor(bgColor);
          g.fillRect(x, y, cellWidth, cellHeight);
          g.setColor(Color.BLACK);
          g.drawRect(x, y, cellWidth, cellHeight);

          // Draw cell content (pawns or card).
          Cell.CellContent content = model.getCellContent(r, c);
          if (content == Cell.CellContent.PAWNS) {
            int pawnCount = model.getPawnCount(r, c);
            g.setColor((cellOwner == PlayerInt.PlayerColor.RED) ? Color.RED : Color.BLUE);
            drawCenteredString(g, String.valueOf(pawnCount), x, y, cellWidth, cellHeight);
          } else if (content == Cell.CellContent.CARD) {
            Card placedCard = model.getCellCard(r, c);
            if (placedCard != null) {
              g.setColor((cellOwner == PlayerInt.PlayerColor.RED) ? Color.RED : Color.BLUE);
              String text = placedCard.getName() + " | Val:" + placedCard.getValue()
                      + " | Cost:" + placedCard.getCost();
              drawCenteredString(g, text, x, y, cellWidth, cellHeight);
            }
          }
        }
      }
    }

    /**
     * Handles mouse clicks on the board panel by converting screen coordinates
     * to board cell coordinates and selecting the cell.
     *
     * @param mouseX the x-coordinate of the mouse click.
     * @param mouseY the y-coordinate of the mouse click.
     */
    private void handleBoardClick(int mouseX, int mouseY) {
      int rows = model.getBoardRows();
      int cols = model.getBoardCols();
      int totalWidth = getWidth();
      int usableWidth = totalWidth - LEFT_MARGIN - RIGHT_MARGIN;
      if (rows <= 0 || cols <= 0 || usableWidth <= 0) {
        return;
      }
      int cellWidth = usableWidth / cols;
      int cellHeight = getHeight() / rows;
      if (mouseX < LEFT_MARGIN) {
        System.out.println("Clicked in Red's score margin");
        return;
      }
      if (mouseX > totalWidth - RIGHT_MARGIN) {
        System.out.println("Clicked in Blue's score margin");
        return;
      }
      int boardX = mouseX - LEFT_MARGIN;
      int clickedCol = boardX / cellWidth;
      int clickedRow = mouseY / cellHeight;
      if (clickedRow >= 0 && clickedRow < model.getBoardRows()
              && clickedCol >= 0 && clickedCol < model.getBoardCols()) {
        selectedRow = clickedRow;
        selectedCol = clickedCol;
        System.out.println("Board cell selected: (" + clickedRow + ", " + clickedCol + ")");
        repaint();
      } else {
        System.out.println("Clicked outside the board");
      }
    }

    /**
     * Draws a string centered within a rectangle defined by x, y, width, and height.
     *
     * @param g      the Graphics context.
     * @param text   the text to draw.
     * @param x      the x-coordinate of the rectangle.
     * @param y      the y-coordinate of the rectangle.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     */
    private void drawCenteredString(Graphics g, String text, int x, int y,
                                    int width, int height) {
      FontMetrics fm = g.getFontMetrics();
      int textWidth = fm.stringWidth(text);
      int textHeight = fm.getAscent();
      int textX = x + (width - textWidth) / 2;
      int textY = y + (height + textHeight) / 2;
      g.drawString(text, textX, textY);
    }
  }

  /**
   * The HandPanel class displays the current player's hand of cards.
   * It shows card information and the card's influence grid.
   * Cards can be selected via mouse clicks.
   */
  private class HandPanel extends JPanel {
    private final ReadOnlyGameModel model;
    private int selectedCardIndex = -1;

    /**
     * Constructs a new HandPanel.
     *
     * @param model the immutable game model.
     */
    public HandPanel(ReadOnlyGameModel model) {
      this.model = model;
      this.setPreferredSize(new Dimension(600, 200));
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          PawnsBoardView.this.requestFocusInWindow();
          handleHandClick(e.getX(), e.getY());
        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      List<Card> hand = model.getCurrentPlayerHand();
      if (hand.isEmpty()) {
        g.drawString("(No cards in hand)", 10, 20);
        return;
      }
      int cardCount = hand.size();
      int panelWidth = getWidth();
      int panelHeight = getHeight();
      int cardWidth = Math.max(1, panelWidth / cardCount);
      PlayerInt.PlayerColor currentColor = model.getCurrentPlayerColor();
      Color cardColor = (currentColor == PlayerInt.PlayerColor.RED)
              ? new Color(255, 200, 200) : new Color(200, 200, 255);
      int infoHeight = panelHeight / 3;
      int gridTopOffset = infoHeight + 10;
      int lineSpacing = 15;
      for (int i = 0; i < cardCount; i++) {
        int x = i * cardWidth;
        int y = 0;
        Card card = hand.get(i);
        if (i == selectedCardIndex) {
          g.setColor(Color.CYAN);
          g.fillRect(x, y, cardWidth, panelHeight);
        } else {
          g.setColor(cardColor);
          g.fillRect(x, y, cardWidth, panelHeight);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cardWidth, panelHeight);
        g.setColor(Color.BLACK);
        String info = card.getName() + " | Cost: " + card.getCost()
                + " | Value: " + card.getValue();
        drawCenteredString(g, info, x, y, cardWidth, infoHeight, g.getFont());
        char[][] rawGrid = card.getInfluenceGrid();
        for (int row = 0; row < rawGrid.length; row++) {
          String rowString = new String(rawGrid[row]);
          int textX = x + 10;
          int textY = y + gridTopOffset + (row * lineSpacing);
          g.drawString(rowString, textX, textY);
        }
      }
    }

    /**
     * Handles mouse clicks on the hand panel by converting screen coordinates
     * to hand cell coordinates and selecting the card.
     *
     * @param mouseX the x-coordinate of the mouse click.
     * @param mouseY the y-coordinate of the mouse click.
     */
    private void handleHandClick(int mouseX, int mouseY) {
      List<Card> hand = model.getCurrentPlayerHand();
      if (hand.isEmpty()) {
        return;
      }
      int cardCount = hand.size();
      int panelWidth = getWidth();
      int cardWidth = Math.max(1, panelWidth / cardCount);
      int clickedIndex = mouseX / cardWidth;
      if (clickedIndex >= 0 && clickedIndex < cardCount) {
        if (selectedCardIndex == clickedIndex) {
          selectedCardIndex = -1;
        } else {
          selectedCardIndex = clickedIndex;
        }
        System.out.println("Hand card clicked: index " + clickedIndex + " ("
                + hand.get(clickedIndex).getName() + ")");
        PawnsBoardView.this.selectedCardIndex = selectedCardIndex;
        repaint();
      }
    }

    /**
     * Draws a string centered within a rectangle defined by x, y, width, height, and font.
     *
     * @param g     the Graphics context.
     * @param text  the text to draw.
     * @param x     the x-coordinate of the rectangle.
     * @param y     the y-coordinate of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param font  the font to use.
     */
    private void drawCenteredString(Graphics g, String text, int x, int y,
                                    int width, int height, Font font) {
      FontMetrics fm = g.getFontMetrics(font);
      int textWidth = fm.stringWidth(text);
      int textHeight = fm.getAscent();
      int textX = x + (width - textWidth) / 2;
      int textY = y + (height + textHeight) / 2;
      g.drawString(text, textX, textY);
    }

    /**
     * Draws a string centered within a rectangle defined by x, y, width, and height,
     * using the current Graphics font.
     *
     * @param g      the Graphics context.
     * @param text   the text to draw.
     * @param x      the x-coordinate of the rectangle.
     * @param y      the y-coordinate of the rectangle.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     */
    private void drawCenteredString(Graphics g, String text, int x, int y,
                                    int width, int height) {
      drawCenteredString(g, text, x, y, width, height, g.getFont());
    }

    /**
     * Clears the selection of the currently selected card.
     */
    public void clearSelection() {
      this.selectedCardIndex = -1;
      repaint();
    }
  }
}