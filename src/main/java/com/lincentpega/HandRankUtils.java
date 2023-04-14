package com.lincentpega;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HandRankUtils {
    public static List<Card> findComboCards(PokerHand hand, @NotNull HandRankType type) {
        switch (type) {
            case ROYAL_FLUSH, STRAIGHT_FLUSH, STRAIGHT, FULL_HOUSE, FLUSH -> {
                return hand.getCards();
            }
            case FOUR_OF_A_KIND -> {
                return findCardsOfAKind(hand, 4);
            }
            case THREE_OF_A_KIND -> {
                return findCardsOfAKind(hand, 3);
            }
            case TWO_PAIRS, PAIR -> {
                return findCardsOfAKind(hand, 2);
            }
            case HIGH_CARD -> {
                return List.of(hand.getCards().stream().max(Card::compareTo).orElseThrow(InvalidHandException::new));
            }
            default -> throw new InvalidHandException();
        }
    }

    public static List<Card> findCardsOfAKind(@NotNull PokerHand hand, int size) {
        return hand.getCards().stream()
            .collect(Collectors.groupingBy(Card::cardRank))
            .values().stream()
            .filter(cards -> cards.size() == size)
            .flatMap(Collection::stream)
            .toList();
    }

    public static boolean hasPair(@NotNull PokerHand hand) {
        return hasSameRank(hand, 2);
    }

    public static boolean hasTwoPairs(@NotNull PokerHand hand) {
        List<Card> cards = hand.getCards();
        return cards.stream()
            .collect(Collectors.groupingBy(Card::cardRank, Collectors.counting()))
            .values().stream()
            .filter(c -> c == 2)
            .count() == 2;
    }

    public static boolean hasThreeOfAKing(@NotNull PokerHand hand) {
        return hasSameRank(hand, 3);
    }

    public static boolean hasStraight(@NotNull PokerHand hand) {
        List<Card> cards = hand.getCards();
        int maxRankValue = cards.stream().mapToInt(card -> card.cardRank().getValue()).max().orElse(0);
        int minRankValue = cards.stream().mapToInt(card -> card.cardRank().getValue()).min().orElse(0);
        return maxRankValue - minRankValue == 4 &&
            cards.stream().mapToInt(card -> card.cardRank().getValue()).distinct().count() == 5;
    }

    public static boolean hasFlush(@NotNull PokerHand hand) {
        return hand.getCards().stream().map(Card::cardSuit).distinct().count() == 1;
    }

    public static boolean hasFullHouse(@NotNull PokerHand hand) {
        return hasSameRank(hand, 2) && hasSameRank(hand, 3);
    }

    public static boolean hasFourOfAKing(@NotNull PokerHand hand) {
        return hasSameRank(hand, 4);
    }

    public static boolean hasStraightFlush(@NotNull PokerHand hand) {
        return hasStraight(hand) && hasFlush(hand);
    }

    public static boolean hasRoyalFlush(@NotNull PokerHand hand) {
        if (!hasFlush(hand)) {
            return false;
        }
        var cardRanks = hand.getCards().stream().map(Card::cardRank).collect(Collectors.toSet());
        return cardRanks.containsAll(List.of(
            CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK, CardRank.TEN
        ));
    }

    public static boolean hasSameRank(@NotNull PokerHand hand, int count) {
        return hand.getCards().stream()
            .collect(Collectors.groupingBy(Card::cardRank, Collectors.counting()))
            .values().stream()
            .filter(c -> c == count).count() == 1;
    }
}
