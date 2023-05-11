package com.xyzcorp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {
    private final List<Card> cardList;

    public Hand(Card... cards) {
        this.cardList = Arrays.asList(cards);
    }

    public Hand() {
        this.cardList = new ArrayList<Card>();
    }

    void drawFrom(Deck deck) {
        cardList.add(deck.draw());
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

    Card topCard() {
        return cardList.get(0);
    }

    void display() {
        System.out.println(cardList.stream()
            .map(Card::display)
            .collect(Collectors.joining(
                ansi().cursorUp(6).cursorRight(1).toString())));
    }

    boolean isBust() {
        return value() > 21;
    }

    boolean isUnderDealLimit() {
        return value() <= 16;
    }

    boolean beats(Hand other) {
        return other.value() < value();
    }

    boolean pushes(Hand other) {
        return other.value() == value();
    }
}