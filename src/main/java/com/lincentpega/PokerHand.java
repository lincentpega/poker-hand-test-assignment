package com.lincentpega;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PokerHand implements Comparable<PokerHand> {
    private final List<Card> cards = new ArrayList<>(5);
    private final HandRank rank;

    public PokerHand(@NotNull String hand) {
        String[] cardStrings = hand.split(" ");
        if (cardStrings.length != 5) {
            throw new InvalidHandException("Hand string should have 5 cards in format Rank:Suit");
        }
        for (String cardString : cardStrings) {
            if (cardString.length() != 2) {
                throw new InvalidHandException("Card should be in format Rank:Suit");
            }
            Card card = null;
            try {
                card = new Card(
                    CardRank.fromSymbol(cardString.substring(0, 1)),
                    CardSuit.fromSymbol(cardString.substring(1, 2))
                );
            } catch (IllegalArgumentException e) {
                throw new InvalidHandException("Illegal values in hand string", e);
            }
            this.cards.add(card);
        }
        this.rank = new HandRank(this);
    }

    @Override
    public int compareTo(@NotNull PokerHand pokerHand) {
        return -this.rank.compareTo(pokerHand.rank);
    }

    public HandRank getRank() {
        return rank;
    }

    public List<Card> getCards() {
        return cards;
    }
}
