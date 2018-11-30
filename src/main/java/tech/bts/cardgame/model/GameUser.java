package tech.bts.cardgame.model;

public class GameUser {

    private long gameId;
    private String username;

    public GameUser() {
        // Needed to POST in Spring Boot
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }
}
