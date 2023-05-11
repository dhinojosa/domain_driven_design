package com.xyzcorp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source: Ted Young
 * <a href="https://moretestable.com/">MoreTestable.com</a>
 */
public class HandValueAceTest {

  @Test
  public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
    Game game = new Game();
    List<Card> cards = List.of(new Card("whocares", Rank.ACE),
                               new Card("whocares", Rank.FIVE));

    assertThat(game.handValueOf(cards))
        .isEqualTo(11 + 5);
  }

  @Test
  public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt1() throws Exception {
    Game game = new Game();
    List<Card> cards = List.of(new Card("whocares", Rank.ACE),
                               new Card("whocares", Rank.EIGHT),
                               new Card("whocares", Rank.THREE));

    assertThat(game.handValueOf(cards))
        .isEqualTo(1 + 8 + 3);
  }

}
