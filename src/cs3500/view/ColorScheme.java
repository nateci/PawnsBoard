package cs3500.view;

import java.awt.Color;

/**
 * Represents a color scheme for rendering game UI components.
 *
 * This interface allows for different styling strategies (e.g., default vs high contrast)
 * to be applied at runtime without changing rendering logic. Implementations define
 * how colors should be chosen for board cells, text, score markers, and highlights.
 */
public interface ColorScheme {

  /**
   * Returns the background color of a board cell.
   *
   * @param playerColor   the color of the current player (used for contextual styling)
   * @param isHighlighted whether the cell is currently selected/highlighted
   * @return the background color for the cell
   */
  Color getCellColor(Color playerColor, boolean isHighlighted);

  /**
   * Returns the appropriate text color for a given context.
   *
   * @param playerColor   the color of the current player
   * @param isHighlighted whether the text is drawn on a highlighted background
   * @return the color used for rendering text
   */
  Color getTextColor(Color playerColor, boolean isHighlighted);

  /**
   * Returns the color used to draw a player's pawn or score indicator.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return the color used to draw the player's pawns or score circles
   */
  Color getScoreCircleColor(Color playerColor);

  /**
   * Returns the color used for highlighting selected cells.
   *
   * @return the highlight color
   */
  Color getHighlightColor();
}
