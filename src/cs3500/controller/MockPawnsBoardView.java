package cs3500.controller;

import cs3500.view.PawnsBoardView;

public class MockPawnsBoardView implements PawnsBoardView {

  public String lastStatus = "";
  public String secondLastStatus = "";
  public String lastTitle = "";
  public String lastError = "";

  @Override
  public void refresh() {
    // do nothing
  }

  @Override
  public void setController(PawnsBoardViewController controller) {
    // do nothing
  }

  @Override
  public void makeVisible() {
    // do nothing
  }

  @Override
  public void setStatus(String status) {
    secondLastStatus = lastStatus;
    lastStatus = status;
  }

  @Override
  public void setTitle(String title) {
    lastTitle = title;
  }
}
