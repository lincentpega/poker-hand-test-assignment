package com.lincentpega;

public enum CardSuit {
    CLUBS("C"),
    DIAMONDS("D"),
    HEARTS("H"),
    SPADES("S");

    private final String symbol;

    CardSuit(String symbol) {
        this.symbol = symbol;
    }

    public static CardSuit fromSymbol(String symbol) {
        for (CardSuit cardSuit : values()) {
            if (cardSuit.getSymbol().equals(symbol)) {
                return cardSuit;
            }
        }
        throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}
