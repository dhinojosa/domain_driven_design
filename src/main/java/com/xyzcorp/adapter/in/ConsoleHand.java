package com.xyzcorp.adapter.in;

import com.xyzcorp.domain.Hand;

import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleHand {

    public static void display(Hand hand){
        System.out.println(hand.getCardList().stream()
            .map(card -> ConsoleCard.display(card))
            .collect(Collectors.joining(
                ansi().cursorUp(6).cursorRight(1).toString())));
    }
}
