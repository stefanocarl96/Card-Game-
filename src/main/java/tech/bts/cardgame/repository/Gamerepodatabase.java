package tech.bts.cardgame.repository;

import org.springframework.stereotype.Repository;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameRepositoryDatabase {

    private Map<Long, Game> gameMap;
    private long nextId;
    private DataSource dataSource;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public GameRepositoryDatabase() throws SQLException{
        gameMap = new HashMap<>();
        nextId = 0;
        dataSource = DataSourceUtil.getDataSourceInPath();
        connection = dataSource.getConnection();
        statement = connection.createStatement();
    }

    public void createGame(Game game) {
        game.setId(nextId);
        gameMap.put(game.getId(), game);
        nextId++;
    }

    public Game gameById(long id) throws SQLException{
        resultSet = statement.executeQuery("SELECT * FROM games WHERE id =" + id);
        int gameId = resultSet.getInt("id");
        String state = resultSet.getString("state");
        String players = resultSet.getString("players");

        Deck deck = new Deck();
        Game game = new Game(deck);
        game.setId(gameId);

        String[] arr = players.split(",");
        for ( String player : arr) {
            game.join(player);
        }
        game.setState(Game.State.valueOf(state));

        return game;
    }

    public List<Game> getAll() throws SQLException{

        List<Game> games = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM games");


        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String state = resultSet.getString("state");
            String players = resultSet.getString("players");

            Deck deck = new Deck();
            Game game = new Game(deck);
            game.setId(id);

            String[] arr = players.split(",");
            for ( String player : arr) {
                game.join(player);
            }
            game.setState(Game.State.valueOf(state));
            games.add(game);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return games;
    }
}