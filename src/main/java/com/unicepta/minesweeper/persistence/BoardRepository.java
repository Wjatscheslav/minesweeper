package com.unicepta.minesweeper.persistence;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.unicepta.minesweeper.persistence.entity.BoardEntity;

public class BoardRepository {

  private static final Map<Integer, BoardEntity> BOARD_STORAGE = new ConcurrentHashMap<>();
  private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

  public void create(BoardEntity board) {
    var id = ID_GENERATOR.incrementAndGet();
    board.setId(id);
    Arrays.stream(board.getTiles()).forEach(tile -> tile.setBoardId(id));
    BOARD_STORAGE.put(id, board);
  }

  public BoardEntity read(Integer id) {
    var entity = BOARD_STORAGE.get(id);
    if (entity == null) {
      throw new IllegalArgumentException(String.format("Board with the given ID: %s does not exist", id));
    }
    return entity;
  }

  public BoardEntity update(BoardEntity board) {
    var existing = BOARD_STORAGE.get(board.getId());
    if (existing == null) {
      throw new IllegalArgumentException(String.format("Board with the given ID: %s does not exist", board.getId()));
    }
    return BOARD_STORAGE.put(board.getId(), board);
  }

  public BoardEntity delete(BoardEntity board) {
    return BOARD_STORAGE.remove(board.getId());
  }

}
