package com.unicepta.minesweeper.persistence.entity;

public class TileEntity {

  private final char value;
  private int boardId;
  private final int xAxis;
  private final int yAxis;
  private boolean isHidden;

  public TileEntity(char value, int xAxis, int yAxis) {
    this.value = value;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.isHidden = true;
  }

  public char getValue() {
    return value;
  }

  public boolean isHidden() {
    return isHidden;
  }

  public void setHidden(boolean hidden) {
    isHidden = hidden;
  }

  public int getBoardId() {
    return boardId;
  }

  public void setBoardId(int boardId) {
    this.boardId = boardId;
  }

  public int getxAxis() {
    return xAxis;
  }

  public int getyAxis() {
    return yAxis;
  }
}
