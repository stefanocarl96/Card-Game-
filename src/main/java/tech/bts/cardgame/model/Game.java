package tech.bts.cardgame.model;

import tech.bts.cardgame.exceptions.*;

import java.util.*;


public class Game {

    public enum State {OPEN, PLAYING, FINISHED}
    private long id;
    private final Deck deck;
    private State state;
    private Map<String, Player> playerMap;

    public Game(Deck deck) {

        this.deck = deck;
        this.state = State.OPEN;
        this.playerMap = new HashMap<>();
    }

    /**
     * When a user joins the game, a new player is created and added to a list of players
     * After two players joined the status of the game is changed to PLAYING and no other
     * users are allowed to enter the game
     */
    public Player join(String userName) {
        if (this.state != State.OPEN) {
            throw new JoiningNotAllowedException();
        }

        Player player = new Player(userName);
        this.playerMap.put(userName, player);
        if (playerMap.size() == 2) {
            this.state = State.PLAYING;
        }

        return player;
    }

    /**
     * When the status is PLAYING, only the users in the game can pick cards
     * If a user has more than three cards TooManyCardsInHandException is executed.
     * If a user tries to pick more than one card before deciding what to do with
     * the previous one, CannotPickTwoCardsInARowException is executed
     */
    public Card pickCard(String userName) {
        Player player = playerMap.get(userName);

        if (this.state != State.PLAYING) {
            throw new NotPlayingYetException();
        }

        if (!playerMap.containsKey(userName)) {
            throw new PlayerNotInTheGameException();
        }

        if (player.getHand() != null && player.getHand().size() >= 3) {
            throw new TooManyCardsInHandException();
        }

        if (player.getPickedCard() != null) {
            throw new CannotPickTwoCardsInARowException();
        }

        Card newPickedCard = deck.pickCard();
        player.setPickedCard(newPickedCard);
        return newPickedCard;

    }

    /**
     * If the user discards a card, it is removed from the pickedCardsByUserName map and
     * the counter of discardedCardsByUserName is augmented by 1.
     * After that, it executes autoComplete and it auto-fills the rest of the hand if the user has
     * already discarded two cards.
     * If a user tries to discard a card before picking it, PickingNeededBeforeActingException
     * is executed
     */
    public void discard(String userName) {
        Player player = playerMap.get(userName);

        if (player.getPickedCard() == null) {
            throw new PickingNeededBeforeActingException();
        }

        if (player.getDiscardedCards() >= 2) {
            throw new TooManyCardsInHandException();
        }

        player.setPickedCard(null);
        player.setDiscardedCards(player.getDiscardedCards() + 1);

        autoComplete(userName);
    }

    /**
     * When a player has discarded 2 cards, the hand of that player is completed automatically
     * by adding cards to it until the player has 3 cards. For example, if a player keeps 2 cards and discards 2 cards,
     * their hand is automatically completed with 1 card more
     */
    public void autoComplete(String userName) {
        Player player = playerMap.get(userName);

        if (player.getDiscardedCards() == 2) {
            if (player.getHand() != null) {
                Hand hand = player.getHand();
                while (hand.size() < 3) {
                    pickCard(userName);
                    keepCard(userName);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    pickCard(userName);
                    keepCard(userName);
                }
            }
        }
    }

    /**
     * If the user keeps the card, it is added to a hand unless it has already three cards,
     * that TooManyCardsInHandException is executed.
     * Once the two players have three cards in hand, compare() method is called to start the battle.
     * If a user tries to keep a card before picking it, PickingNeededBeforeActingException
     * is executed
     */
    public void keepCard(String userName) {
        Player player = playerMap.get(userName);

        if (player.getPickedCard() == null) {
            throw new PickingNeededBeforeActingException();
        }

        if (player.getHand() == null) {
            List<Card> cards = new ArrayList<>();
            cards.add(player.getPickedCard());
            player.setHand(new Hand(cards));
            player.setPickedCard(null);
        } else {
            if (player.getHand().size() < 3) {
                Hand hand = player.getHand();
                hand.add(player.getPickedCard());
                player.setPickedCard(null);
            } else {
                throw new TooManyCardsInHandException();
            }
        }

        int handsCompleted = 0;
        for (String user : getPlayersName()) {
            player = playerMap.get(user);
            if (player.getHand() != null && player.getHand().size() == 3) {
                handsCompleted++;
            }
        }

        if (handsCompleted == 2) {
            compare();
        }

    }

    /**
     * When the hands of the 2 players are complete (3 cards each)
     * the battle is performed and the winning player (if any) gets a point.
     * After that, the control fields (like pickedCard) are restarted
     * and if the deck has less than ten cards, the game state is set to FINISHED
     */
    private void compare() {
        Map<String, Card> totals = new HashMap<>();

        for (String userName : getPlayersName()) {
            Player player = playerMap.get(userName);
            Card total = player.getHand().calculate();
            totals.put(userName, total);
            player.setPickedCard(null);
            player.setHand(null);
            player.setDiscardedCards(0);
        }

        ArrayList<String> usernames = new ArrayList<>(totals.keySet());

        int result = 0;

        if (totals.get(usernames.get(0)).getMagic() > totals.get(usernames.get(1)).getMagic()) {
            result++;
        } else if (totals.get(usernames.get(0)).getMagic() < totals.get(usernames.get(1)).getMagic()) {
            result--;
        }

        if (totals.get(usernames.get(0)).getStrength() > totals.get(usernames.get(1)).getStrength()) {
            result++;
        } else if (totals.get(usernames.get(0)).getStrength() < totals.get(usernames.get(1)).getStrength()) {
            result--;
        }

        if (totals.get(usernames.get(0)).getIntelligence() > totals.get(usernames.get(1)).getIntelligence()) {
            result++;
        } else if (totals.get(usernames.get(0)).getIntelligence() < totals.get(usernames.get(1)).getIntelligence()) {
            result--;
        }

        if (result > 0) {
            playerMap.get(usernames.get(0)).setPoints(1);
        } else if (result < 0) {
            playerMap.get(usernames.get(1)).setPoints(1);
        } else {
            //System.out.println("No one wins...");
        }

        if (deck.size() < 10) {
            this.state = State.FINISHED;
        }
    }

    public Set<String> getPlayersName() {
        return playerMap.keySet();
    }

    public State getState() {
        return this.state;
    }

    public Map<String, Hand> getHands() {
        Map<String, Hand> hands = new HashMap<>();
        for (String user : getPlayersName()) {
            Player player = playerMap.get(user);
            hands.put(user, player.getHand());
        }
        return hands;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.playerMap.values().toString();
    }

    public void setState(State state) {
        this.state = state;
    }
}