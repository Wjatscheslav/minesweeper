package com.unicepta.minesweeper.api;

public class Tile {
  public static char HIDDEN = '?';
  public static char MINE = '*';
  public static char EMPTY = '-';

  private char value;

  public Tile() {
    this.value = HIDDEN;
  }

  public char getValue() {
    return value;
  }

  public void setValue(char value) {
    this.value = value;
  }
}
