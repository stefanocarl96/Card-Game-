package tech.bts.cardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public void generate() {

        for (int m = 1; m <= 8; m++) {
            for (int s = 1; s <= 9 - m; s++) {
                int i = 10 - m - s;
                this.add(new Card(m, s, i));
            }
        }
    }

    public void shuffle() {

        //System.out.println("Shuffling " + cards.size() + " cards");

        Random random = new Random();

        for (int i = 0; i < cards.size() - 1; i++) {

            // pick a random index and swap it with card at index 'i'
            int randomIndex = random.nextInt(this.cards.size()); // random index

            //System.out.println("Swapping cards at indexes " + i + " and " + randomIndex);

            Card card1 = cards.get(i);
            Card card2 = cards.get(randomIndex);

            cards.set(randomIndex, card1);
            cards.set(i, card2);
        }
    }

    public Card pickCard() {

        return cards.remove(cards.size() - 1);
    }

    public Hand deal(int size) {

        List<Card> handCards = new ArrayList<Card>();

        for (int i = 1; i <= size; i++) {
            handCards.add(pickCard());
        }

        Hand result = new Hand(handCards);

        return result;
    }
}
