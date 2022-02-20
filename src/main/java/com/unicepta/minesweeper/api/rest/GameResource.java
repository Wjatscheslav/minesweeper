package com.unicepta.minesweeper.api.rest;

import com.unicepta.minesweeper.api.dto.Board;
import com.unicepta.minesweeper.api.dto.CreateGameRequest;
import com.unicepta.minesweeper.api.dto.Tile;
import com.unicepta.minesweeper.service.GameService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/games")
public class GameResource {

  private final GameService gameService = new GameService();

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Board createGame(CreateGameRequest request) {
    return gameService.createNewGame(request);
  }

  @GET
  @Path("/{id}")
  @Produces("application/json")
  public Board getBoard(@PathParam("id") int id) {
    return gameService.getBoardById(id);
  }

  @POST
  @Path("/check")
  @Consumes("application/json")
  @Produces("application/json")
  public Board checkTile(Tile tile) {
    return gameService.checkTile(tile);
  }
}
