package com.unicepta.minesweeper.service;

import static com.unicepta.minesweeper.api.dto.enums.TileValues.MINE;
import static com.unicepta.minesweeper.persistence.entity.BoardEntity.buildNewBoard;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.unicepta.minesweeper.api.dto.Board;
import com.unicepta.minesweeper.api.dto.CreateGameRequest;
import com.unicepta.minesweeper.api.dto.Tile;
import com.unicepta.minesweeper.persistence.BoardRepository;
import com.unicepta.minesweeper.persistence.entity.BoardEntity;
import com.unicepta.minesweeper.persistence.entity.TileEntity;

public class GameService {

  private final BoardRepository boardRepository = new BoardRepository();

  public Board createNewGame(CreateGameRequest request) {
    var entity = buildNewBoard(request.width, request.height, request.numberOfMines);
    boardRepository.create(entity);
    return new Board(entity);
  }

  public Board getBoardById(int boardId) {
    var entity = boardRepository.read(boardId);
    return new Board(entity);
  }

  public Board checkTile(Tile tile) {
    var boardEntity = boardRepository.read(tile.getBoardId());
    var board = new Board(boardEntity);
    var tileEntity = getTileEntityByTile(boardEntity, tile);
    if (tileEntity.getValue() == MINE) {
      tileEntity.setHidden(false);
      boardEntity.setGameLost(true);
      boardRepository.update(boardEntity);
      return new Board(boardEntity);
    }
    Set<Tile> visited = new HashSet<>();
    uncoverNeighbours(board, tile, visited);
    updateBoardEntity(visited, boardEntity);
    
    return new Board(boardEntity);
  }

  private void updateBoardEntity(Set<Tile> visited, BoardEntity entity) {
    for (Tile tile: visited) {
      Stream.of(entity.getTiles())
          .filter(tileEntity -> tileEntity.getxAxis() == tile.getxAxis() && tileEntity.getyAxis() == tile.getyAxis())
          .forEach(tileEntity -> tileEntity.setHidden(false));
    }
    boardRepository.update(entity);
  }

  private void uncoverNeighbours(Board board, Tile tile, Set<Tile> visited) {
    int neighbourMinesCount = getNeighbourMinesCount(board, tile);
    if (neighbourMinesCount > 0) {
      tile.setValue(Character.forDigit(neighbourMinesCount, 10));
      return;
    }
    for (Tile child: board.getTilesGraph().get(tile)) {
      if (!visited.contains(child)) {
        visited.add(child);
        uncoverNeighbours(board, child, visited);
      }
    }
  }

  private int getNeighbourMinesCount(Board board, Tile tile) {
    return (int) board.getTilesGraph().get(tile).stream()
        .filter(t -> t.getValue() == MINE)
        .count();
  }


  private TileEntity getTileEntityByTile(BoardEntity boardEntity, Tile tile) {
    int tileIndex = (tile.getxAxis() + 1) * tile.getyAxis();
    return boardEntity.getTiles()[tileIndex];
  }
}
