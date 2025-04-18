package cs3500.view;

import java.awt.Color;

public class HighContrastColorScheme implements ColorScheme {

  @Override
  public Color getCellColor(Color playerColor, boolean isHighlighted) {
    return isHighlighted ? Color.YELLOW : Color.BLACK;
  }

  @Override
  public Color getTextColor(Color playerColor, boolean isHighlighted) {
    return isHighlighted ? Color.BLACK : Color.WHITE;
  }

  @Override
  public Color getScoreCircleColor(Color playerColor) {
    return playerColor.equals(Color.RED) ? Color.RED : Color.CYAN;
  }

  @Override
  public Color getHighlightColor() {
    return Color.YELLOW;
  }
}
