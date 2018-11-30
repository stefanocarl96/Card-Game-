package tech.bts.cardgame.model;

import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand(List<Card> cards) {

        this.cards = cards;
    }

    public Card calculate() {

        int magic = 0;
        int strength = 0;
        int intelligence = 0;

        for (Card card : cards) {
            magic += card.getMagic();
            strength += card.getStrength();
            intelligence += card.getIntelligence();
        }

        return new Card(magic, strength, intelligence);
    }
}
