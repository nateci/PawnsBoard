package cs3500.view;

import java.awt.*;
import cs3500.pawnsboard.Player;

public interface ColorScheme {
  Color getCellColor(Color playerColor, boolean isHighlighted);
  Color getTextColor(Color playerColor, boolean isHighlighted);
  Color getScoreCircleColor(Color playerColor);
  Color getHighlightColor();
}
