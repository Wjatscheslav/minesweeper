package com.unicepta.minesweeper.api;

public class Board {

  public Board() {
    this(1,1, new Tile[] { new Tile() });
  }

  public static Board buildNewBoard(int width, int height, int numberOfMines) {
    var tiles = new Tile[width * height];
    for (int i=0; i < width* height; i++) {
      tiles[i] = new Tile();
      tiles[i].setValue(Tile.HIDDEN);
    }
    return new Board(width, height, tiles);
  }

  public Board(int width, int height, Tile[] tiles) {
    this.width = width;
    this.height = height;
    this.tiles = tiles;
  }

  private final int width;
  private final int height;
  private final Tile[] tiles;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Tile[] getTiles() {
    return tiles;
  }
}
