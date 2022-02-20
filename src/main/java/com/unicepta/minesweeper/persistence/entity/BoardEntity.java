package com.unicepta.minesweeper.persistence.entity;

import static com.unicepta.minesweeper.api.dto.enums.TileValues.EMPTY;
import static com.unicepta.minesweeper.api.dto.enums.TileValues.MINE;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class BoardEntity {

  public BoardEntity(int width, int height, TileEntity[] tiles) {
    this.width = width;
    this.height = height;
    this.tiles = tiles;
  }
  
  private int id;
  private final int width;
  private final int height;
  private final TileEntity[] tiles;
  private boolean gameLost = false;

  public static BoardEntity buildNewBoard(int width, int height, int numberOfMines) {
    Set<Integer> minesIndexes = getMinesIndexes(numberOfMines, width * height);
    var tiles = new TileEntity[width * height];
    for (int i = 0; i < width * height; i++) {
      var tileValue = minesIndexes.contains(i) ? MINE : EMPTY;
      var tile = new TileEntity(tileValue, i % width, i / width);
      tiles[i] = tile;
    }

    return new BoardEntity(width, height, tiles);
  }

  private static Set<Integer> getMinesIndexes(int numberOfMines, int tilesCount) {
    int numberBound = Math.min(numberOfMines, tilesCount);
    if (numberBound == 0) {
      numberBound++;
    }
    return ThreadLocalRandom.current().ints(0, numberBound).distinct().limit(numberOfMines)
        .boxed()
        .collect(Collectors.toSet());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public TileEntity[] getTiles() {
    return tiles;
  }

  public boolean isGameLost() {
    return gameLost;
  }

  public void setGameLost(boolean gameLost) {
    this.gameLost = gameLost;
  }
}
