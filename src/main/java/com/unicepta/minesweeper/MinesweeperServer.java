package com.unicepta.minesweeper;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.servlet.ServletContainer;

class MinesweeperServer {

  Server server;

  MinesweeperServer() {
    server = new Server(8080);
    var handler = new ServletContextHandler();
    handler.setContextPath("/");
    server.setHandler(handler);

    var holder = handler.addServlet(ServletContainer.class, "/*");
    holder.setInitOrder(1);
    holder.setInitParameter("jersey.config.server.provider.packages",
        "com.unicepta.minesweeper.api");
  }

  void start() throws Exception {
    server.start();
  }

  void join() throws InterruptedException {
    server.join();
  }

  void destroy() {
    server.destroy();
  }

  void waitUntilStarted() throws InterruptedException {
    for (int i=0; i < 60; i++) {
      Thread.sleep(1_000);
      if (server.isStarted()) return;
    }
    throw new RuntimeException("Server could not be started.");
  }

  public void stop() throws Exception {
    server.stop();
  }
}
