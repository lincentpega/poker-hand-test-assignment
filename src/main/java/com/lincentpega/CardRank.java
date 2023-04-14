package com.lincentpega;

public enum CardRank {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("T", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String symbol;
    private final int value;

    public static CardRank fromSymbol(String symbol) {
        for (CardRank cardRank : values()) {
            if (cardRank.getSymbol().equals(symbol)) {
                return cardRank;
            }
        }
        throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }

    public static CardRank fromValue(int value) {
        for (var cardRank : values()) {
            if (cardRank.getValue() == value) {
                return cardRank;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    CardRank(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return this.value;
    }
}
