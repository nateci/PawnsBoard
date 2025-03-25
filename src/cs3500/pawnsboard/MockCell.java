package cs3500.pawnsboard;

import java.awt.Color;

/**
 * A simple mock implementation of {@link ReadOnlyPawnsBoardCell}
 * used for testing or placeholder purposes.
 * This mock always reports a RED owner, 2 pawns, no card present,
 * and provides a basic textual view.
 */
public class MockCell implements ReadOnlyPawnsBoardCell {

  @Override
  public boolean hasCard() {
    return false;
  }

  @Override
  public ReadOnlyPawnsBoardCard getCard() {
    return null;
  }

  @Override
  public Color getOwner() {
    return Color.RED;
  }

  @Override
  public int getPawnCount() {
    return 2;
  }

  @Override
  public String toTextualView() {
    return "2";
  }
}
