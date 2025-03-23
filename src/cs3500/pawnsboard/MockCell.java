package cs3500.pawnsboard;

import java.awt.Color;

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
