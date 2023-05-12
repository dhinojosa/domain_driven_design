package com.xyzcorp.domain;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Source: Ted Young
 * <a href="https://moretestable.com/">MoreTestable.com</a>
 */
public class Game {

    private final Deck deck;

    private Hand playerHand = new Hand();

    private final Hand dealerHand = new Hand();

    private boolean isPlayerDone = Boolean.FALSE;

    public Game() {
        deck = new Deck();
    }

    public void playerHits() {
        playerHand.drawFrom(getDeck());
        isPlayerDone = playerHand.isBust();
    }

    public void playerStands() {
        isPlayerDone = Boolean.TRUE;
    }

    public boolean isPlayerDone() {
        return isPlayerDone;
    }

    public void initialDeal() {
        dealRound();
        dealRound();
    }

    private void dealRound() {
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void dealerPlays() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!getPlayerHand().isBust()) {
            while (getDealerHand().isUnderDealLimit()) {
                getDealerHand().drawFrom(deck);
            }
        }
    }

}
