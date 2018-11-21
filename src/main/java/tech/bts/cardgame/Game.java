package tech.bts.cardgame;

import java.util.*;

public class Game {

    public enum State { OPEN, PLAYING }

    private final Deck deck;
    private State state;
    private Map<String, Player> playersByUsername;

    public Game(Deck deck) {
        this.deck = deck;
        this.state = State.OPEN;
        this.playersByUsername = new HashMap<>();
    }

    public State getState() {
        return state;
    }

    public void join(String username) {

        if (state != State.OPEN) {
            throw new JoiningNotAllowedException();
        }

        Player player = new Player(username);

        playersByUsername.put(username, player);

        if (playersByUsername.size() == 2) {
            state = State.PLAYING;
        }
    }

    public Set<String> getPlayerNames() {
        return playersByUsername.keySet();
    }

    public Card pickCard(String username) {

        if (!playersByUsername.containsKey(username)) {
            throw new PlayerNotInTheGameException();
        }

        if (state != State.PLAYING) {
            throw new NotPlayingException();
        }

        Player player = playersByUsername.get(username);

        Card pickedCard = player.getPickedCard();
        if (pickedCard != null) {
            throw new CannotPick2CardsInARowException();
        }

        Card newPickedCard = deck.pickCard();

        player.setPickedCard(newPickedCard);

        return newPickedCard;
    }

    public void discard(String username) {

        Player player = playersByUsername.get(username);

        Card pickedCard = player.getPickedCard();

        if (pickedCard == null) {
            throw new DidNotPickCardException();
        }

        int discards = player.getDiscardCount();
        if (discards == 2) {
            throw new TooManyDiscardsException();
        }

        player.setDiscardCount(discards + 1);

        player.setPickedCard(null);
    }






    /**
     * Returns a negative integer, zero, or a positive integer
     * as the first hand is less than, equal to,
     * or greater than the second hand.
     *
    private int compare(Hand hand1, Hand hand2) {

        int points1 = 0;
        int points2 = 0;

        Card total1 = hand1.calculate();
        Card total2 = hand2.calculate();

        if (total1.getMagic() > total2.getMagic()) {
            points1++;
        } else if (total1.getMagic() < total2.getMagic()) {
            points2++;
        }

        // TODO: do the same with strength and intelligence

        return points1 - points2;
    }
     */
}
