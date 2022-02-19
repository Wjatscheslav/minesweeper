package com.unicepta.minesweeper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicepta.minesweeper.api.Board;
import com.unicepta.minesweeper.api.CreateGameRequest;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.HttpPost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IntegrationTest {

  private static ServerRunner runner;

  @BeforeAll
  static void setupServer() throws InterruptedException {
    runner = new ServerRunner();
    runner.start();
    runner.waitUntilStarted();
  }

  @AfterAll
  static void stopServer() throws Exception {
    runner.stopServer();
  }

  @Test
  void testCreateGame() throws Exception {
    var post = new HttpPost("http://localhost:8080/games");
    var payload = new ObjectMapper().writeValueAsString(new CreateGameRequest(9, 9, 5));
    post.setEntity(new StringEntity(payload));
    post.setHeader("Content-type", "application/json");

    try (var client = HttpClients.createDefault()) {
      var response = client.execute(post);
      assertEquals(200, response.getStatusLine().getStatusCode());

      var data = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      var board = new ObjectMapper().readValue(data, Board.class);
      assertEquals(9, board.getWidth());
    }
  }
}
