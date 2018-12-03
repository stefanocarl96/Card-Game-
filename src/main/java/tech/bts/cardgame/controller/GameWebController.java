package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.service.GameService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/games")
public class GameWebController {

    private GameService gameService;

    @Autowired
    public GameWebController(GameService gameService) {
        this.gameService = gameService;
    }

    /** Returns the games (as HTML) */
    @RequestMapping(method = GET)
    public String getAllGames() {

        return buildGameList();
    }

    /** Returns details of the game with the given gameId (as HTML) */
    @RequestMapping(method = GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) {

        Game game = gameService.getGameById(gameId);

        return
                "<h1>Game " + game.getId() + "</h1>" +
                "<p>State: " + game.getState() + "</p>" +
                "<p>Players: " + game.getPlayerNames() + "</p>";
    }

    /** Creates a new game and returns the games (as HTML) */
    @RequestMapping(method = GET, path = "/create")
    public String createGame() {

        gameService.createGame();

        return buildGameList();
    }

    /** Builds the HTML to display the games */
    private String buildGameList() {

        String result = "<h1>List of games</h1>";

        result += "<p><a href=\"/games/create\">Create game</a></p>";

        for (Game game : gameService.getAllGames()) {

            result += "<p><a href=\"/games/" + game.getId() + "\">Game " + game.getId() + "</a> is " + game.getState() + "</p>";
        }

        return result;
    }
}
