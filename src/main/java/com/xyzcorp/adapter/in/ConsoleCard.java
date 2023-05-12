package com.xyzcorp.adapter.in;

import com.xyzcorp.domain.Card;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleCard {
    public static void displayBackOfCard() {
        System.out.print(
            ansi()
                .cursorUp(7)
                .cursorRight(12)
                .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                .a("└─────────┘"));
    }

    public static String display(Card card) {
        String[] lines = new String[7];
        lines[0] = "┌─────────┐";
        lines[1] = String.format("│%s%s       │", card.rank(), card.rank().isTen() ? "" : " ");
        lines[2] = "│         │";
        lines[3] = String.format("│    %s    │", card.suit());
        lines[4] = "│         │";
        lines[5] = String.format("│       %s%s│", card.rank().isTen() ? "" : " ", card.rank());
        lines[6] = "└─────────┘";

        Ansi.Color cardColor = card.suit().isRed() ? Ansi.Color.RED : Ansi.Color.BLACK;
        return ansi()
            .fg(cardColor).toString()
            + String.join(ansi().cursorDown(1)
            .cursorLeft(11)
            .toString(), lines);
    }
}
