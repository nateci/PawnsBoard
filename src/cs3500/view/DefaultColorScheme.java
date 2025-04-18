package cs3500.view;

import java.awt.Color;

public class DefaultColorScheme implements ColorScheme {

  @Override
  public Color getCellColor(Color playerColor, boolean isHighlighted) {
    return isHighlighted ? Color.CYAN : new Color(169, 169, 169); // Light gray
  }

  @Override
  public Color getTextColor(Color playerColor, boolean isHighlighted) {
    return Color.BLACK;
  }

  @Override
  public Color getScoreCircleColor(Color playerColor) {
    return playerColor.equals(Color.RED)
            ? new Color(255, 182, 193) // Pink
            : new Color(173, 216, 230); // Light blue
  }

  @Override
  public Color getHighlightColor() {
    return Color.CYAN;
  }
}
