package com.unicepta.minesweeper.api.dto;

import static com.unicepta.minesweeper.api.dto.enums.TileValues.HIDDEN;

import java.util.Objects;

import com.unicepta.minesweeper.persistence.entity.TileEntity;

public class Tile {

  private char value;
  private int boardId;
  private int xAxis;
  private int yAxis;

  public Tile() {
    this.value = HIDDEN;
  }

  public Tile(TileEntity entity) {
    this.boardId = entity.getBoardId();
    this.value = entity.isHidden() ? HIDDEN : entity.getValue();
    this.boardId = entity.getBoardId();
    this.xAxis = entity.getxAxis();
    this.yAxis = entity.getyAxis();
  }

  public Tile(int xAxis, int yAxis, int boardId) {
    this.boardId = boardId;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
  }

  public char getValue() {
    return value;
  }

  public void setValue(char value) {
    this.value = value;
  }

  public int getBoardId() {
    return boardId;
  }

  public void setBoardId(int boardId) {
    this.boardId = boardId;
  }

  public void setxAxis(int xAxis) {
    this.xAxis = xAxis;
  }

  public void setyAxis(int yAxis) {
    this.yAxis = yAxis;
  }

  public int getxAxis() {
    return xAxis;
  }

  public int getyAxis() {
    return yAxis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tile)) return false;
    Tile tile = (Tile) o;
    return boardId == tile.boardId && xAxis == tile.xAxis && yAxis == tile.yAxis;
  }

  @Override
  public int hashCode() {
    return Objects.hash(boardId, xAxis, yAxis);
  }
}
