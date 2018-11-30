package tech.bts.cardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.repository.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepo;

    @Autowired
    public GameService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    public void createGame() {

        Deck deck = new Deck();
        deck.generate();
        deck.shuffle();
        Game game = new Game(deck);

        gameRepo.create(game);
    }
}
