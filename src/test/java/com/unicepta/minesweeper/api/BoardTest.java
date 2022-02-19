package com.unicepta.minesweeper.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  public void createsBoard() {
    var b = Board.buildNewBoard(5, 6, 9);
    assertEquals(5, b.getWidth());
    assertEquals(6, b.getHeight());
    assertEquals(5 * 6, b.getTiles().length);
  }

}