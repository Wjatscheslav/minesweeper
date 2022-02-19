package com.unicepta.minesweeper.api;

public class CreateGameRequest {
  public int width;
  public int height;
  public int numberOfMines;

  public CreateGameRequest() {
    this(1, 1, 1);
  }
  public CreateGameRequest(int width, int height, int numberOfMines) {
    this.width = width;
    this.height = height;
    this.numberOfMines = numberOfMines;
  }
}
