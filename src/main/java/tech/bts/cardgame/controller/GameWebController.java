package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        String result = "<h1>List of games</h1>\n";

        result += "<p><a href=\"/games/create\">Create game</a></p>\n";

        result += "<ol>\n";

        for (Game game : gameService.getAllGames()) {

            result += "<li><a href=\"/games/" + game.getId() + "\">Game " + game.getId() + "</a> is " + game.getState() + "</li>\n";
        }

        result += "</ol>\n";

        return result;
    }

    /** Returns details of the game with the given gameId (as HTML) */
    @RequestMapping(method = GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) {

        Game game = gameService.getGameById(gameId);

        String result =
                "<a href=\"/games\">Go back to game list</a>" +
                "<h1>Game " + game.getId() + "</h1>" +
                "<p>State: " + game.getState() + "</p>" +
                "<p>Players: " + game.getPlayerNames() + "</p>";

        if (game.getState() == Game.State.OPEN) {
            result += "<a href=\"/games/" + game.getId() + "/join\">Join game</a>";
        }

        return result;
    }

    @RequestMapping(method = GET, path = "/{gameId}/join")
    public void joinGame(HttpServletResponse response, @PathVariable long gameId) throws IOException {

        gameService.joinGame(new GameUser(gameId, "Ferran"));
        response.sendRedirect("/games/" + gameId);
    }

    /** Creates a new game and returns the games (as HTML) */
    @RequestMapping(method = GET, path = "/create")
    public void createGame(HttpServletResponse response) throws IOException {

        gameService.createGame();
        response.sendRedirect("/games");
    }

}
