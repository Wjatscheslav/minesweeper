package com.unicepta.minesweeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ServerRunner extends Thread {
  private static final Logger log = LoggerFactory.getLogger(ServerRunner.class);

  private static MinesweeperServer server;

  ServerRunner() {
    server = new MinesweeperServer();
  }

  @Override
  public void run() {
    try {
      server.start();
    } catch (Exception e) {
      log.error("Could not start server", e);
    }
  }

  void waitUntilStarted() throws InterruptedException {
    try {
      server.waitUntilStarted();
      log.info("Started server.");
    } catch (InterruptedException e) {
      throw e;
    } catch (Exception e) {
      log.error("Could not start server", e);
      throw e;
    }
  }


  void stopServer() throws Exception {
    server.stop();
    server.join();
    server.destroy();
  }
}
