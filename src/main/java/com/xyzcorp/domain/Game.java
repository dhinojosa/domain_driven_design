package com.xyzcorp.domain;

import com.xyzcorp.adapter.in.ConsoleCard;
import com.xyzcorp.adapter.in.ConsoleGame;
import com.xyzcorp.adapter.in.ConsoleHand;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Source: Ted Young
 * <a href="https://moretestable.com/">MoreTestable.com</a>
 */
public class Game {

    private final Deck deck;

    private Hand playerHand = new Hand();

    private final Hand dealerHand = new Hand();

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRound();
        dealRound();
    }

    private void dealRound() {
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public void play() {
        // get Player's decision: hit until they stand, then they're done (or they go bust)
        boolean playerBusted = false;
        while (!playerBusted) {
            displayGameState();
            String playerChoice = ConsoleGame.inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                playerHand.drawFrom(deck);
                if (playerHand.isBust()) {
                    playerBusted = true;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }

        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (dealerHand.isUnderDealLimit()) {
                dealerHand.drawFrom(deck);
            }
        }

        displayFinalGameState();

        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (dealerHand.isBust()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (playerHand.beats(dealerHand)) {
            System.out.println("You beat the Dealer! 💵");
        } else if (playerHand.pushes(dealerHand)) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    private void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleCard.display(dealerHand.topCard())); // first card is Face Up

        // second card is the hole card, which is hidden
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.display(playerHand);
        System.out.println(" (" + playerHand.value() + ")");
    }

    private void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        ConsoleHand.display(dealerHand);
        System.out.println(" (" + dealerHand.value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.display(playerHand);
        System.out.println(" (" + playerHand.value() + ")");
    }
}
