package com.unicepta.minesweeper.api.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicepta.minesweeper.persistence.entity.BoardEntity;
import com.unicepta.minesweeper.persistence.entity.TileEntity;

@JsonIgnoreProperties({"tilesGraph"})
public class Board {

  private int id;
  private final int width;
  private final int height;
  private transient final Map<Tile, LinkedList<Tile>> tilesGraph;
  private boolean gameLost = false;
  private final Tile[] tiles;

  public Board() {
    this.width = 1;
    this.height = 1;
    this.tilesGraph = new HashMap<>();
    tiles = new Tile[] { new Tile() };
  }

  public Board(BoardEntity entity) {
    this.id = entity.getId();
    this.height = entity.getHeight();
    this.width = entity.getWidth();
    this.tiles = Stream.of(entity.getTiles())
        .map(Tile::new)
        .toArray(tile -> new Tile[entity.getTiles().length]);
    this.tilesGraph = new HashMap<>();
    this.gameLost = entity.isGameLost();
    fillAdjacencyList(entity);
  }

  public void fillAdjacencyList(BoardEntity entity) {
    for (TileEntity tileEntity: entity.getTiles()) {
      Tile tile = new Tile(tileEntity);
      tilesGraph.put(tile, new LinkedList<>());
    }
    for (TileEntity tileEntity: entity.getTiles()) {
      Tile tile = new Tile(tileEntity);
      int xAxis = tile.getxAxis();
      int yAxis = tile.getyAxis();

      Tile leftUp = new Tile(xAxis - 1, yAxis - 1, entity.getId());
      if (tilesGraph.containsKey(leftUp)) {
        tilesGraph.get(leftUp).add(tile);
      }
      Tile up = new Tile(xAxis, yAxis - 1, entity.getId());
      if (tilesGraph.containsKey(up)) {
        tilesGraph.get(up).add(tile);
      }
      Tile rightUp = new Tile(xAxis + 1, yAxis - 1, entity.getId());
      if (tilesGraph.containsKey(rightUp)) {
        tilesGraph.get(rightUp).add(tile);
      }
      Tile right = new Tile(xAxis + 1, yAxis, entity.getId());
      if (tilesGraph.containsKey(right)) {
        tilesGraph.get(right).add(tile);
      }
      Tile rightDown = new Tile(xAxis + 1, yAxis + 1, entity.getId());
      if (tilesGraph.containsKey(rightDown)) {
        tilesGraph.get(rightDown).add(tile);
      }
      Tile down = new Tile(xAxis, yAxis + 1, entity.getId());
      if (tilesGraph.containsKey(down)) {
        tilesGraph.get(down).add(tile);
      }
      Tile leftDown = new Tile(xAxis - 1, yAxis + 1, entity.getId());
      if (tilesGraph.containsKey(leftDown)) {
        tilesGraph.get(leftDown).add(tile);
      }
      Tile left = new Tile(xAxis - 1, yAxis, entity.getId());
      if (tilesGraph.containsKey(left)) {
        tilesGraph.get(left).add(tile);
      }
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isGameLost() {
    return gameLost;
  }

  public void setGameLost(boolean gameLost) {
    this.gameLost = gameLost;
  }

  public Map<Tile, LinkedList<Tile>> getTilesGraph() {
    return tilesGraph;
  }

  public Tile[] getTiles() {
    return tiles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Board)) return false;
    Board board = (Board) o;
    return id == board.id && width == board.width && height == board.height && gameLost == board.gameLost && Objects.equals(tilesGraph, board.tilesGraph);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, width, height, tilesGraph, gameLost);
  }
}
