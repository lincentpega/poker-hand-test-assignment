package com.lincentpega;

import junit.framework.TestCase;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PokerHandTest extends TestCase {
    public void testConstructor() {
        // given
        var highCardString = "TC 4H 7D KC 2S";
        var onePairString = "KC KH 7D 2C 5S";
        var twoPairString = "KC KH 7D 7C 5S";
        var threeOfAKindString = "KC KH KD 7C 5S";
        var straightString = "3C 4H 5D 6C 7S";
        var flushString = "KC QC 9C 8C 2C";
        var fullHouseString = "KC KH KD 7C 7S";
        var fourOfAKindString = "6S 6D 6H 6C KS";
        var straightFlushString = "2S 3S 4S 5S 6S";
        var royalFlushString = "AH KH QH JH TH";

        // when
        var highCardHand = new PokerHand(highCardString);
        var onePairHand = new PokerHand(onePairString);
        var twoPairsHand = new PokerHand(twoPairString);
        var threeOfAKindHand = new PokerHand(threeOfAKindString);
        var straightHand = new PokerHand(straightString);
        var flushHand = new PokerHand(flushString);
        var fullHouseHand = new PokerHand(fullHouseString);
        var fourOfAKindHand = new PokerHand(fourOfAKindString);
        var straightFlushHand = new PokerHand(straightFlushString);
        var royalFlushHand = new PokerHand(royalFlushString);

        // then
        assertThat(highCardHand.getRank().getType()).isEqualTo(HandRankType.HIGH_CARD);
        assertThat(onePairHand.getRank().getType()).isEqualTo(HandRankType.PAIR);
        assertThat(twoPairsHand.getRank().getType()).isEqualTo(HandRankType.TWO_PAIRS);
        assertThat(threeOfAKindHand.getRank().getType()).isEqualTo(HandRankType.THREE_OF_A_KIND);
        assertThat(straightHand.getRank().getType()).isEqualTo(HandRankType.STRAIGHT);
        assertThat(flushHand.getRank().getType()).isEqualTo(HandRankType.FLUSH);
        assertThat(fullHouseHand.getRank().getType()).isEqualTo(HandRankType.FULL_HOUSE);
        assertThat(fourOfAKindHand.getRank().getType()).isEqualTo(HandRankType.FOUR_OF_A_KIND);
        assertThat(straightFlushHand.getRank().getType()).isEqualTo(HandRankType.STRAIGHT_FLUSH);
        assertThat(royalFlushHand.getRank().getType()).isEqualTo(HandRankType.ROYAL_FLUSH);
    }

    public void testConstructorIncorrectLengthInput() {
        // given
        var incorrectLengthString = "4H 7D KC 2S";

        // when
        Throwable thrown = catchThrowable(() ->
            new PokerHand(incorrectLengthString)
        );

        // then
        assertThat(thrown).isInstanceOf(InvalidHandException.class);
        assertThat(thrown).hasMessage("Hand string should have 5 cards in format Rank:Suit");
    }

    public void testConstructorIncorrectCardLength() {
        // given
        var incorrectCardLengthString = "4H 7D KC 2S 10C";

        // when
        Throwable thrown = catchThrowable(() ->
            new PokerHand(incorrectCardLengthString)
        );

        // then
        assertThat(thrown).isInstanceOf(InvalidHandException.class);
        assertThat(thrown).hasMessage("Card should be in format Rank:Suit");
    }

    public void testConstructorIncorrectValuesInput() {
        // given
        var incorrectValuesString = "4H 7M KC 2S TC";

        // when
        Throwable thrown = catchThrowable(() ->
            new PokerHand(incorrectValuesString)
        );

        // then
        assertThat(thrown).isInstanceOf(InvalidHandException.class);
        assertThat(thrown).hasMessage("Illegal values in hand string");
    }

    public void testCompareToHighCardAndOnePair() {
        // given
        var highCardString = "4H 7D KC 2S TC";
        var onePairString = "KC KH 7D 2C 5S";

        var highCardHand = new PokerHand(highCardString);
        var onePairHand = new PokerHand(onePairString);

        // when
        int compared = highCardHand.compareTo(onePairHand);

        // then
        assertThat(compared).isGreaterThan(0);
    }

    public void testCompareHighCardToByKicker() {
        // given
        var greaterString = "TC KH 7S 5D 2H";
        var smallerString = "TD KC 4H 5D 2S";

        var greaterHand = new PokerHand(greaterString);
        var smallerHand = new PokerHand(smallerString);

        // when
        int compared = greaterHand.compareTo(smallerHand);

        // then
        assertThat(compared).isLessThan(0);
    }

    public void testCompareFlushOneByOne() {
        // given
        var greaterFlushString = "3S KS 7S 2S TS";
        var smallerFlushString = "TD 4D 8D 3D JD";

        var greaterFlushHand = new PokerHand(greaterFlushString);
        var smallerFlushHand = new PokerHand(smallerFlushString);

        // when
        int compared = greaterFlushHand.compareTo(smallerFlushHand);

        // then
        assertThat(compared).isLessThan(0);
    }

    public void testCompareTwoPairsOneByOne() {
        // given
        var greaterTwoPairsString = "8H 8D 4C 4S 9H";
        var smallerTwoPairsString = "8H 8D 3C 3S 9H";

        var greaterTwoPairsHand = new PokerHand(greaterTwoPairsString);
        var smallerTwoPairsHand = new PokerHand(smallerTwoPairsString);

        // when
        int compared = greaterTwoPairsHand.compareTo(smallerTwoPairsHand);

        // then
        assertThat(compared).isLessThan(0);
    }

    public void testSortList() {
        // given
        var royalFlushHand = new PokerHand("AH KH QH JH TH");
        var straightFlushHand = new PokerHand("8S 7S 6S 5S 4S");
        var fourOfAKindHand = new PokerHand("AC AD AS AH 3C");
        var fullHouseHand = new PokerHand("KC KD KS 9D 9H");
        var flushGreaterHand = new PokerHand("QD 9D 7D 5D 2D");
        var flushSmallerHand = new PokerHand("QD 7D 5D 4D 3D");


        List<PokerHand> list = List.of(
            fourOfAKindHand,
            fullHouseHand,
            flushSmallerHand,
            flushGreaterHand,
            royalFlushHand,
            straightFlushHand
        );

        List<PokerHand> rightOrder = List.of(
            royalFlushHand,
            straightFlushHand,
            fourOfAKindHand,
            fullHouseHand,
            flushGreaterHand,
            flushSmallerHand
        );

        // when
        List<PokerHand> sortedList = list.stream().sorted().toList();

        // then
        assertThat(sortedList).isEqualTo(rightOrder);
    }

}