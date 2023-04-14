package com.lincentpega;


public record Card(CardRank cardRank, CardSuit cardSuit) implements Comparable<Card> {

    @Override
    public int compareTo(Card card) {
        return this.cardRank.getValue() - card.cardRank().getValue();
    }
}
