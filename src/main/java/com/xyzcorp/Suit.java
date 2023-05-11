package com.xyzcorp;

public enum Suit {

    CLUBS("♣"),

    DIAMONDS("♦"),

    HEARTS("♥"),

    SPADES("♠");
    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    boolean isRed() {
        return "♥♦".contains(symbol);
    }
}
