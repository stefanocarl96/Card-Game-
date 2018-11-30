package tech.bts.cardgame.model;

public class Player {

    private String name;
    private int discardCount;
    private Card pickedCard;

    public Player(String name) {
        this.name = name;
        this.discardCount = 0;
        this.pickedCard = null;
    }

    public int getDiscardCount() {
        return discardCount;
    }

    public void setDiscardCount(int discardCount) {
        this.discardCount = discardCount;
    }

    public Card getPickedCard() {
        return pickedCard;
    }

    public void setPickedCard(Card pickedCard) {
        this.pickedCard = pickedCard;
    }
}
