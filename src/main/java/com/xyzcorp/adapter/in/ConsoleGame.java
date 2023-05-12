package com.xyzcorp.adapter.in;

import com.xyzcorp.domain.Game;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {

    public static String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void welcomeScreen() {
        System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
    }

    public static void resetScreen() {
        System.out.println(ansi().reset());
    }

    public static void main(String[] args) {
        Game game = new Game();

        welcomeScreen();

        game.initialDeal();
        play(game);

        resetScreen();
    }

    public static void play(Game game) {
        // get Player's decision: hit until they stand, then they're done (or they go bust)
        //boolean playerBusted = false;
        while (!game.isPlayerDone()) {
            displayGameState(game);
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                game.playerStands();
            }
            if (playerChoice.startsWith("h")) {
                game.playerHits();
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }

        game.dealerPlays();

        displayFinalGameState(game);

        displayGameResults(game);
    }

    public static void displayFinalGameState(Game game) {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        ConsoleHand.display(game.getDealerHand());
        System.out.println(" (" + game.getDealerHand().value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.display(game.getPlayerHand());
        System.out.println(" (" + game.getDealerHand().value() + ")");
    }

    public static void displayGameState(Game game) {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleCard.display(game.getDealerHand().topCard())); // first card is Face Up

        // second card is the hole card, which is hidden
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.display(game.getPlayerHand());
        System.out.println(" (" + game.getPlayerHand().value() + ")");
    }

    public static void displayGameResults(Game game) {
        if (game.getPlayerHand().isBust()) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (game.getDealerHand().isBust()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (game.getPlayerHand().beats(game.getDealerHand())) {
            System.out.println("You beat the Dealer! 💵");
        } else if (game.getPlayerHand().pushes(game.getDealerHand())) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }
}
