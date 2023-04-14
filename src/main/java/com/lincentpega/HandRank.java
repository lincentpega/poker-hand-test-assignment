package com.lincentpega;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HandRank implements Comparable<HandRank> {
    private final HandRankType type;
    private final List<Card> kickers = new ArrayList<>();
    private final Card highestCard;
    private final List<Card> comboCards = new ArrayList<>();

    public HandRank(PokerHand hand) {
        if (HandRankUtils.hasRoyalFlush(hand)) {
            type = HandRankType.ROYAL_FLUSH;
        } else if (HandRankUtils.hasStraightFlush(hand)) {
            type = HandRankType.STRAIGHT_FLUSH;
        } else if (HandRankUtils.hasFourOfAKing(hand)) {
            type = HandRankType.FOUR_OF_A_KIND;
        } else if (HandRankUtils.hasFullHouse(hand)) {
            type = HandRankType.FULL_HOUSE;
        } else if (HandRankUtils.hasFlush(hand)) {
            type = HandRankType.FLUSH;
        } else if (HandRankUtils.hasStraight(hand)) {
            type = HandRankType.STRAIGHT;
        } else if (HandRankUtils.hasThreeOfAKing(hand)) {
            type = HandRankType.THREE_OF_A_KIND;
        } else if (HandRankUtils.hasTwoPairs(hand)) {
            type = HandRankType.TWO_PAIRS;
        } else if (HandRankUtils.hasPair(hand)) {
            type = HandRankType.PAIR;
        } else {
            type = HandRankType.HIGH_CARD;
        }

        comboCards.addAll(HandRankUtils.findComboCards(hand, type));
        kickers.addAll(hand.getCards().stream().filter(card -> !comboCards.contains(card)).toList());

        highestCard = comboCards.stream().max(Card::compareTo).orElseThrow(InvalidHandException::new);
    }

    @Override
    public int compareTo(@NotNull HandRank handRank) {
        if (this.type != handRank.type) {
            return this.type.getValue() - handRank.type.getValue();
        }
        if (this.highestCard.cardRank() != handRank.highestCard.cardRank()) {
            return this.highestCard.cardRank().getValue() - handRank.highestCard.cardRank().getValue();
        }
        // This is combinations which should be compared sequentially, everything else could be compared by
        // highest card of combination or by kickers
        if (type == HandRankType.FLUSH || type == HandRankType.TWO_PAIRS || type == HandRankType.FULL_HOUSE) {
            int compared = compareByCombo(handRank);
            if (compared != 0) {
                return compared;
            }
        }

        return compareByKickers(handRank);
    }

    private int compareByCombo(@NotNull HandRank handRank) {
        List<CardRank> descComboCardsRanks1 = comboCards.stream()
            .map(Card::cardRank)
            .sorted(Comparator.reverseOrder()).toList();
        List<CardRank> descComboCardsRanks2 = handRank.comboCards.stream()
            .map(Card::cardRank)
            .sorted(Comparator.reverseOrder()).toList();
        for (int i = 0; i < descComboCardsRanks1.size(); i++) {
            int compared = descComboCardsRanks1.get(i).getValue()
                - descComboCardsRanks2.get(i).getValue();
            if (compared != 0) {
                return compared;
            }
        }
        return 0;
    }

    private int compareByKickers(@NotNull HandRank handRank) {
        List<CardRank> kickersRanks1 = this.kickers.stream()
            .map(Card::cardRank)
            .sorted(Comparator.reverseOrder()).toList();
        List<CardRank> kickersRanks2 = handRank.kickers.stream()
            .map(Card::cardRank)
            .sorted(Comparator.reverseOrder()).toList();

        for (int i = 0; i < kickersRanks1.size(); i++) {
            int compared = kickersRanks1.get(i).getValue()
                - kickersRanks2.get(i).getValue();
            if (compared != 0) {
                return compared;
            }
        }
        return 0;
    }


    public HandRankType getType() {
        return type;
    }
}