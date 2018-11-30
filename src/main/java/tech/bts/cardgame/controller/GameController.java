package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bts.cardgame.model.Card;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/games")
    public long createGame() {

        Game game = gameService.createGame();
        return game.getId();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/games/{gameId}/join")
    public void joinGame(@RequestBody GameUser gameUser, @PathVariable("gameId") long gameId) {

        gameUser.setGameId(gameId);
        gameService.joinGame(gameUser);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/games/{gameId}/pick")
    public Card pickCard(@RequestBody GameUser gameUser, @PathVariable("gameId") long gameId) {

        gameUser.setGameId(gameId);
        return gameService.pickCard(gameUser);
    }
}
