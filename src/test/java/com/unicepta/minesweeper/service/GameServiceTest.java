package com.unicepta.minesweeper.service;

import static com.unicepta.minesweeper.api.dto.enums.TileValues.HIDDEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.unicepta.minesweeper.api.dto.Board;
import com.unicepta.minesweeper.api.dto.CreateGameRequest;

public class GameServiceTest {

  private final GameService gameService = new GameService();

  @Test
  public void shouldCreate3X3BoardWithAllHiddenTiles() {
    // given
    var width = 3;
    var height = 4;
    var minesCount = 3;
    CreateGameRequest input = new CreateGameRequest(width, height, minesCount);

    // when
    Board actual = gameService.createNewGame(input);

    // then
    assertEquals(height, actual.getHeight());
    assertEquals(width, actual.getWidth());
    assertEquals(width * height, getHiddenCount(actual));
    assertEquals(width * height, actual.getTiles().length);
  }

  @Test
  public void shouldReturnBoardByIdIfExists() {
    // given
    var width = 3;
    var height = 4;
    var minesCount = 3;
    CreateGameRequest input = new CreateGameRequest(width, height, minesCount);

    // when
    Board existing = gameService.createNewGame(input);
    Board actual = gameService.getBoardById(existing.getId());

    // then
    assertNotNull(actual);
    assertEquals(existing, actual);
  }

  @Test
  public void shouldThrowExceptionIfBoardWithTheIdDoesNotExist() {
    // given
    var width = 3;
    var height = 4;
    var minesCount = 3;
    CreateGameRequest input = new CreateGameRequest(width, height, minesCount);

    // when
    Board existing = gameService.createNewGame(input);

    // then
    var otherId = existing.getId() + 1;
    assertThrows(IllegalArgumentException.class, () -> gameService.getBoardById(otherId));
  }

  private long getHiddenCount(Board board) {
    return Stream.of(board.getTiles())
        .filter(tile -> tile.getValue() == HIDDEN)
        .count();
  }

}
