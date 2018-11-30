package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.bts.cardgame.model.JoinGame;
import tech.bts.cardgame.service.GameService;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/games")
    public void createGame() {

        gameService.createGame();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/games")
    public void joinGame(@RequestBody JoinGame joinGame) {

        gameService.joinGame(joinGame);
    }
}
