package tech.bts.cardgame.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public class GameRepositoryJdbc {

    private JdbcTemplate jdbcTemplate;

    public GameRepositoryJdbc() throws SQLException{
        DataSource dataSource = DataSourceUtil.getDataSourceInPath();
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public void createGame(Game game) {
        jdbcTemplate.update("insert into games (state, players)" +
                " values ('" + game.getState() + "', NULL)");
    }

    public Game gameById(long id) {

        return jdbcTemplate.queryForObject("SELECT * FROM games WHERE id =" + id,
                (resultSet, rowNum) -> getGame(resultSet));
    }

    public List<Game> getAll() {

        return jdbcTemplate.query("SELECT * FROM games",
                (resultSet, rowNum) -> getGame(resultSet));


    }


    private Game getGame(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        String state = resultSet.getString("state");
        String players = resultSet.getString("players");

        Deck deck = new Deck();
        Game game = new Game(deck);
        game.setId(id);

        if (players != null) {
            String[] arr = players.split(",");
            for (String player : arr) {
                game.join(player);
            }
        }
        game.setState(Game.State.valueOf(state));
        return game;
    }

    public void update(Game game) {

        String players = "";

        for (String player : game.getPlayersName()) {
            if (players.isEmpty()) {
                players += player;
            } else {
                players += ", " + player;
            }
        }

        jdbcTemplate.update("UPDATE games SET state = '" + game.getState().toString() + "', players = '" + players + "' WHERE id = '" + game.getId()+ "'");
    }

    public void createOrUpdate(Game game) {
        Game requestedGame = jdbcTemplate.queryForObject("SELECT * FROM games WHERE id =" + game.getId(),
                (resultSet, rowNum) -> getGame(resultSet));

        if (requestedGame != null) {
            update(game);
        } else {
            createGame(game);
        }
    }
}