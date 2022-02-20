package com.unicepta.minesweeper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicepta.minesweeper.api.dto.Board;
import com.unicepta.minesweeper.api.dto.CreateGameRequest;
import com.unicepta.minesweeper.api.dto.Tile;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.apache.http.client.methods.HttpPost;

import static com.unicepta.minesweeper.api.dto.enums.TileValues.HIDDEN;
import static com.unicepta.minesweeper.api.dto.enums.TileValues.MINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  public void shouldUnhideAllElementsIfThereAreNoMines() throws Exception {
    var post = new HttpPost("http://localhost:8080/games");
    var payload = new ObjectMapper().writeValueAsString(new CreateGameRequest(3, 3, 0));
    post.setEntity(new StringEntity(payload));
    post.setHeader("Content-type", "application/json");

    try (var client = HttpClients.createDefault()) {
      var response = client.execute(post);
      assertEquals(200, response.getStatusLine().getStatusCode());
      var data = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      var board = new ObjectMapper().readValue(data, Board.class);
      var checkRequest = new HttpPost("http://localhost:8080/games/check");
      Tile tile = new Tile(0, 0, board.getId());
      var checkPayload = new ObjectMapper().writeValueAsString(tile);
      checkRequest.setEntity(new StringEntity(checkPayload));
      checkRequest.setHeader("Content-type", "application/json");
      var checkResponse = client.execute(checkRequest);
      assertEquals(200, checkResponse.getStatusLine().getStatusCode());
      var checkData = EntityUtils.toString(checkResponse.getEntity(), StandardCharsets.UTF_8);
      var checkBoard = new ObjectMapper().readValue(checkData, Board.class);
      long hiddenCount = getCountByValue(checkBoard, HIDDEN);
      long minesCount = getCountByValue(checkBoard, MINE);
      assertEquals(0, hiddenCount);
      assertEquals(0, minesCount);
    }
  }

  @Test
  public void shouldLoseTheGameIfMineWasChecked() throws Exception {
    var post = new HttpPost("http://localhost:8080/games");
    var payload = new ObjectMapper().writeValueAsString(new CreateGameRequest(3, 3, 9));
    post.setEntity(new StringEntity(payload));
    post.setHeader("Content-type", "application/json");

    try (var client = HttpClients.createDefault()) {
      var response = client.execute(post);
      assertEquals(200, response.getStatusLine().getStatusCode());
      var data = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      var board = new ObjectMapper().readValue(data, Board.class);
      var checkRequest = new HttpPost("http://localhost:8080/games/check");
      Tile tile = new Tile(0, 0, board.getId());
      var checkPayload = new ObjectMapper().writeValueAsString(tile);
      checkRequest.setEntity(new StringEntity(checkPayload));
      checkRequest.setHeader("Content-type", "application/json");
      var checkResponse = client.execute(checkRequest);
      assertEquals(200, checkResponse.getStatusLine().getStatusCode());
      var checkData = EntityUtils.toString(checkResponse.getEntity(), StandardCharsets.UTF_8);
      var checkBoard = new ObjectMapper().readValue(checkData, Board.class);
      assertTrue(checkBoard.isGameLost());
    }
  }

  private long getCountByValue(Board board, char value) {
    return Stream.of(board.getTiles())
        .filter(tile -> tile.getValue() == value)
        .count();
  }

}
