package com.xyzcorp.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {
    private final List<Card> cardList;

    public Hand(Card... cards) {
        this.cardList = Arrays.asList(cards);
    }

    public Hand() {
        this.cardList = new ArrayList<Card>();
    }

    public void drawFrom(Deck deck) {
        cardList.add(deck.draw());
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public int value() {
        int handValue = cardList
            .stream()
            .mapToInt(c -> c.rank().value())
            .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = cardList
            .stream()
            .anyMatch(card -> card.rank().value() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue < 11) {
            handValue += 10;
        }

        return handValue;
    }

    public Card topCard() {
        return cardList.get(0);
    }

    public boolean isBust() {
        return value() > 21;
    }

    boolean isUnderDealLimit() {
        return value() <= 16;
    }

    public boolean beats(Hand other) {
        return other.value() < value();
    }

    public boolean pushes(Hand other) {
        return other.value() == value();
    }
}