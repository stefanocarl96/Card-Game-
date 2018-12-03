package tech.bts.cardgame;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.model.GameUser;
import tech.bts.cardgame.service.GameService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner createDummyData(GameService gameService) {

        return args -> {

            Game game1 = gameService.createGame();
            Game game2 = gameService.createGame();
            Game game3 = gameService.createGame();

            gameService.joinGame(new GameUser(game1.getId(), "bart"));
            gameService.joinGame(new GameUser(game1.getId(), "lisa"));
            gameService.joinGame(new GameUser(game2.getId(), "homer"));
        };
    }
}