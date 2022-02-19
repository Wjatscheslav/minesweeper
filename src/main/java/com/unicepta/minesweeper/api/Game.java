package com.unicepta.minesweeper.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/games")
public class Game {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Board createGame(CreateGameRequest request) {
    return Board.buildNewBoard(request.width, request.height, request.numberOfMines);
  }

  @GET
  @Path("/{id}")
  @Produces("application/json")
  public Board getBoard(@PathParam("id") int id) {
    return new Board();
  }
}
