package com.unicepta.minesweeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    var server = new MinesweeperServer();
    try {
      server.start();
      server.join();
    } catch (Exception ex) {
      log.error("error", ex);
    } finally {
      server.destroy();
    }
  }
}